package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.utils.Breadcrumb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Controller
public class ProfileController extends Base {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @GetMapping("/profile")
  public String profile(Model model) {

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.profile", null, LocaleContextHolder.getLocale()), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("globalMenu", "profile");
    model.addAttribute("submenu", "profile");

    if (hasSpecialRole()) {
      return "admin/profile/profile";
    }

    return "profile/profile";
  }

  @GetMapping("/profile/orders")
  public String orders(Model model) {

    model.addAttribute("globalMenu", "profile");
    model.addAttribute("submenu", "orders");

    return "profile/orders";
  }

  @GetMapping("/profile/order")
  public String orderDetails(@RequestParam("orderId") String orderId, Model model) {

    model.addAttribute("globalMenu", "profile");
    model.addAttribute("submenu", "orders");

    return "profile/order";
  }

  @GetMapping("/profile/wishlist")
  public String wishlist(Model model) {

    model.addAttribute("globalMenu", "profile");
    model.addAttribute("submenu", "wishlist");

    return "profile/wishlist";
  }

  @GetMapping("/profile/addresses")
  public String addresses(Model model) {

    model.addAttribute("globalMenu", "profile");
    model.addAttribute("submenu", "addresses");

    return "profile/addresses";
  }
}
