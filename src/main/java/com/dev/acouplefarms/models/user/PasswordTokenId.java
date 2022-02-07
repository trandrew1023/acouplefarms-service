package com.dev.acouplefarms.models.user;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PasswordTokenId implements Serializable {
  private Long userId;
  private String token;
}
