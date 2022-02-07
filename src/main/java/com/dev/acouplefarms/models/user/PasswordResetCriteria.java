package com.dev.acouplefarms.models.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PasswordResetCriteria {
  private String token;
  private String newPassword;
}
