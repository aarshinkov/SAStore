package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    
//    Collections.sort(principals, null);
    
    model.addAttribute("onlineUsers", principals);
    
    return "admin/fragments/fragments :: #onlineUsersList";
  }
}
