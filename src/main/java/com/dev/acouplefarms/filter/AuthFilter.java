package com.dev.acouplefarms.filter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Log4j2
public class AuthFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final Environment environment;

  public AuthFilter(
      final AuthenticationManager authenticationManager, final Environment environment) {
    this.authenticationManager = authenticationManager;
    this.environment = environment;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    final UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(
            request.getParameter("username"), request.getParameter("password"));
    return authenticationManager.authenticate(authToken);
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException {
    final User user = (User) authResult.getPrincipal();
    final Algorithm algorithm =
        Algorithm.HMAC256(environment.getProperty("spring.secret").getBytes());
    final String accessToken =
        JWT.create()
            .withSubject(user.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
            .withIssuer(request.getRequestURL().toString())
            .withClaim(
                "grantedAuthority",
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
            .sign(algorithm);
    final String refreshToken =
        JWT.create()
            .withSubject(user.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 60 * 60 * 1000))
            .withIssuer(request.getRequestURL().toString())
            .sign(algorithm);
    final Map<String, String> tokens = new HashMap<>();
    tokens.put("access_token", accessToken);
    tokens.put("refresh_token", refreshToken);
    response.setContentType(APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
  }

  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    super.unsuccessfulAuthentication(request, response, failed);
  }
}
