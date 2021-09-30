package com.sastore.web.controllers;

import com.sastore.web.entities.RoleEntity;
import com.sastore.web.base.Base;
import com.sastore.web.collections.ObjCollection;
import com.sastore.web.entities.UserEntity;
import com.sastore.web.enums.Roles;
import com.sastore.web.filters.UserFilter;
import com.sastore.web.services.RoleService;
import com.sastore.web.services.UserService;
import com.sastore.web.utils.Breadcrumb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Controller
public class UsersController extends Base {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserService userService;

  @Autowired
  private RoleService roleService;

  @GetMapping("/admin/users")
  public String users(@ModelAttribute("filter") UserFilter filter,
          @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
          @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit, Model model) {

    if (page <= 0) {
      return "redirect:/admin/users?page=1" + filter.getPagingParams();
    }

    if (limit <= 0) {
      return "redirect:/admin/users?limit=10" + filter.getPagingParams();
    }

    boolean isSearched = false;

    if (!filter.isFilterEmpty()) {
      isSearched = true;
    }

    model.addAttribute("isSearched", isSearched);

    ObjCollection<UserEntity> users = userService.getUsers(page, limit, filter);

    model.addAttribute("users", users);

    String otherParams = "&limit=" + limit;

    model.addAttribute("otherParameters", otherParams);

    model.addAttribute("pageWrapper", users.getPage());
    model.addAttribute("maxPagesPerView", 3);

    model.addAttribute("limit", limit);

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.managing.users", null, LocaleContextHolder.getLocale()), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("pageTitle", getMessage("users.header"));
    model.addAttribute("globalMenu", "users");

    return "admin/users/dashboard";
  }

  @GetMapping(value = "/admin/users", params = {"id"})
  public String user(@RequestParam("id") String userId, Model model) {

    UserEntity user = userService.getUserByUserId(userId);

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.managing.users", null, LocaleContextHolder.getLocale()), "/admin/users"));
    breadcrumbs.add(new Breadcrumb(user.getFullName(), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("user", user);

    List<RoleEntity> roles = roleService.getRoles();

    List<RoleEntity> unassignedRoles = new ArrayList<>();

    model.addAttribute("roles", roles);

    for (RoleEntity role : roles) {
      if (!user.getRoles().contains(role)) {
        unassignedRoles.add(role);
      }
    }

    model.addAttribute("unassignedRoles", unassignedRoles);

    model.addAttribute("pageTitle", user.getFullName());
    model.addAttribute("globalMenu", "users");

    return "admin/users/user";
  }

  @PostMapping("/admin/user/role/add")
  public String addRole(@RequestParam("userId") String userId,
          @RequestParam("rolename") String rolename, RedirectAttributes redirectAttributes) {

    try {
      Roles role = Roles.valueOf(rolename.toUpperCase());

      UserEntity updatedUser = userService.addRole(userId, role);

      //redirectAttributes.addFlashAttribute("msgSuccess", "");
    } catch (Exception e) {
      //redirectAttributes.addFlashAttribute("msgError", "");
    }

    return "redirect:/admin/users?id=" + userId;
  }

  @PostMapping("/admin/user/role/remove")
  public String removeRole(@RequestParam("userId") String userId,
          @RequestParam("rolename") String rolename, RedirectAttributes redirectAttributes) {

    try {
      Roles role = Roles.valueOf(rolename.toUpperCase());

      UserEntity updatedUser = userService.removeRole(userId, role);

      //redirectAttributes.addFlashAttribute("msgSuccess", "");
    } catch (Exception e) {
      //redirectAttributes.addFlashAttribute("msgError", "");
    }

    return "redirect:/admin/users?id=" + userId;
  }

  @PostMapping("/admin/users/remove")
  public String removeUser(@RequestParam("userId") String userId, RedirectAttributes redirectAttributes) {

    boolean result = userService.removeUser(userId);

    if (result) {

    }

    return "redirect:/admin/users";
  }

  @GetMapping("/admin/users/count/admins")
  public String getAdminsUsersCount(Model model) {

    model.addAttribute("count", userService.getUsersCountByRole(Roles.ADMIN.getRole()));

    return "admin/users/fragments :: #adminCount";
  }

  @GetMapping("/admin/users/count/sales")
  public String getSalesUsersCount(Model model) {

    model.addAttribute("count", userService.getUsersCountByRole(Roles.SALES.getRole()));

    return "admin/users/fragments :: #salesCount";
  }

  @GetMapping("/admin/users/count/users")
  public String getNormalUsersCount(Model model) {

    model.addAttribute("count", userService.getUsersCountByRole(Roles.USER.getRole()));

    return "admin/users/fragments :: #usersCount";
  }

  @GetMapping("/admin/users/count/products")
  public String getProductsUsersCount(Model model) {

    model.addAttribute("count", userService.getUsersCountByRole(Roles.PRODUCTS.getRole()));

    return "admin/users/fragments :: #productsCount";
  }

  @GetMapping("/admin/users/count/orders")
  public String getOrdersUsersCount(Model model) {

    model.addAttribute("count", userService.getUsersCountByRole(Roles.ORDERS.getRole()));

    return "admin/users/fragments :: #ordersCount";
  }
}
