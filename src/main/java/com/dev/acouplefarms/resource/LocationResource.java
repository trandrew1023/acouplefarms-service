package com.dev.acouplefarms.resource;

import static com.dev.acouplefarms.util.StringUtil.scrubString;

import com.dev.acouplefarms.models.location.Location;
import com.dev.acouplefarms.service.location.LocationService;
import com.dev.acouplefarms.service.organization.OrganizationService;
import java.time.Instant;
import java.util.Date;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/location")
public class LocationResource {

  @Autowired private final LocationService locationService;
  @Autowired private final OrganizationService organizationService;

  /**
   * Constructor
   *
   * @param locationService The {@link LocationService}
   */
  public LocationResource(
      final LocationService locationService, final OrganizationService organizationService) {
    this.locationService = locationService;
    this.organizationService = organizationService;
  }

  @PostMapping("")
  public ResponseEntity<?> saveLocation(@RequestBody final Location location) {
    final String nameKey = scrubString(location.getName());
    final Long organizationId = location.getOrganizationId();
    if (locationService.getLocationByNameKeyAndOrganizationId(nameKey, organizationId) != null) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    if (organizationService.getOrganizationById(organizationId).isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    final Date curDate = Date.from(Instant.now());
    location.setNameKey(nameKey);
    location.setCreateDate(curDate);
    location.setUpdateDate(curDate);
    location.setActive(true);
    locationService.saveLocation(location);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping("/edit")
  public ResponseEntity<?> editLocation(@RequestBody final Location location) {
    if (locationService.getLocationById(location.getId()) == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    final String nameKey = scrubString(location.getName());
    final Long organizationId = location.getOrganizationId();
    if (locationService.getLocationByNameKeyAndOrganizationId(nameKey, organizationId) != null) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    if (organizationService.getOrganizationById(organizationId).isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    final Date curDate = Date.from(Instant.now());
    location.setNameKey(nameKey);
    location.setUpdateDate(curDate);
    locationService.saveLocation(location);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
