package com.dev.acouplefarms.service.location;

import com.dev.acouplefarms.models.location.Location;
import com.dev.acouplefarms.models.location.LocationColumn;
import com.dev.acouplefarms.models.relation.LocationColumnRelation;
import com.dev.acouplefarms.repository.LocationColumnRepository;
import com.dev.acouplefarms.repository.LocationRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LocationServiceImpl implements LocationService {

  private final LocationColumnRepository locationColumnRepository;
  private final LocationRepository locationRepository;

  @Override
  public Location saveLocation(final Location location) {
    return locationRepository.save(location);
  }

  @Override
  public Location getLocationById() {
    return null;
  }

  @Override
  public Location getLocationByNameKeyAndOrganizationId(
      final String nameKey, final Long organizationId) {
    return locationRepository.findByNameKeyAndOrganizationId(nameKey, organizationId);
  }

  @Override
  public Set<Location> getOrgLocations(final Long organizationId) {
    return locationRepository.findByOrganizationId(organizationId);
  }

  @Override
  public LocationColumnRelation saveLocationColumnRelation() {
    return null;
  }

  @Override
  public LocationColumn saveLocationColumn(final LocationColumn locationColumn) {
    return locationColumnRepository.save(locationColumn);
  }

  @Override
  public Set<LocationColumn> getLocationColumnsByOrganizationId(final Long organizationId) {
    return locationColumnRepository.findByOrganizationId(organizationId);
  }

  @Override
  public LocationColumn getLocationColumnByNameKeyAndOrganizationId(
      final String nameKey, final Long organizationId) {
    return locationColumnRepository.findByNameKeyAndOrganizationId(nameKey, organizationId);
  }
}
