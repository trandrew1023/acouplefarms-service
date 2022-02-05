package com.dev.acouplefarms.resource;

import static com.dev.acouplefarms.util.StringUtil.scrubString;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dev.acouplefarms.models.organization.OrganizationResponse;
import com.dev.acouplefarms.models.relation.UserOrgRelation;
import com.dev.acouplefarms.models.user.AuthorityRole;
import com.dev.acouplefarms.models.user.User;
import com.dev.acouplefarms.models.user.UserResponse;
import com.dev.acouplefarms.service.organization.OrganizationService;
import com.dev.acouplefarms.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/user")
public class UserResource {

  private static final String AUTHORIZATION_HEADER_KEY = "Bearer-";

  @Autowired private final UserService userService;
  @Autowired private final OrganizationService organizationService;
  @Autowired private final Environment environment;

  public UserResource(
      final UserService userService,
      final OrganizationService organizationService,
      final Environment environment) {
    this.userService = userService;
    this.organizationService = organizationService;
    this.environment = environment;
  }

  @GetMapping("/{username}")
  public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
    return ResponseEntity.ok().body(new UserResponse(userService.getUserByUsername(username)));
  }

  @GetMapping("/search/{username}")
  public ResponseEntity<Set<UserResponse>> searchUsersByUsername(@PathVariable String username) {
    final Set<UserResponse> users =
        userService.searchByUsername(username).stream()
            .map(UserResponse::new)
            .collect(Collectors.toSet());
    return ResponseEntity.ok().body(users);
  }

  @GetMapping("")
  public ResponseEntity<UserResponse> getUser(final HttpServletRequest request) {
    final UserResponse userResponse =
        new UserResponse(userService.getUserByUsername(request.getUserPrincipal().getName()));
    return ResponseEntity.ok().body(userResponse);
  }

  @GetMapping("/token/refresh")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    final String authorizationHeader = request.getHeader(AUTHORIZATION);
    if (authorizationHeader != null && authorizationHeader.startsWith(AUTHORIZATION_HEADER_KEY)) {
      try {
        final String refreshToken =
            authorizationHeader.substring(AUTHORIZATION_HEADER_KEY.length());
        final Algorithm algorithm =
            Algorithm.HMAC256(environment.getProperty("spring.secret").getBytes());
        final JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        final DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
        final String username = decodedJWT.getSubject();
        final User user = userService.getUserByUsername(username);
        final String accessToken =
            JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim(
                    "grantedAuthority",
                    user.getGrantedAuthorities().stream().map(AuthorityRole::getRoleName).toList())
                .sign(algorithm);
        final Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
      } catch (Exception e) {
        response.setHeader("error", e.getMessage());
        response.setStatus(UNAUTHORIZED.value());
        final Map<String, String> errors = new HashMap<>();
        errors.put("error_message", e.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), errors);
      }
    } else {
      throw new RuntimeException("Refresh token is missing");
    }
  }

  @PostMapping("")
  public ResponseEntity<?> saveUser(@RequestBody final User user) {
    final String username = user.getUsername();
    final String email = user.getEmail();
    final Pattern alphaNumericPattern = Pattern.compile("^[a-zA-Z0-9]*$");
    final Matcher alphaNumericMatcher = alphaNumericPattern.matcher(username);
    if (!alphaNumericMatcher.matches()
        || username.isBlank()
        || user.getFirstname().isBlank()
        || user.getLastname().isBlank()) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if (userService.getUserByUsername(username) != null) {
      return new ResponseEntity<>("username", HttpStatus.CONFLICT);
    }
    if (userService.getUserByEmail(email) != null) {
      return new ResponseEntity<>("email", HttpStatus.CONFLICT);
    }
    user.setCreateDate(Date.from(Instant.now()));
    user.setUpdateDate(Date.from(Instant.now()));
    user.setUsernameKey(scrubString(username));
    user.setActive(false);
    userService.saveUser(user);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/organizations")
  public ResponseEntity<Set<OrganizationResponse>> getUserOrganizations(
      final HttpServletRequest request) {
    final Long userId = userService.getUserByUsername(request.getUserPrincipal().getName()).getId();
    final Set<OrganizationResponse> organizations =
        organizationService.getOrganizationsByUserId(userId);
    return new ResponseEntity<>(organizations, HttpStatus.OK);
  }

  @PostMapping("/organization")
  public ResponseEntity<?> saveUserOrganizationRelation(
      @RequestBody final UserOrgRelation userOrgRelation) {
    final Long userId = userOrgRelation.getUserId();
    final Long organizationId = userOrgRelation.getOrganizationId();
    try {
      userService.getUserById(userId);
      organizationService.getOrganizationById(organizationId);
    } catch (final EntityNotFoundException entityNotFoundException) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    userService.saveUserOrganizationRelation(userOrgRelation);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
