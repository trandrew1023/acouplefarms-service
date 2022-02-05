package com.dev.acouplefarms.models.organization;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class SaveUsersToOrgCriteria {
    private Long organizationId;
    private Set<UserOrgCriteria> userOrgCriteria;

    @AllArgsConstructor
    @Getter
    public class UserOrgCriteria {
        private Long userId;
        private boolean admin;
        private boolean active;
    }
}
