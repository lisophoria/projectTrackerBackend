package com.lisophoria.projectTracker.auth;

import com.lisophoria.projectTracker.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  private String accessToken;
  private String refreshToken;
  private User user;
}
