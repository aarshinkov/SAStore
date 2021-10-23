package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.comparators.LoggedUserComparator;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
@Controller
public class SystemController extends Base {

  @Autowired
  private SessionRegistry sessionRegistry;

  @GetMapping("/system/onlineUsersList")
  public String loadOnlineUsersList(Model model) {

    List<Object> principals = sessionRegistry.getAllPrincipals();

    Collections.sort(principals, new LoggedUserComparator(LoggedUserComparator.LOGGED_ON));

    model.addAttribute("onlineUsers", principals);

    return "admin/fragments/fragments :: #onlineUsersList";
  }

  @GetMapping("/system/timeToLive")
  @ResponseBody
  public Integer timeToLive(HttpServletRequest request) {
    int maxInactiveInterval = request.getSession().getMaxInactiveInterval();

    return maxInactiveInterval;
  }
}
