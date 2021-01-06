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
public class CheckoutController extends Base {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/checkout")
    public String checkoutShipping(Model model) {
        return "checkout/checkout";
    }

    @GetMapping("/checkout/review")
    public String checkoutReview(Model model) {
        return "checkout/review";
    }
}
