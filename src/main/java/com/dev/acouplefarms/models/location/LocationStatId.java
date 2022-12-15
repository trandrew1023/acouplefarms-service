package com.dev.acouplefarms.models.location;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class LocationStatId implements Serializable {
  private Long locationId;
  private Long locationColumnId;
  private Date date;
}
