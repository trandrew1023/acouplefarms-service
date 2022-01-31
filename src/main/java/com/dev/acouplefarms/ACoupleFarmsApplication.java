package com.dev.acouplefarms;

import java.util.Properties;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ACoupleFarmsApplication {

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
}
