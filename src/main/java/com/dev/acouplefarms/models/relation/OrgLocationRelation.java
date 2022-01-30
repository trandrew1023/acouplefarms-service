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
@IdClass(OrgLocationRelationId.class)
public class OrgLocationRelation {
  @Id private Long organizationId;
  @Id private Long locationId;
  private Date createDate;
  private Date updateDate;
  private Boolean active;
}
