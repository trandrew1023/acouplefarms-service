package com.dev.acouplefarms.repository;

import com.dev.acouplefarms.models.user.AuthorityRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRoleRepository extends JpaRepository<AuthorityRole, Long> {
  AuthorityRole getAuthorityRoleByRoleName(final String roleName);
}
