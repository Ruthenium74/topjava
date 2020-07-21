package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import ru.javawebinar.topjava.model.Role;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RolesBatchPreparedStatementSetter implements BatchPreparedStatementSetter {
    private final int userId;
    private final Role[] roles;

    public RolesBatchPreparedStatementSetter(int userId, Role[] roles) {
        this.userId = userId;
        this.roles = roles;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, roles[i].toString());
    }

    @Override
    public int getBatchSize() {
        return roles.length;
    }
}
