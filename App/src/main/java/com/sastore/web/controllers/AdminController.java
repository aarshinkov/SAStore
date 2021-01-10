package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.collections.ObjCollection;
import com.sastore.web.entities.UserEntity;
import com.sastore.web.filters.UserFilter;
import com.sastore.web.services.UserService;
import com.sastore.web.utils.Breadcrumb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController extends Base {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("globalMenu", "dashboard");
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String users(Model model) {

        UserFilter userFilter = new UserFilter();

        ObjCollection<UserEntity> users = userService.getUsers(1, 15, userFilter);

        model.addAttribute("users", users);

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

        model.addAttribute("globalMenu", "users");

        return "admin/users/user";
    }
}
