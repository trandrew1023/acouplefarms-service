package com.dev.acouplefarms.filter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthorizationFilter extends OncePerRequestFilter {

  private static final String AUTHORIZATION_HEADER_KEY = "Bearer-";
  @Autowired private final Environment environment;

  public AuthorizationFilter(final Environment environment) {
    this.environment = environment;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (request.getServletPath().equals("/login")
        || request.getServletPath().equals("/user/token/refresh")) {
      filterChain.doFilter(request, response);
    } else {
      final String authorizationHeader = request.getHeader(AUTHORIZATION);
      if (authorizationHeader != null && authorizationHeader.startsWith(AUTHORIZATION_HEADER_KEY)) {
        try {
          final String token = authorizationHeader.substring(AUTHORIZATION_HEADER_KEY.length());
          final Algorithm algorithm =
              Algorithm.HMAC256(environment.getProperty("spring.secret").getBytes());
          final JWTVerifier jwtVerifier = JWT.require(algorithm).build();
          final DecodedJWT decodedJWT = jwtVerifier.verify(token);
          final String username = decodedJWT.getSubject();
          final Collection<SimpleGrantedAuthority> grantedAuthorities =
              decodedJWT.getClaim("grantedAuthority").asList(String.class).stream()
                  .map(SimpleGrantedAuthority::new)
                  .collect(Collectors.toSet());
          final UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
          filterChain.doFilter(request, response);
        } catch (Exception e) {
          response.setHeader("error", e.getMessage());
          response.setStatus(UNAUTHORIZED.value());
          final Map<String, String> errors = new HashMap<>();
          errors.put("error_message", e.getMessage());
          response.setContentType(APPLICATION_JSON_VALUE);
          new ObjectMapper().writeValue(response.getOutputStream(), errors);
        }
      } else {
        filterChain.doFilter(request, response);
      }
    }
  }
}
