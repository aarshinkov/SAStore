package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.entities.FavoriteEntity;
import com.sastore.web.entities.UserEntity;
import com.sastore.web.services.FavoriteService;
import com.sastore.web.services.UserService;
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
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Controller
public class ProfileController extends Base {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserService userService;

  @Autowired
  private FavoriteService favoriteService;

  @GetMapping("/profile")
  public String profile(HttpServletRequest request, Model model) {

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.profile", null, LocaleContextHolder.getLocale()), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("globalMenu", "profile");
    model.addAttribute("submenu", "profile");

    if (hasSpecialRole()) {

      UserEntity user = userService.getUserByUserId(getLoggedUserId(request));

      model.addAttribute("pageTitle", user.getFullName());
      return "admin/profile/profile";
    }

    return "profile/profile";
  }

  @GetMapping("/profile/roles")
  public String roles(HttpServletRequest request, Model model) {

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.profile", null, LocaleContextHolder.getLocale()), "/profile"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.profile.roles", null, LocaleContextHolder.getLocale()), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("pageTitle", getMessage("roles.header"));
    model.addAttribute("globalMenu", "profile");
    model.addAttribute("submenu", "roles");

    UserEntity user = userService.getUserByUserId(getLoggedUserId(request));

    model.addAttribute("roles", user.getRoles());

    return "admin/profile/roles";
  }

  @GetMapping("/profile/orders")
  public String orders(Model model) {

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.profile", null, LocaleContextHolder.getLocale()), "/profile"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.profile.orders", null, LocaleContextHolder.getLocale()), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

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

  @GetMapping("/profile/favorites")
  public String favorites(HttpServletRequest request, Model model) {

    List<FavoriteEntity> favorites = favoriteService.getUserFavorites(getLoggedUserId(request));
    
    model.addAttribute("favorites", favorites);

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.profile", null, LocaleContextHolder.getLocale()), "/profile"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.profile.favorites", null, LocaleContextHolder.getLocale()), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("globalMenu", "profile");
    model.addAttribute("submenu", "favorites");

    return "profile/favorites";
  }
}
