package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.entities.FavoriteEntity;
import com.sastore.web.entities.OrderEntity;
import com.sastore.web.entities.OrderProducts;
import com.sastore.web.entities.UserEntity;
import com.sastore.web.services.FavoriteService;
import com.sastore.web.services.OrderService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

  @Autowired
  private OrderService orderService;

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

    Long finishedOrders = orderService.getUserFinishedOrdersCount(getLoggedUserId(request));
    model.addAttribute("finishedOrders", finishedOrders);

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
  public String orders(HttpServletRequest request, Model model) {

    List<OrderEntity> orders = orderService.getUserOrders(getLoggedUserId(request));
    model.addAttribute("orders", orders);

    Long finishedOrders = orderService.getUserFinishedOrdersCount(getLoggedUserId(request));
    model.addAttribute("finishedOrders", finishedOrders);

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.profile", null, LocaleContextHolder.getLocale()), "/profile"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.profile.orders", null, LocaleContextHolder.getLocale()), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("globalMenu", "profile");
    model.addAttribute("submenu", "orders");

    return "profile/orders";
  }

  @GetMapping(value = "/profile/orders", params = {"id"})
  public String orderDetails(@RequestParam("id") String orderId, Model model) {

    OrderEntity order = orderService.getOrder(orderId);
    model.addAttribute("order", order);

    Double subtotal = 0.00;

    for (OrderProducts op : order.getOrderProducts()) {
      subtotal += (op.getQuantity() * op.getPricePerUnit());
    }

    Double discount = 0.00;

    model.addAttribute("subtotal", subtotal);
    model.addAttribute("discount", discount);

    model.addAttribute("globalMenu", "profile");
    model.addAttribute("submenu", "orders");

    return "profile/order";
  }

  @GetMapping("/profile/favorites")
  public String favorites(HttpServletRequest request, Model model) {

    List<FavoriteEntity> favorites = favoriteService.getUserFavorites(getLoggedUserId(request));
    model.addAttribute("favorites", favorites);

    Long finishedOrders = orderService.getUserFinishedOrdersCount(getLoggedUserId(request));
    model.addAttribute("finishedOrders", finishedOrders);

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.profile", null, LocaleContextHolder.getLocale()), "/profile"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.profile.favorites", null, LocaleContextHolder.getLocale()), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("globalMenu", "profile");
    model.addAttribute("submenu", "favorites");

    return "profile/favorites";
  }

  @PostMapping(value = "/profile/favorites/create")
  public String createFavorite(@RequestParam("productId") String productId, HttpServletRequest request, Model model) {

    log.debug("Adding product with ID " + productId + " to favorites");

    FavoriteEntity favorite = favoriteService.createFavorite(getLoggedUserId(request), productId);
    request.getSession().setAttribute("favoritesCount", favoriteService.getUserFavoritesCount(getLoggedUserId(request)));
    return "fragments/fragments :: #success";
  }

  @PostMapping(value = "/profile/favorites/delete")
  public String deleteFavorite(@RequestParam("favoriteId") String favoriteId, HttpServletRequest request, Model model) {

    FavoriteEntity deletedFavorite = favoriteService.deleteFavorite(favoriteId);
    request.getSession().setAttribute("favoritesCount", favoriteService.getUserFavoritesCount(getLoggedUserId(request)));
    return "redirect:/profile/favorites";
  }

  @PostMapping(value = "/profile/favorites/remove")
  public String removeFavorite(@RequestParam("productId") String productId, HttpServletRequest request, Model model) {

    log.debug("Removing product with ID " + productId + " fromfavorites");

    FavoriteEntity favorite = favoriteService.removeFavorite(getLoggedUserId(request), productId);
    request.getSession().setAttribute("favoritesCount", favoriteService.getUserFavoritesCount(getLoggedUserId(request)));
    return "fragments/fragments :: #success";
  }
}
