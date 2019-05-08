package com.sbc.sas.controllers;

import com.sbc.sas.base.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController extends Base {

    @GetMapping(value = "/login")
    public String prepareLogin(Model model) {
        model.addAttribute("globalMenu", "login");
        
        return "login/login";
    }

}
