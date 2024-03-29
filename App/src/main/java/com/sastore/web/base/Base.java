package com.sastore.web.base;

import com.sastore.web.enums.Roles;
import com.sastore.web.security.LoggedUser;
import com.sastore.web.security.SecurityChecks;
import com.sastore.web.services.SystemService;
import com.sastore.web.utils.AppConstants;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Component
public class Base {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  protected SecurityChecks sc;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private SystemService systemService;

  protected String getLoggedUserId(HttpServletRequest request) {
    LoggedUser loggedUser = (LoggedUser) systemService.getSessionAttribute(request, AppConstants.SESSION_LOGGED_USER);
    return loggedUser.getUserId();
  }

  protected Boolean hasSpecialRole() {
    return sc.hasRole(Roles.ADMIN.getRole()) || sc.hasRole(Roles.SALES.getRole()) || sc.hasRole(Roles.PRODUCTS.getRole()) || sc.hasRole(Roles.ORDERS.getRole());
  }

  protected String getMessage(String key) {
    return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
  }

  protected String getMessage(String key, Object... params) {
    return messageSource.getMessage(key, params, LocaleContextHolder.getLocale());
  }

  protected boolean isLoggedIn() {
    return sc.isLoggedIn();
  }

  public String getEnvironment() {
    return getMessage("env");
  }

  protected String getEcontApiUrl() {
    // Excluding '/' 
    return getMessage("env.econt.base");
  }
}
