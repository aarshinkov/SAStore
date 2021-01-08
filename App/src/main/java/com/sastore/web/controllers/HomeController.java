package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.enums.Roles;
import com.sastore.web.security.SecurityChecks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Controller
public class HomeController extends Base {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping({"/", "/home"})
    public String home(Model model) {

        if (sc.hasRole(Roles.ADMIN.getRole()) || sc.hasRole(Roles.SALES.getRole())) {
            return "redirect:/dashboard";
        }

//        if (SecurityContextHolder.getContext()) {
//            log.debug("LOGGED IN");
//        } else {
//            log.debug("NOT LOGGED IN");
//        }


        model.addAttribute("globalMenu", "home");
        return "home";
    }
}
