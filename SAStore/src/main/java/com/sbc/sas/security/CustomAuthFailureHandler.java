package com.sbc.sas.security;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.*;

public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler
{

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException
  {

    log.debug("Authentication failed: " + ex.getMessage());
    String username = request.getParameter("username");
    log.debug("Username: " + username);

    setDefaultFailureUrl("/login?error");

    super.onAuthenticationFailure(request, response, ex);
  }

}
