package com.dev.acouplefarms.models.user;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityRole {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  private String roleName;
}
