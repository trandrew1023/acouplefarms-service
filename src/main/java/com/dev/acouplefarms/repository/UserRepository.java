package com.dev.acouplefarms.repository;

import com.dev.acouplefarms.models.user.User;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findById(final Long id);

  User findByUsername(final String username);

  User findByUsernameKey(final String usernameKey);

  User findByEmail(final String email);

  @Query("FROM User u WHERE u.usernameKey LIKE %:usernameKey%")
  Set<User> searchByUsernameKey(final String usernameKey);
}
