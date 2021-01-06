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
public class CartController extends Base {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/cart")
    public String cart(Model model) {
        return "cart";
    }
}
