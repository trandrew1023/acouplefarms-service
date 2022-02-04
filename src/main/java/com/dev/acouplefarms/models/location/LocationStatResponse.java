package com.dev.acouplefarms.models.location;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LocationStatResponse {
  private Long locationStatId;
  private Long locationId;
  private String locationName;
  private Map<Long, String> locationColumnIdToValue;
}
