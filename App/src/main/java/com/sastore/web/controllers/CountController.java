package com.sastore.web.controllers;

import com.sastore.web.enums.ProductStatuses;
import com.sastore.web.enums.Roles;
import com.sastore.web.services.OrderService;
import com.sastore.web.services.ProductService;
import com.sastore.web.services.UserService;
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
public class CountController {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserService userService;

  @Autowired
  private ProductService productService;
  
  @Autowired
  private OrderService orderService;

  @GetMapping("/count/users")
  public String getNormalUsersCount(Model model) {

    model.addAttribute("count", userService.getUsersCountByRole(Roles.USER.getRole()));

    return "admin/fragments/fragments :: #countUsers";
  }

  @GetMapping("/count/products/active")
  public String getActiveProductsCount(Model model) {

    model.addAttribute("count", productService.getProductsCountByStatus(ProductStatuses.ACTIVE.getStatus()));

    return "admin/fragments/fragments :: #countActiveProducts";
  }

  @GetMapping("/count/orders/finished")
  public String getFinishedOrdersCount(Model model) {

    Long finishedOrdersCount = orderService.getFinishedOrdersCount();
    
    model.addAttribute("count", finishedOrdersCount);

    return "admin/fragments/fragments :: #countFinishedOrders";
  }
}
