package com.dev.acouplefarms;

import com.dev.acouplefarms.models.organization.Organization;
import com.dev.acouplefarms.models.relation.UserOrgRelation;
import com.dev.acouplefarms.models.user.AuthorityRole;
import com.dev.acouplefarms.models.user.User;
import com.dev.acouplefarms.service.organization.OrganizationService;
import com.dev.acouplefarms.service.user.UserService;
import com.google.common.collect.Sets;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ACoupleFarmsApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(ACoupleFarmsApplication.class, args);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Autowired private UserService userService;
  @Autowired private OrganizationService organizationService;

  @Override
  public void run(String... args) throws Exception {
    final AuthorityRole adminRole = userService.saveRole(new AuthorityRole(null, "ROLE_ADMIN"));
    final AuthorityRole userRole = userService.saveRole(new AuthorityRole(null, "ROLE_USER"));

    userService.saveUser(
        new User(
            null,
            "admin",
            "admin",
            "admin",
            "admin",
            "admin@email.com",
            "123-456-7890",
            "ADMIN",
            Date.from(Instant.now()),
            Date.from(Instant.now()),
            true,
            Sets.newHashSet(adminRole, userRole)));
    userService.saveUser(
        new User(
            null,
            "trandrew",
            "Andrew",
            "Tran",
            "password",
            "andrewtran1023@live.com",
            "515-657-0589",
            "TRANDREW",
            Date.from(Instant.now()),
            Date.from(Instant.now()),
            true,
            Sets.newHashSet(userRole)));
    organizationService.saveOrganization(
        new Organization(
            null,
            "AT Org",
            "ATORG",
            "org@atorg.com",
            "123-456-7890",
            Date.from(Instant.now()),
            Date.from(Instant.now()),
            true));
    organizationService.saveOrganization(
        new Organization(
            2L,
            "Test Org",
            "TESTORG",
            "test@testorg.com",
            "123-456-7890",
            Date.from(Instant.now()),
            Date.from(Instant.now()),
            true));
    userService.saveUserOrganizationRelation(
        new UserOrgRelation(
            userService.getUserByUsernameKey("TRANDREW").getId(),
            organizationService.getOrganizationByName("ATORG").getId(),
            Date.from(Instant.now()),
            Date.from(Instant.now()),
            true,
            true));
  }
}
