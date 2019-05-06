package com.sbc.sas.security;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.savedrequest.*;

public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException
  {
    log.debug("Authentication successful.");

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    SASUser sasUser = (SASUser) auth.getPrincipal();

    SavedRequest savedRequest = (SavedRequest) request.getSession(false).getAttribute("SPRING_SECURITY_SAVED_REQUEST");

    String redirectUrl = request.getContextPath() + "/";

    request.getSession(false).setAttribute("user", "(" + sasUser.getFullName() + ") " + sasUser.getUsername());

    if (savedRequest != null)
    {
      redirectUrl = savedRequest.getRedirectUrl();
    }

    log.debug("Redirecting to page: " + redirectUrl);
    response.sendRedirect(redirectUrl);
  }
}
