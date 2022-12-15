package com.dev.acouplefarms.models.relation;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(LocationColumnRelationId.class)
public class LocationColumnRelation {
  @Id private Long locationId;
  @Id private Long locationColumnId;
  private Date createDate;
  private Date updateDate;
  private Boolean active;
}
