package com.sastore.web;

import com.sastore.web.api.EcontApi;
import com.sastore.web.beans.PasswordValidator;
import com.sastore.web.beans.Version;
import com.sastore.web.security.CustomAuthSuccessHandler;
import com.sastore.web.security.CustomAuthenticationProvider;
import com.sastore.web.uploader.Uploader;
import java.nio.charset.StandardCharsets;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.client.RestTemplate;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@SpringBootApplication
public class SaStoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(SaStoreApplication.class, args);
  }

  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    return restTemplate;
  }

  @Bean
  public Version version() {
    return new Version();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Bean
  public PasswordValidator passwordValidator() {
    return new PasswordValidator();
  }

  @Bean
  public Uploader uploader() {
    return new Uploader();
  }

//    @Bean
//    public SecurityChecks securityChecks() {
//        return new SecurityChecks();
//    }
  @Bean
  public EcontApi econtApi() {
    return new EcontApi();
  }

//  @Bean
//  public AuthenticationSuccessHandler authenticationSuccessHandler() {
//    return new CustomAuthSuccessHandler();
//  }
  @Bean
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }
}
