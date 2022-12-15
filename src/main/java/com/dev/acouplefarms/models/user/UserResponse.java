package com.dev.acouplefarms.models.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserResponse {
  public UserResponse(final User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.firstname = user.getFirstname();
    this.lastname = user.getLastname();
    this.email = user.getEmail();
  }

  private Long id;
  private String username;
  private String firstname;
  private String lastname;
  private String email;
}
