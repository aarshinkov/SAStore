package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.collections.ObjCollection;
import com.sastore.web.entities.ProductEntity;
import com.sastore.web.filters.ProductFilter;
import com.sastore.web.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController extends Base {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private ProductService productService;

  @GetMapping("/dashboard")
  public String dashboard(Model model) {

    model.addAttribute("globalMenu", "dashboard");
    return "admin/dashboard";
  }

  @GetMapping("/products")
  public String getProducts(@ModelAttribute("filter") ProductFilter filter,
          @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
          @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit, Model model) {

    if (page <= 0) {
      return "redirect:/admin/products?page=1" + filter.getPagingParams();
    }

    if (limit <= 0) {
      return "redirect:/admin/products?limit=10" + filter.getPagingParams();
    }

    ObjCollection<ProductEntity> products = productService.getProducts(page, limit, filter);

    model.addAttribute("products", products);

    String otherParams = "";

    otherParams = "&limit=" + limit;

    model.addAttribute("otherParameters", otherParams);

    model.addAttribute("pageWrapper", products.getPage());
    model.addAttribute("maxPagesPerView", 3);

    model.addAttribute("limit", limit);

    model.addAttribute("submenu", "products");
    model.addAttribute("globalMenu", "products");

    return "admin/products/dashboard";
  }

  @GetMapping(value = "/products", params = {"id"})
  public String product(@RequestParam(name = "id") Long productId, Model model) {

    ProductEntity product = productService.getProductByProductId(productId);

    model.addAttribute("product", product);

    model.addAttribute("globalMenu", "products");

    return "admin/products/product";
  }
}
