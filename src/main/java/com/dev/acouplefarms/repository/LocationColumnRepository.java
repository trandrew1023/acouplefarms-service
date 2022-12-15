package com.dev.acouplefarms.repository;

import com.dev.acouplefarms.models.location.LocationColumn;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationColumnRepository extends JpaRepository<LocationColumn, Long> {
  Set<LocationColumn> findByOrganizationId(final Long organizationId);

  LocationColumn findByNameKeyAndOrganizationId(final String nameKey, final Long organizationId);
}
