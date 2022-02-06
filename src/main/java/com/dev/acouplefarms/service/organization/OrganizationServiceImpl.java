package com.dev.acouplefarms.service.organization;

import static com.dev.acouplefarms.util.StringUtil.scrubString;

import com.dev.acouplefarms.models.location.Location;
import com.dev.acouplefarms.models.organization.Organization;
import com.dev.acouplefarms.models.organization.OrganizationResponse;
import com.dev.acouplefarms.models.relation.OrgLocationRelation;
import com.dev.acouplefarms.models.relation.UserOrgRelation;
import com.dev.acouplefarms.repository.LocationRepository;
import com.dev.acouplefarms.repository.OrgLocationRelationRepository;
import com.dev.acouplefarms.repository.OrganizationRepository;
import com.dev.acouplefarms.repository.UserOrgRelationRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrganizationServiceImpl implements OrganizationService {

  private final LocationRepository locationRepository;
  private final OrganizationRepository organizationRepository;
  private final OrgLocationRelationRepository orgLocationRelationRepository;
  private final UserOrgRelationRepository userOrgRelationRepository;

  @Override
  public Organization saveOrganization(final Organization organization) {
    return organizationRepository.save(organization);
  }

  @Override
  public OrgLocationRelation saveOrgLocationRelation(
      final OrgLocationRelation orgLocationRelation) {
    final Long organizationId = orgLocationRelation.getOrganizationId();
    final Long locationId = orgLocationRelation.getLocationId();
    final Optional<Organization> organization = organizationRepository.findById(organizationId);
    if (organization.isEmpty()) {
      throw new EntityNotFoundException("Organization not found wth id: " + organizationId);
    }
    final Optional<Location> location = locationRepository.findById(locationId);
    if (location.isEmpty()) {
      throw new EntityNotFoundException("Location not found wth id: " + locationId);
    }
    if (orgLocationRelationRepository.findByOrganizationIdAndLocationId(organizationId, locationId)
        != null) {
      throw new EntityExistsException(
          "Org " + organizationId + "is already associated to location " + locationId);
    }
    return orgLocationRelationRepository.save(orgLocationRelation);
  }

  @Override
  public Set<UserOrgRelation> getUserOrgRelationsByOrganizationId(final Long organizationId) {
    return userOrgRelationRepository.findByOrganizationIdAndActive(organizationId, true);
  }

  @Override
  public Optional<Organization> getOrganizationById(final Long id) {
    return organizationRepository.findById(id);
  }

  @Override
  public Set<OrganizationResponse> getOrganizationsByUserId(final long userId) {
    final Set<UserOrgRelation> userOrgRelations =
        userOrgRelationRepository.findByUserIdAndActive(userId, true);
    final Set<OrganizationResponse> organizations = new HashSet<>();
    for (final UserOrgRelation userOrgRelation : userOrgRelations) {
      organizations.add(
          new OrganizationResponse(
              organizationRepository.getById(userOrgRelation.getOrganizationId()),
              userOrgRelation.getAdmin()));
    }
    return organizations;
  }

  @Override
  public Organization getOrganizationByName(final String name) {
    return organizationRepository.findByNameKey(scrubString(name));
  }
}
