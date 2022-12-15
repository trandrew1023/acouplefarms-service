package com.dev.acouplefarms.models.location;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LocationStatResponse {
  private Long locationStatId;
  private Long locationId;
  private String locationName;
  private Map<Long, String> locationColumnIdToValue;
}
