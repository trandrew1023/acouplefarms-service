package com.dev.acouplefarms.models.user;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PasswordTokenId.class)
public class PasswordToken {
  @Id private Long userId;
  @Id private String token;
  private Date createDate;
  private Date expiryDate;
  private boolean used;
}
