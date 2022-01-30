package com.dev.acouplefarms.service.user;

import static com.dev.acouplefarms.util.StringUtil.scrubString;

import com.dev.acouplefarms.models.relation.UserOrgRelation;
import com.dev.acouplefarms.models.user.AuthorityRole;
import com.dev.acouplefarms.models.user.User;
import com.dev.acouplefarms.repository.AuthorityRoleRepository;
import com.dev.acouplefarms.repository.UserOrgRelationRepository;
import com.dev.acouplefarms.repository.UserRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

  private final AuthorityRoleRepository authorityRoleRepository;
  private final UserRepository userRepository;
  private final UserOrgRelationRepository userOrgRelationRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User saveUser(final User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  @Override
  public User getUserByUsername(final String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public User getUserByUsernameKey(final String usernameKey) {
    return userRepository.findByUsernameKey(usernameKey);
  }

  @Override
  public User getUserByEmail(final String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public User getUserById(final Long id) {
    final Optional<User> user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new EntityNotFoundException("User not found wth id: " + id);
    }
    return user.get();
  }

  @Override
  public Set<User> searchByUsername(final String username) {
    return userRepository.searchByUsernameKey(scrubString(username));
  }

  @Override
  public AuthorityRole saveRole(final AuthorityRole role) {
    return authorityRoleRepository.save(role);
  }

  @Override
  public void saveUserOrganizationRelation(final UserOrgRelation userOrgRelation) {
    userOrgRelationRepository.save(userOrgRelation);
  }

  @Override
  public void saveAllUserOrgRelations(Set<UserOrgRelation> userOrgRelations) {
    userOrgRelationRepository.saveAll(userOrgRelations);
  }

  @Override
  public void addRoleToUser(final String username, final String roleName) {
    final User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User with username: " + username + "not found");
    }
    final AuthorityRole authorityRole =
        authorityRoleRepository.getAuthorityRoleByRoleName(roleName);
    user.getGrantedAuthorities().add(authorityRole);
  }

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    final User user = getUserByUsernameKey(scrubString(username));
    if (user == null) {
      throw new UsernameNotFoundException("User " + username + "not found");
    }
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        user.getGrantedAuthorities().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getRoleName()))
            .collect(Collectors.toSet()));
  }
}
