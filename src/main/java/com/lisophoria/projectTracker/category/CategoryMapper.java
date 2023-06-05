package com.lisophoria.projectTracker.category;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Category category = new Category();

        category.setCategoryId(resultSet.getInt("categoryId"));
        category.setCategoryName(resultSet.getString("categoryName"));
        category.setUserId(resultSet.getInt("userId"));

        return category;
    }
}
