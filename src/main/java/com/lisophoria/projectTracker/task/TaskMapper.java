package com.lisophoria.projectTracker.task;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Task task = new Task();

        task.setTaskId(resultSet.getInt("taskId"));
        task.setCategoryId(resultSet.getInt("categoryId"));
        task.setTaskName(resultSet.getString("taskName"));
        task.setDescription(resultSet.getString("description"));
        task.setStatus(resultSet.getBoolean("status"));

        return task;
    }
}
