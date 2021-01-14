package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

        if (hasSpecialRole()) {
            return "redirect:/admin/dashboard";
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
