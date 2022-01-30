package com.dev.acouplefarms.repository;

import com.dev.acouplefarms.models.organization.Organization;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
  Optional<Organization> findById(final Long id);

  Organization findByName(final String name);

  Organization findByNameKey(final String nameKey);
}
