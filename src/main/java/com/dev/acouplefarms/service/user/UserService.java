package com.dev.acouplefarms.service.user;

import com.dev.acouplefarms.models.relation.UserOrgRelation;
import com.dev.acouplefarms.models.user.AuthorityRole;
import com.dev.acouplefarms.models.user.User;
import java.util.Set;

public interface UserService {
  User saveUser(final User user);

  User getUserByUsername(final String username);

  User getUserByUsernameKey(final String username);

  User getUserByEmail(final String email);

  User getUserById(final Long id);

  Set<User> searchByUsername(final String username);

  Set<User> getUsersByIds(final Set<Long> userIds);

  AuthorityRole saveRole(final AuthorityRole role);

  UserOrgRelation getUserOrgRelation(final Long userId, final Long organizationId);

  void saveUserOrganizationRelation(final UserOrgRelation userOrgRelation);

  void saveAllUserOrgRelations(final Set<UserOrgRelation> userOrgRelations);

  void addRoleToUser(final String username, final String role);
}
