package com.dev.acouplefarms.models.user;

import static javax.persistence.GenerationType.AUTO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`users`")
public class User {

  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  private String username;
  private String firstname;
  private String lastname;
  private String password;
  private String email;
  private String phoneNumber;
  private String usernameKey;
  private Date createDate;
  private Date updateDate;
  private Boolean active;

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<AuthorityRole> grantedAuthorities = new HashSet<>();
}
