package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.collections.ObjCollection;
import com.sastore.web.entities.ProductEntity;
import com.sastore.web.services.ProductService;
import com.sastore.web.filters.ProductFilter;
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
public class HomeController extends Base {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private ProductService productService;

  @GetMapping({"/", "/home"})
  public String home(Model model) {

    if (hasSpecialRole()) {
      return "redirect:/admin/dashboard";
    }

    ObjCollection<ProductEntity> products = productService.getProducts(1, 10, new ProductFilter());

    model.addAttribute("products", products);

    String otherParams = "";

//        otherParams = "&limit=" + limit;
    model.addAttribute("otherParameters", otherParams);

    model.addAttribute("pageWrapper", products.getPage());
    model.addAttribute("maxPagesPerView", 3);

    model.addAttribute("globalMenu", "home");

    return "forward:/products";
  }
}
