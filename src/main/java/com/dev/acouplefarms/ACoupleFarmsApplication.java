package com.dev.acouplefarms;

import com.dev.acouplefarms.models.location.Location;
import com.dev.acouplefarms.models.location.LocationColumn;
import com.dev.acouplefarms.models.organization.Organization;
import com.dev.acouplefarms.models.relation.UserOrgRelation;
import com.dev.acouplefarms.models.user.AuthorityRole;
import com.dev.acouplefarms.models.user.PasswordToken;
import com.dev.acouplefarms.models.user.User;
import com.dev.acouplefarms.service.location.LocationService;
import com.dev.acouplefarms.service.organization.OrganizationService;
import com.dev.acouplefarms.service.user.UserService;
import com.google.common.collect.Sets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Properties;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log4j2
@SpringBootApplication
public class ACoupleFarmsApplication implements CommandLineRunner {

  @Autowired private Environment environment;

  public static void main(String[] args) {
    SpringApplication.run(ACoupleFarmsApplication.class, args);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  JavaMailSender javaMailSender() {
    final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setHost(environment.getProperty("mail.host"));
    javaMailSender.setPort(Integer.valueOf(environment.getProperty("mail.port")));
    javaMailSender.setUsername(environment.getProperty("mail.username"));
    javaMailSender.setPassword(environment.getProperty("mail.password"));

    Properties props = javaMailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", environment.getProperty("mail.properties.mail.smtp.auth"));
    props.put(
        "mail.smtp.starttls.enable",
        environment.getProperty("mail.properties.mail.smtp.starttls.enable"));
    props.put("mail.debug", "true");

    return javaMailSender;
  }

  @Autowired private LocationService locationService;
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
    final User user =
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
    final Organization organization =
        organizationService.saveOrganization(
            new Organization(
                null,
                "Andrew Farm",
                "ANDREWFARM",
                "org@atorg.com",
                "123-456-7890",
                Date.from(Instant.now()),
                Date.from(Instant.now()),
                true));
    final Organization joshuaFarm =
        organizationService.saveOrganization(
            new Organization(
                null,
                "Joshua Farm",
                "JOSHUAFARM",
                "org@atorg.com",
                "123-456-7890",
                Date.from(Instant.now()),
                Date.from(Instant.now()),
                true));
    final Organization testOrg =
        organizationService.saveOrganization(
            new Organization(
                null,
                "Test Farm",
                "TESTFARM",
                "test@testorg.com",
                "123-456-7890",
                Date.from(Instant.now()),
                Date.from(Instant.now()),
                true));
    userService.saveUserOrganizationRelation(
        new UserOrgRelation(
            user.getId(),
            organization.getId(),
            Date.from(Instant.now()),
            Date.from(Instant.now()),
            true,
            true));
    userService.saveUserOrganizationRelation(
        new UserOrgRelation(
            user.getId(),
            joshuaFarm.getId(),
            Date.from(Instant.now()),
            Date.from(Instant.now()),
            true,
            true));
    userService.saveUserOrganizationRelation(
        new UserOrgRelation(
            user.getId(),
            testOrg.getId(),
            Date.from(Instant.now()),
            Date.from(Instant.now()),
            false,
            true));
    locationService.saveLocation(
        new Location(
            null,
            organization.getId(),
            "Coop 1",
            "COOP1",
            Date.from(Instant.now()),
            Date.from(Instant.now()),
            true));
    locationService.saveLocation(
        new Location(
            null,
            organization.getId(),
            "Coop 2",
            "COOP2",
            Date.from(Instant.now()),
            Date.from(Instant.now()),
            true));
    locationService.saveLocation(
        new Location(
            null,
            organization.getId(),
            "Coop 3",
            "COOP3",
            Date.from(Instant.now()),
            Date.from(Instant.now()),
            true));
    locationService.saveLocation(
        new Location(
            null,
            organization.getId(),
            "Coop 4",
            "COOP4",
            Date.from(Instant.now()),
            Date.from(Instant.now()),
            true));
    locationService.saveLocation(
        new Location(
            null,
            organization.getId(),
            "Coop 5",
            "COOP5",
            Date.from(Instant.now()),
            Date.from(Instant.now()),
            true));
    locationService.saveLocation(
        new Location(
            null,
            organization.getId(),
            "Coop 6",
            "COOP6",
            Date.from(Instant.now()),
            Date.from(Instant.now()),
            true));
    locationService.saveLocationColumn(
        new LocationColumn(
            null,
            organization.getId(),
            "Food",
            "FOOD",
            Date.from(Instant.now()),
            Date.from(Instant.now()),
            true));
    locationService.saveLocationColumn(
        new LocationColumn(
            null,
            organization.getId(),
            "Water",
            "WATER",
            Date.from(Instant.now()),
            Date.from(Instant.now()),
            true));
    locationService.saveLocationColumn(
        new LocationColumn(
            null,
            organization.getId(),
            "Notes",
            "NOTES",
            Date.from(Instant.now()),
            Date.from(Instant.now()),
            true));
    userService.savePasswordToken(
        new PasswordToken(
            user.getId(),
            "abc",
            Date.from(Instant.now()),
            Date.from(Instant.now().plus(1, ChronoUnit.DAYS)),
            false));
  }
}
