package com.dev.acouplefarms.models.location;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LocationStatsCriteria {
  private Long locationId;
  private Map<Long, String> locationColumnIdToValue;
}
