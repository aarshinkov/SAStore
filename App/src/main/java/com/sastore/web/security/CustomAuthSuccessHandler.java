package com.sastore.web.security;

import com.sastore.web.domain.*;
import com.sastore.web.entities.*;
import com.sastore.web.repositories.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final RequestCache requestCache = new HttpSessionRequestCache();

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private UsersRepository usersRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

    log.debug("Authentication successful");

    HttpSession session = request.getSession();

    String email = authentication.getName();

    UserEntity user = usersRepository.findByEmail(email);

    NameDomain names = NameDomain.builder().firstName(user.getFirstName()).lastName(user.getLastName()).build();

    session.setAttribute("user", names);
    session.setAttribute("userId", user.getUserId());
    session.setAttribute("email", email);
    session.setAttribute("avatar", user.getAvatar());
    session.setAttribute("roles", user.getRoles());
    session.setAttribute("createdOn", user.getCreatedOn());

    SavedRequest savedRequest = requestCache.getRequest(request, response);

    if (savedRequest == null) {
      super.onAuthenticationSuccess(request, response, authentication);

      return;
    }

    String targetUrlParameter = getTargetUrlParameter();
    if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
      requestCache.removeRequest(request, response);
      super.onAuthenticationSuccess(request, response, authentication);

      return;
    }

    clearAuthenticationAttributes(request);

    // Use the DefaultSavedRequest URL
    String targetUrl = savedRequest.getRedirectUrl();
    logger.debug("Redirecting to url: " + targetUrl);
    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }
}
