package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.validation.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator;

    private static final RowMapper<Role> SINGLE_USER_ROLE_ROW_MAPPER = new RowMapper<Role>() {
        @Override
        public Role mapRow(ResultSet resultSet, int i) throws SQLException {
            return Role.valueOf(resultSet.getString("role"));
        }
    };

    private static final ResultSetExtractor<List<User>> USER_RESULT_SET_EXTRACTOR = new ResultSetExtractor<List<User>>() {
        @Override
        public List<User> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Integer, User> userMap = new LinkedHashMap<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                User user = userMap.get(id);
                if (user == null) {
                    user = new User();
                    user.setId(id);
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRegistered(resultSet.getTimestamp("registered"));
                    user.setEnabled(resultSet.getBoolean("enabled"));
                    user.setCaloriesPerDay(resultSet.getInt("calories_per_day"));
                    user.setRoles(EnumSet.of(Role.valueOf(resultSet.getString("role"))));
                    userMap.put(id, user);
                } else {
                    Set<Role> userRoles = user.getRoles();
                    userRoles.add(Role.valueOf(resultSet.getString("role")));
                    user.setRoles(userRoles);
                    userMap.replace(id, user);
                }
            }
            return List.copyOf(userMap.values());
        }
    };

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        validator = validatorFactory.getValidator();
    }

    @Override
    @Transactional
    public User save(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            Role[] roles = user.getRoles().toArray(new Role[0]);
            jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?, ?)",
                    new RolesBatchPreparedStatementSetter(user.getId(), roles));
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id",
                parameterSource) == 0) {
            return null;
        } else {
            List<Role> oldRoles = jdbcTemplate.query("SELECT role FROM user_roles WHERE user_id=?",
                    SINGLE_USER_ROLE_ROW_MAPPER, user.getId());
            jdbcTemplate.batchUpdate("DELETE FROM user_roles WHERE user_id=? AND role=?",
                    new RolesBatchPreparedStatementSetter(user.getId(), oldRoles.toArray(new Role[0])));
            Set<Role> newRoles = user.getRoles();
            jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?,?)",
                    new RolesBatchPreparedStatementSetter(user.getId(), newRoles.toArray(new Role[0])));
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_roles ur ON u.id = ur.user_id" +
                " WHERE u.id=?", USER_RESULT_SET_EXTRACTOR, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_roles ur ON u.id = ur.user_id " +
                "WHERE email=?", USER_RESULT_SET_EXTRACTOR, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_roles ur ON u.id = ur.user_id " +
                "ORDER BY name, email", USER_RESULT_SET_EXTRACTOR);
    }
}
