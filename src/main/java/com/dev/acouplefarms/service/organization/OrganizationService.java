package com.dev.acouplefarms.service.organization;

import com.dev.acouplefarms.models.organization.Organization;
import com.dev.acouplefarms.models.organization.OrganizationResponse;
import com.dev.acouplefarms.models.relation.OrgLocationRelation;
import java.util.Optional;
import java.util.Set;

public interface OrganizationService {
  Optional<Organization> getOrganizationById(final Long id);

  Set<OrganizationResponse> getOrganizationsByUserId(final long userId);

  Organization getOrganizationByName(final String name);

  Organization saveOrganization(final Organization organization);

  OrgLocationRelation saveOrgLocationRelation(final OrgLocationRelation orgLocationRelation);
}
