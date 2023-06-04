package com.lisophoria.projectTracker.token;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TokenRepository {
  private final NamedParameterJdbcTemplate template;

  public TokenRepository (NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  public List<Token> findAllValidTokenByUser(Integer userId) {
    String sql = "select * from token where userId = :userId and expired = false or revoked = false";
    SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("userId", userId);

    return template.query(sql, parameterSource, new TokenMapper());
  }

  public Optional<Token> findByToken(String token) {
    String sql = "select * from token where token = :token";
    SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("token", token);

    return Optional.ofNullable(template.queryForObject(sql, parameterSource, new TokenMapper()));
  }

  public Token save(Token token) {
    try {
      String checkIsExistsSql = "select * from public.token where userId = :userId";
      SqlParameterSource checkIsExistsParameterSource = new MapSqlParameterSource()
              .addValue("userId", token.getUserId());

      boolean isExists = template.query(checkIsExistsSql, checkIsExistsParameterSource, new TokenMapper()).size() > 0;

      if (isExists) {
        String sql = "update public.token set token = :token where userId = :userId";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("token", token.getToken())
                .addValue("userId", token.getUserId());

        template.update(sql, parameterSource);
      } else {
        String sql = "insert into public.token (token, tokentype, revoked, expired, userid) values (:token, :tokenType, :revoked, :expired, :userId)";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("token", token.getToken())
                .addValue("tokenType", token.getTokenType().name())
                .addValue("revoked", token.isRevoked())
                .addValue("expired", token.isExpired())
                .addValue("userId", token.getUserId());

        template.update(sql, parameterSource);
      }

      return token;
    } catch (Exception e) {
      throw new RuntimeException("cannot save token", e);
    }

  }

  public List<Token> saveAll(List<Token> tokens) {
    try {
      List<Token> resultList = new ArrayList<Token>();
      tokens.forEach((token) -> resultList.add(this.save(token)));
      return tokens;
    } catch (Exception e) {
      throw new RuntimeException("cannot save tokens", e);
    }
  }
}
