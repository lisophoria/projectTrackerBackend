package com.lisophoria.projectTracker.task;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class TaskRepository {
    private final NamedParameterJdbcTemplate template;

    @Transactional
    public List<Task> getByCategoryId(Integer categoryId) {
        try {
            String sql = "select * from task where categoryId = :categoryId";
            SqlParameterSource parameterSource = new MapSqlParameterSource()
                    .addValue("categoryId", categoryId);

            return template.query(sql, parameterSource, new TaskMapper());
        } catch (Exception e) {
            throw new RuntimeException("cannot find category", e);
        }
    }

    @Transactional
    public void deleteById(Integer taskId) {
        try {
            String sql = "delete from task where taskId = :taskId";
            SqlParameterSource parameterSource = new MapSqlParameterSource()
                    .addValue("taskId", taskId);

            template.update(sql, parameterSource);
        } catch (Exception e) {
            throw new RuntimeException("cannot delete tasak", e);
        }
    }

    @Transactional
    public Task save(Task task) {
        try {
            String checkIsExistsSql = "select exists(select 1 from task where taskId = :taskId)";
            SqlParameterSource checkIsExistsParameterSource = new MapSqlParameterSource()
                    .addValue("taskId", task.getTaskId());

            boolean isExists = Boolean.TRUE.equals(template.queryForObject(checkIsExistsSql, checkIsExistsParameterSource, Boolean.class));

            String sql;
            SqlParameterSource parameterSource;

            if (isExists) {
                sql = "update public.task set taskName = :taskName, description = :description, status = :status where taskId = :taskId";
                parameterSource = new MapSqlParameterSource()
                        .addValue("taskName", task.getTaskName())
                        .addValue("description", task.getDescription())
                        .addValue("status", task.getStatus())
                        .addValue("taskId", task.getTaskId());
            } else {
                sql = "insert into public.task (taskId, taskName, description, categoryId, status) values (:taskId, :taskName, :description, :categoryId, false)";
                parameterSource = new MapSqlParameterSource()
                        .addValue("taskId", task.getTaskId())
                        .addValue("taskName", task.getTaskName())
                        .addValue("description", task.getDescription())
                        .addValue("categoryId", task.getCategoryId());
            }

            template.update(sql, parameterSource);

            return task;
        } catch (Exception e) {
            throw new RuntimeException("cannot save task", e);
        }
    }
}
