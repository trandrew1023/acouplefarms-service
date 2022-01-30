package com.dev.acouplefarms.models.relation;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class LocationColumnRelationId implements Serializable {
  private Long locationId;
  private Long locationColumnId;
}
