package com.dev.acouplefarms.repository;

import com.dev.acouplefarms.models.user.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {
  PasswordToken findByToken(final String token);
}
