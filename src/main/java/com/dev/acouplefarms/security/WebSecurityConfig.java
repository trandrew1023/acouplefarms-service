package com.dev.acouplefarms.security;

import com.dev.acouplefarms.filter.AuthFilter;
import com.dev.acouplefarms.filter.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private final Environment environment;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserDetailsService userDetailsService;

  @Override
  protected void configure(final AuthenticationManagerBuilder authenticationManagerBuilder)
      throws Exception {
    authenticationManagerBuilder
        .userDetailsService(userDetailsService)
        .passwordEncoder(bCryptPasswordEncoder);
  }

  @Override
  protected void configure(final HttpSecurity httpSecurity) throws Exception {
    final AuthFilter authFilter = new AuthFilter(authenticationManagerBean(), environment);

    httpSecurity
        .csrf()
        .disable()
        .headers()
        .frameOptions()
        .disable()
        .and()
        .cors()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/h2-console/**")
        .permitAll()
        .antMatchers("/login")
        .permitAll()
        .antMatchers("/user/email/reset-password")
        .permitAll()
        .antMatchers("/user/reset-password")
        .permitAll()
        .antMatchers("/user/reset-token")
        .permitAll()
        .antMatchers("/user/token/refresh")
        .permitAll()
        .antMatchers("/user")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .addFilter(authFilter)
        .addFilterBefore(
            new AuthorizationFilter(environment), UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }
}
