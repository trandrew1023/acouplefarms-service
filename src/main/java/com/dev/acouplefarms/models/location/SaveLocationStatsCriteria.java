package com.dev.acouplefarms.models.location;

import java.util.Date;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SaveLocationStatsCriteria {
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Date date;

  private Set<LocationStatsCriteria> locationStatsCriteria;
}
