package com.lisophoria.projectTracker.token;

import com.lisophoria.projectTracker.user.User;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

  public Integer tokenId;

  public String token;

  public TokenType tokenType = TokenType.BEARER;

  public boolean revoked;

  public boolean expired;

  public Integer userId;
}
