package com.dev.acouplefarms.models.location;

import static javax.persistence.GenerationType.AUTO;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  private Long organizationId;
  private String name;
  private String nameKey;
  // private LocationType locationType;
  private Date createDate;
  private Date updateDate;
  private Boolean active;
}
