package com.lisophoria.projectTracker.user;

import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepository {

  private final NamedParameterJdbcTemplate template;

  public UserRepository (NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  @Transactional(readOnly = true)
  public Optional<User> findByEmail(String email) {
    String sql = "select * from public._user where email = :email";
    SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("email", email);

    return Optional.ofNullable(template.queryForObject(sql, parameterSource, new UserMapper()));
  }

  @Transactional
  public User save(User user) {
    try {
      String sql = "insert into public._user (firstname, lastname, email, password, role) values ( :firstname, :lastname, :email, :password, :role)";
      SqlParameterSource parameterSource = new MapSqlParameterSource()
              .addValue("firstname", user.getFirstname())
              .addValue("lastname", user.getLastname())
              .addValue("email", user.getEmail())
              .addValue("password", user.getPassword())
              .addValue("role", user.getRole().getName());

      template.update(sql, parameterSource);

      return null;
    } catch (Exception e) {
      throw new RuntimeException("cannot save user", e);
    }
  }
}
