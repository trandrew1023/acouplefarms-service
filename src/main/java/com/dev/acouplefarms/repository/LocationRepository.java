package com.dev.acouplefarms.repository;

import com.dev.acouplefarms.models.location.Location;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
  Optional<Location> findById(final Long id);

  Location findByNameKeyAndOrganizationId(final String nameKey, final Long organizationId);

  Set<Location> findByOrganizationId(final Long organizationId);
}
