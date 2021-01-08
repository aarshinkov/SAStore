package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Controller
public class AccountController extends Base {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/account")
    public String account(Model model) {

        model.addAttribute("globalMenu", "account");
        model.addAttribute("submenu", "account");

        return "account/account";
    }

    @GetMapping("/account/orders")
    public String orders(Model model) {

        model.addAttribute("globalMenu", "account");
        model.addAttribute("submenu", "orders");

        return "account/orders";
    }

    @GetMapping("/account/order")
    public String orderDetails(@RequestParam("orderId") String orderId, Model model) {

        model.addAttribute("globalMenu", "account");
        model.addAttribute("submenu", "orders");

        return "account/order";
    }

    @GetMapping("/account/wishlist")
    public String wishlist(Model model) {

        model.addAttribute("globalMenu", "account");
        model.addAttribute("submenu", "wishlist");

        return "account/wishlist";
    }

    @GetMapping("/account/addresses")
    public String addresses(Model model) {

        model.addAttribute("globalMenu", "account");
        model.addAttribute("submenu", "addresses");

        return "account/addresses";
    }
}
