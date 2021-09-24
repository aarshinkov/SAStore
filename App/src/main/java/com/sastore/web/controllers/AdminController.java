package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.services.ProductService;
import com.sastore.web.utils.Breadcrumb;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.dashboard", null, LocaleContextHolder.getLocale()), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("pageTitleType", "welcome");
    model.addAttribute("globalMenu", "dashboard");

    return "admin/dashboard";
  }
}
