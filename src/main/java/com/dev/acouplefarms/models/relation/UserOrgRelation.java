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
@IdClass(UserOrgRelationId.class)
public class UserOrgRelation {
  @Id private Long userId;
  @Id private Long organizationId;
  private Date createDate;
  private Date updateDate;
  private Boolean admin;
  private Boolean active;
}
