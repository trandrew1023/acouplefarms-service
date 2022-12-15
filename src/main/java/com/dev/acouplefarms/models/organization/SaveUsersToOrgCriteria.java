package com.dev.acouplefarms.models.organization;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SaveUsersToOrgCriteria {
  private Long organizationId;
  private Set<Long> userIds;
}
