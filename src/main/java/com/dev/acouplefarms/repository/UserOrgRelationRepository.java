package com.dev.acouplefarms.repository;

import com.dev.acouplefarms.models.relation.UserOrgRelation;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrgRelationRepository extends JpaRepository<UserOrgRelation, Long> {
  Set<UserOrgRelation> findByUserId(final Long userId);

  UserOrgRelation findByUserIdAndOrganizationId(final Long userId, final Long organizationId);
}
