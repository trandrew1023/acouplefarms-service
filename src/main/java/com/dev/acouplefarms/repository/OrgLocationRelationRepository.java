package com.dev.acouplefarms.repository;

import com.dev.acouplefarms.models.relation.OrgLocationRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgLocationRelationRepository extends JpaRepository<OrgLocationRelation, Long> {
  OrgLocationRelation findByOrganizationId(final Long organizationId);

  OrgLocationRelation findByOrganizationIdAndLocationId(
      final Long organizationId, final Long locationId);
}
