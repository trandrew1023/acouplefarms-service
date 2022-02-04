package com.dev.acouplefarms.repository;

import com.dev.acouplefarms.models.location.LocationStat;
import java.util.Date;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationStatRepository extends JpaRepository<LocationStat, Long> {
  Set<LocationStat> findByLocationId(final Long locationId);

  Set<LocationStat> findByLocationIdAndDate(final Long locationId, final Date date);
}
