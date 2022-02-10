package com.dev.acouplefarms.models.location;

import java.util.Date;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LocationStatsResponse {
  private Date date;
  private Set<LocationStatResponse> locationStatResponses;
}
