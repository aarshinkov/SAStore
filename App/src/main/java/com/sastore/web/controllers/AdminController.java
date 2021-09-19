package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController extends Base {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private ProductService productService;

  @GetMapping("/admin/dashboard")
  public String dashboard(Model model) {

    model.addAttribute("globalMenu", "dashboard");
    return "admin/dashboard";
  }
}
