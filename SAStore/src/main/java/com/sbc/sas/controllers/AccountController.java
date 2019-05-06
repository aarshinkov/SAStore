package com.sbc.sas.controllers;

import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/account")
public class AccountController
{
  private Logger log = LoggerFactory.getLogger(getClass());
  
  @GetMapping(value = "/settings")
  public String settings(Model model) {
    return "account/settings";
  }
}
