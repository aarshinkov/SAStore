package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.entities.ProductEntity;
import com.sastore.web.services.ProductService;
import static com.sastore.web.utils.AppConstants.FREE_SHIPPING;
import static com.sastore.web.utils.AppConstants.SHIPPING;
import static com.sastore.web.utils.AppConstants.SHIPPING_FREE_AFTER;
import com.sastore.web.utils.Breadcrumb;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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

  @Autowired
  private ProductService productService;

  @GetMapping("/cart")
  public String cart(HttpServletRequest request, Model model) {

	log.debug("Session ID: " + request.getSession(true).getId());

	List<String> cartProducts = new ArrayList<>();
	List<ProductEntity> products = new ArrayList<>();

	Double totalPrice = 0.00;
	Double totalDiscount = 0.00;

	for (Cookie cookie : request.getCookies()) {
	  if (cookie.getName().contains("product_")) {
		log.debug("Product ID: " + cookie.getValue());
		ProductEntity product = productService.getProductByProductId(cookie.getValue());
		totalPrice += product.getPrice();
		totalDiscount += product.getDiscount();
		products.add(product);
	  }
	}

	model.addAttribute("products", products);
	model.addAttribute("totalPrice", totalPrice);

	if (totalPrice - totalDiscount >= SHIPPING_FREE_AFTER) {
	  model.addAttribute("shipping", FREE_SHIPPING);
	} else {
	  model.addAttribute("shipping", SHIPPING);
	}

	model.addAttribute("totalDiscount", totalDiscount);

	List<Breadcrumb> breadcrumbs = new ArrayList<>();
	breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
	if (isLoggedIn()) {
	  breadcrumbs.add(new Breadcrumb(getMessage("nav.profile", null, LocaleContextHolder.getLocale()), "/profile"));
	}
	breadcrumbs.add(new Breadcrumb(getMessage("nav.cart", null, LocaleContextHolder.getLocale()), null));
	model.addAttribute("breadcrumbs", breadcrumbs);

	return "cart";
  }
}
