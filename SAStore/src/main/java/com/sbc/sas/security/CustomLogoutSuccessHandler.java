package com.sbc.sas.security;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.logout.*;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler
{

  private Logger log = LoggerFactory.getLogger(this.getClass());

  public CustomLogoutSuccessHandler(String defaultTargetURL)
  {
    this.setDefaultTargetUrl(defaultTargetURL);
  }

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
  {
    super.onLogoutSuccess(request, response, authentication);
  }
}
