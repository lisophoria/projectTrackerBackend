package com.lisophoria.projectTracker.token;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenMapper implements RowMapper<Token> {
    @Override
    public Token mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Token token = new Token();
        token.setTokenId(resultSet.getInt("tokenId"));
        token.setToken(resultSet.getString("token"));
        token.setTokenType(TokenType.valueOf(resultSet.getString("tokenType")));
        token.setRevoked(resultSet.getBoolean("revoked"));
        token.setExpired(resultSet.getBoolean("expired"));
        token.setUserId(resultSet.getInt("userId"));

        return token;
    }
}
