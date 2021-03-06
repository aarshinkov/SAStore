package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.collections.ObjCollection;
import com.sastore.web.entities.RoleEntity;
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

@Controller
public class AdminController extends Base {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("globalMenu", "dashboard");
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String users(@ModelAttribute("filter") UserFilter filter,
                        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                        @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit, Model model) {

        ObjCollection<UserEntity> users = userService.getUsers(page, limit, filter);

        model.addAttribute("users", users);

        String otherParams = "";

        if (limit != null && limit > 0) {
            otherParams = "&limit=" + limit;
        }

//        if (!StringUtils.isEmpty(category))
//        {
//            otherParams += "&cat=" + category;
//        }

        model.addAttribute("otherParameters", otherParams);

        model.addAttribute("pageWrapper", users.getPage());
        model.addAttribute("maxPagesPerView", 3);

        model.addAttribute("limit", limit);

        model.addAttribute("globalMenu", "users");

        return "admin/users/dashboard";
    }

    @GetMapping("/user/{userId}")
    public String user(@PathVariable("userId") String userId, Model model) {

        UserEntity user = userService.getUserByUserId(userId);

        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
        breadcrumbs.add(new Breadcrumb("Users", "/users"));
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

        model.addAttribute("globalMenu", "users");

        return "admin/users/user";
    }

    @PostMapping("/user/role/add")
    public String addRole(@RequestParam("userId") String userId,
                          @RequestParam("rolename") String rolename, RedirectAttributes redirectAttributes) {

        try {
            Roles role = Roles.valueOf(rolename.toUpperCase());

            UserEntity updatedUser = userService.addRole(userId, role);

            //redirectAttributes.addFlashAttribute("msgSuccess", "");
        } catch (Exception e) {
            //redirectAttributes.addFlashAttribute("msgError", "");
        }

        return "redirect:/user/" + userId;
    }

    @PostMapping("/user/role/remove")
    public String removeRole(@RequestParam("userId") String userId,
                             @RequestParam("rolename") String rolename, RedirectAttributes redirectAttributes) {

        try {
            Roles role = Roles.valueOf(rolename.toUpperCase());

            UserEntity updatedUser = userService.removeRole(userId, role);

            //redirectAttributes.addFlashAttribute("msgSuccess", "");
        } catch (Exception e) {
            //redirectAttributes.addFlashAttribute("msgError", "");
        }

        return "redirect:/user/" + userId;
    }

    @GetMapping("/count/admins")
    public String getAdminsCount(Model model) {

        model.addAttribute("count", userService.getUsersCountByRole(Roles.ADMIN.getRole()));

        return "admin/users/fragments :: #adminCount";
    }

    @GetMapping("/count/sales")
    public String getSalesCount(Model model) {

        model.addAttribute("count", userService.getUsersCountByRole(Roles.SALES.getRole()));

        return "admin/users/fragments :: #salesCount";
    }

    @GetMapping("/count/users")
    public String getUsersCount(Model model) {

        model.addAttribute("count", userService.getUsersCountByRole(Roles.USER.getRole()));

        return "admin/users/fragments :: #usersCount";
    }

    private List<RoleEntity> getUnassignedRoles(List<RoleEntity> roles, List<RoleEntity> userRoles) {
        List<String> auxRoles = new ArrayList<>();
        List<String> auxUserRoles = new ArrayList<>();

        for (RoleEntity role : roles) {
            auxRoles.add(role.getRolename());
        }

        for (RoleEntity userRole : userRoles) {
            auxUserRoles.add(userRole.getRolename());
        }

        List<RoleEntity> unassignedRoles = new ArrayList<>();

        for (String role : auxRoles) {
            if (!auxUserRoles.contains(role)) {
                unassignedRoles.add(new RoleEntity(role));
            }
        }

        return unassignedRoles;
    }
}
