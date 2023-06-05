package com.lisophoria.projectTracker.category;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CategoryRepository {
    private final NamedParameterJdbcTemplate template;

    public CategoryRepository (NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Transactional
    public List<Category> getByUserId(Integer userId) {
        try {
            String sql = "select * from category where userId = :userId";
            SqlParameterSource parameterSource = new MapSqlParameterSource()
                    .addValue("userId", userId);

            return template.query(sql, parameterSource, new CategoryMapper());
        } catch (Exception e) {
            throw new RuntimeException("cannot find category", e);
        }
    }

    @Transactional
    public void deleteById(Integer categoryId) {
        try {
            String sql = "delete from category where categoryId = :categoryId";
            SqlParameterSource parameterSource = new MapSqlParameterSource()
                    .addValue("categoryId", categoryId);

            template.update(sql, parameterSource);
        } catch (Exception e) {
            throw new RuntimeException("cannot delete category", e);
        }
    }

    @Transactional
    public Category save(Category category) {
        try {
            String checkIsExistsSql = "select exists(select 1 from category where categoryId = :categoryId)";
            SqlParameterSource checkIsExistsParameterSource = new MapSqlParameterSource()
                    .addValue("categoryId", category.getCategoryId());

            boolean isExists = Boolean.TRUE.equals(template.queryForObject(checkIsExistsSql, checkIsExistsParameterSource, Boolean.class));

            String sql;
            SqlParameterSource parameterSource;

            if (isExists) {
                sql = "update public.category set categoryName = :categoryName where categoryId = :categoryId";
                parameterSource = new MapSqlParameterSource()
                        .addValue("categoryName", category.getCategoryName())
                        .addValue("categoryId", category.getCategoryId());

            } else {
                sql = "insert into public.category (categoryName, userId) values (:categoryName, :userId)";
                parameterSource = new MapSqlParameterSource()
                        .addValue("categoryName", category.getCategoryName())
                        .addValue("userId", category.getUserId());

            }

            template.update(sql, parameterSource);

            return category;
        } catch (Exception e) {
            throw new RuntimeException("cannot save category", e);
        }
    }
}
