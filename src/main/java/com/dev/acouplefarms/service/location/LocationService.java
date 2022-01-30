package com.dev.acouplefarms.service.location;

import com.dev.acouplefarms.models.location.Location;
import com.dev.acouplefarms.models.relation.LocationColumnRelation;
import java.util.Set;

public interface LocationService {
  Location saveLocation(final Location location);

  Location getLocationById();

  Location getLocationByNameKeyAndOrganizationId(final String nameKey, final Long organizationId);

  Set<Location> getOrgLocations(final Long organizationId);

  LocationColumnRelation saveLocationColumnRelation();
}
