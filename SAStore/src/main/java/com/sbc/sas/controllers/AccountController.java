package com.sbc.sas.controllers;

import com.sbc.sas.base.*;
import com.sbc.sas.entity.*;
import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/account")
public class AccountController extends Base
{
  private Logger log = LoggerFactory.getLogger(getClass());
  
  private static final String GLOBAL_MENU = "account";
  
  @GetMapping(value = "/personal")
  public String viewPersonal(Model model) {
    
    User user = getUser();
    
    model.addAttribute("user", user);
    model.addAttribute("globalMenu", GLOBAL_MENU);
    model.addAttribute("submenu", "personal");
    
    return "account/settings";
  }
  
  @PostMapping(value = "/personal")
  public String savePersonal(Model model) {
    log.debug("In post method personal");
    
    return "redirect:/personal";
  }
}
