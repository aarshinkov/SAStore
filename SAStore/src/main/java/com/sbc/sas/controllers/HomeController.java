package com.sbc.sas.controllers;

import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController
{
  private Logger log = LoggerFactory.getLogger(this.getClass());
  
  @GetMapping(value = {"/", "/home"})
  public String home() {
    return "home";
  }
}
