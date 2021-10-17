package com.sastore.web.security;

import com.sastore.web.entities.UserEntity;
import com.sastore.web.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserService userService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    log.debug("Authenticating...");
    final String email = authentication.getName();
    final String password = authentication.getCredentials().toString();

    UserEntity user = userService.getUserByEmail(email);

    return new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), user.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
