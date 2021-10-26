package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.utils.Breadcrumb;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Controller
public class OrdersController extends Base {

  private final Logger log = LoggerFactory.getLogger(getClass());

  //ORDERS
  @GetMapping("/admin/orders")
  public String getAdminOrders(HttpServletRequest request, Model model) {
    
    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.managing.orders", null, LocaleContextHolder.getLocale()), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("pageTitle", getMessage("orders.title"));
    model.addAttribute("globalMenu", "orders");
    model.addAttribute("submenu", "orders");
    
    return "admin/orders/dashboard";
  }
}
