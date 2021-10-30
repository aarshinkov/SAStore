package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.entities.OrderEntity;
import com.sastore.web.entities.OrderProducts;
import com.sastore.web.repositories.OrdersRepository;
import com.sastore.web.utils.Breadcrumb;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Controller
public class OrdersController extends Base {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private OrdersRepository ordersRepository;

  //ORDERS
  @GetMapping("/admin/orders")
  public String getAdminOrders(HttpServletRequest request, Model model) {

    List<OrderEntity> orders = ordersRepository.findAll();
    model.addAttribute("orders", orders);

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.managing.orders", null, LocaleContextHolder.getLocale()), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("pageTitle", getMessage("orders.title"));
    model.addAttribute("globalMenu", "orders");
    model.addAttribute("submenu", "orders");

    return "admin/orders/dashboard";
  }

  @GetMapping(value = "/admin/orders", params = {"id"})
  public String getAdminOrder(@RequestParam("id") Integer orderId, HttpServletRequest request, Model model) {

    OrderEntity order = ordersRepository.findByOrderId(orderId);
    model.addAttribute("order", order);

    Double subtotal = 0.00;

    for (OrderProducts op : order.getOrderProducts()) {
      subtotal += (op.getQuantity() * op.getPricePerUnit());
    }

    Double discount = 0.00;

    model.addAttribute("subtotal", subtotal);
    model.addAttribute("discount", discount);

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.managing.orders", null, LocaleContextHolder.getLocale()), "/admin/orders"));
    breadcrumbs.add(new Breadcrumb("Order #" + order.getOrderId(), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("pageTitle", getMessage("orders.title"));
    model.addAttribute("globalMenu", "orders");
    model.addAttribute("submenu", "orders");

    return "admin/orders/order";
  }
}
