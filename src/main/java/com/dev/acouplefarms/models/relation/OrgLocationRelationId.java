package com.dev.acouplefarms.models.relation;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class OrgLocationRelationId implements Serializable {
  private Long organizationId;
  private Long locationId;
}
