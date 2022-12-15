package com.dev.acouplefarms.repository;

import com.dev.acouplefarms.models.location.LocationStat;
import java.util.Date;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationStatRepository extends JpaRepository<LocationStat, Long> {
  Set<LocationStat> findByLocationId(final Long locationId);

  Set<LocationStat> findByLocationIdAndDate(final Long locationId, final Date date);

  //  @Query("SELECT ls FROM LOCATION_STAT ls WHERE ls.location_id = %:locationId% AND ls.date >=
  // %:startDate% OR ls.date <= %:endDate%")
  //  Set<LocationStat> findLocationStatsBetweenDates(final Long locationId, final Date startDate,
  // final Date endDate);
}
