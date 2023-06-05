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
}
