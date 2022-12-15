package com.dev.acouplefarms.models.relation;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserOrgRelationId implements Serializable {
  private Long userId;
  private Long organizationId;
}
