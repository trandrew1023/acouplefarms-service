package com.dev.acouplefarms.service.location;

import com.dev.acouplefarms.models.location.Location;
import com.dev.acouplefarms.models.location.LocationColumn;
import com.dev.acouplefarms.models.location.LocationStat;
import com.dev.acouplefarms.models.relation.LocationColumnRelation;
import java.util.Date;
import java.util.Set;

public interface LocationService {
  Location saveLocation(final Location location);

  Location getLocationById();

  Location getLocationByNameKeyAndOrganizationId(final String nameKey, final Long organizationId);

  Set<Location> getOrgLocations(final Long organizationId);

  LocationColumnRelation saveLocationColumnRelation();

  LocationColumn saveLocationColumn(final LocationColumn locationColumn);

  Set<LocationColumn> getLocationColumnsByOrganizationId(final Long organizationId);

  LocationColumn getLocationColumnByNameKeyAndOrganizationId(
      final String nameKey, final Long organizationId);

  Set<LocationStat> getLocationStatsByLocationIdAndDate(final Long locationId, final Date date);

  void saveLocationStats(final Set<LocationStat> locationStats);
}
