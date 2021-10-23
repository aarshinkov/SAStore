package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.utils.Breadcrumb;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Controller
public class CartController extends Base {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @GetMapping("/cart")
  public String cart(HttpServletRequest request, Model model) {

    log.debug("request.session: " + request.getSession(true).getId());

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    if (isLoggedIn()) {
      breadcrumbs.add(new Breadcrumb(getMessage("nav.profile", null, LocaleContextHolder.getLocale()), "/profile"));
    }
    breadcrumbs.add(new Breadcrumb(getMessage("nav.cart", null, LocaleContextHolder.getLocale()), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    return "cart";
  }
}
