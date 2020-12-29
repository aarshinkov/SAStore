package com.sastore.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping({"/", "/home"})
    public String home() {
        return "home";
    }
}
