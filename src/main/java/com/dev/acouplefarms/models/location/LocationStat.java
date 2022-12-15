package com.dev.acouplefarms.models.location;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@IdClass(LocationStatId.class)
public class LocationStat {
  @Id private Long locationId;
  @Id private Long locationColumnId;
  private String value;
  @Id private Date date;
  private Date updateDate;
}
