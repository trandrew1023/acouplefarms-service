package com.dev.acouplefarms.models.organization;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SaveOrganizationCriteria {
  private Organization organization;
  private Set<Long> userIds;
}
