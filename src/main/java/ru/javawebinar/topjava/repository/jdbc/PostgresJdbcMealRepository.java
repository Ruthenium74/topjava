package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Repository
@Profile("postgres")
public class PostgresJdbcMealRepository extends JdbcMealRepository {

    @Autowired
    public PostgresJdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public MapSqlParameterSource getMapSqlParameterSource(Meal meal, int userId) {
        return new MapSqlParameterSource().addValue("id", meal.getId())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("date_time", meal.getDateTime())
                .addValue("user_id", userId);
    }

    @Override
    public PreparedStatementSetter getPreparedStatement(int userId, LocalDateTime from, LocalDateTime to) {
        return new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, userId);
                preparedStatement.setObject(2, from);
                preparedStatement.setObject(3, to);
            }
        };
    }
}
