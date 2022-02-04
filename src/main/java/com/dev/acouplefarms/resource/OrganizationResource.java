package com.dev.acouplefarms.resource;

import static com.dev.acouplefarms.util.StringUtil.scrubString;

import com.dev.acouplefarms.models.location.Location;
import com.dev.acouplefarms.models.location.LocationColumn;
import com.dev.acouplefarms.models.location.LocationStat;
import com.dev.acouplefarms.models.location.LocationStatResponse;
import com.dev.acouplefarms.models.location.LocationStatsCriteria;
import com.dev.acouplefarms.models.location.SaveLocationStatsCriteria;
import com.dev.acouplefarms.models.organization.Organization;
import com.dev.acouplefarms.models.organization.SaveOrganizationCriteria;
import com.dev.acouplefarms.models.relation.UserOrgRelation;
import com.dev.acouplefarms.models.user.User;
import com.dev.acouplefarms.service.location.LocationService;
import com.dev.acouplefarms.service.organization.OrganizationService;
import com.dev.acouplefarms.service.user.UserService;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/organization")
public class OrganizationResource {

  @Autowired private final LocationService locationService;
  @Autowired private final OrganizationService organizationService;
  @Autowired private final UserService userService;

  /**
   * Constructor
   *
   * @param organizationService The {@link OrganizationService} instance
   */
  public OrganizationResource(
      final LocationService locationService,
      final OrganizationService organizationService,
      final UserService userService) {
    this.locationService = locationService;
    this.organizationService = organizationService;
    this.userService = userService;
  }

  @GetMapping("/{orgName}/check")
  public ResponseEntity<?> getOrganizationByName(@PathVariable String orgName) {
    final Organization organization = organizationService.getOrganizationByName(orgName);
    if (organization == null) {
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.IM_USED);
  }

  /**
   * Saves a new {@link Organization} and associates it to the users
   *
   * @param saveOrganizationCriteria The {@link SaveOrganizationCriteria} to save
   * @return {@link ResponseEntity} containing response status after save
   */
  @PostMapping("")
  public ResponseEntity<?> saveOrganization(
      @RequestBody final SaveOrganizationCriteria saveOrganizationCriteria,
      final HttpServletRequest request) {
    final User user = userService.getUserByUsername(request.getUserPrincipal().getName());
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    final Organization organization = saveOrganizationCriteria.getOrganization();
    if (organizationService.getOrganizationByName(organization.getName()) != null) {
      return new ResponseEntity<>("name", HttpStatus.CONFLICT);
    }
    final Date curDate = Date.from(Instant.now());
    organization.setActive(true);
    organization.setCreateDate(curDate);
    organization.setUpdateDate(curDate);
    organization.setNameKey(scrubString(organization.getName()));
    for (final Long userId : saveOrganizationCriteria.getUserIds()) {
      if (userService.getUserById(userId) == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    final Organization savedOrganization = organizationService.saveOrganization(organization);
    final Long organizationId = savedOrganization.getId();
    final Set<UserOrgRelation> userOrgRelations = new HashSet<>();
    for (final Long userId : saveOrganizationCriteria.getUserIds()) {
      userOrgRelations.add(
          new UserOrgRelation(
              userId, organizationId, curDate, curDate, userId == user.getId(), true));
    }
    userService.saveAllUserOrgRelations(userOrgRelations);
    log.info("SAVED ORG ID: " + savedOrganization.getId());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/locations/{organizationId}")
  public ResponseEntity<?> getOrgLocations(@PathVariable final Long organizationId) {
    return new ResponseEntity<>(locationService.getOrgLocations(organizationId), HttpStatus.OK);
  }

  @PostMapping("/location-column")
  public ResponseEntity<?> saveLocationColumn(@RequestBody final LocationColumn locationColumn) {
    final Long organizationId = locationColumn.getOrganizationId();
    if (organizationService.getOrganizationById(organizationId).isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    final String nameKey = scrubString(locationColumn.getName());
    if (locationService.getLocationByNameKeyAndOrganizationId(nameKey, organizationId) != null) {
      return new ResponseEntity<>("name", HttpStatus.CONFLICT);
    }
    final Date curDate = Date.from(Instant.now());
    locationColumn.setNameKey(nameKey);
    locationColumn.setCreateDate(curDate);
    locationColumn.setUpdateDate(curDate);
    locationColumn.setActive(true);
    locationService.saveLocationColumn(locationColumn);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/location-columns/{organizationId}")
  public ResponseEntity<?> getOrgLocationColumns(@PathVariable final Long organizationId) {
    return new ResponseEntity<>(
        locationService.getLocationColumnsByOrganizationId(organizationId), HttpStatus.OK);
  }

  @GetMapping("/location-stats/{organizationId}/{date}")
  public ResponseEntity<?> getOrgLocationStats(
      @PathVariable final Long organizationId,
      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date date) {
    final Set<Location> locations = locationService.getOrgLocations(organizationId);
    final Set<LocationStatResponse> locationStatsResponse = new HashSet<>();
    for (final Location location : locations) {
      final String locationName = location.getName();
      final Set<LocationStat> locationStats =
          locationService.getLocationStatsByLocationIdAndDate(location.getId(), date);
      final Map<Long, String> locationColumnIdToValue = new HashMap<>();
      for (final LocationStat locationStat : locationStats) {
        locationColumnIdToValue.put(locationStat.getLocationColumnId(), locationStat.getValue());
      }
      locationStatsResponse.add(
          new LocationStatResponse(
              location.getId(), location.getId(), locationName, locationColumnIdToValue));
    }
    return new ResponseEntity<>(locationStatsResponse, HttpStatus.OK);
  }

  @PostMapping("/location-stats/{organizationId}")
  public ResponseEntity<?> saveOrgLocationStats(
      @PathVariable final Long organizationId,
      @RequestBody SaveLocationStatsCriteria saveLocationStatsCriteria) {
    final Set<LocationStat> locationStatsToSave = new HashSet<>();
    final Date date = saveLocationStatsCriteria.getDate();
    final Date curDate = Date.from(Instant.now());
    for (final LocationStatsCriteria locationStatsCriteria :
        saveLocationStatsCriteria.getLocationStatsCriteria()) {
      final Long locationId = locationStatsCriteria.getLocationId();
      for (final Map.Entry<Long, String> locationColumnIdToValue :
          locationStatsCriteria.getLocationColumnIdToValue().entrySet()) {
        locationStatsToSave.add(
            new LocationStat(
                locationId,
                locationColumnIdToValue.getKey(),
                locationColumnIdToValue.getValue(),
                date,
                curDate));
      }
    }
    locationService.saveLocationStats(locationStatsToSave);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
