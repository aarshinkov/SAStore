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
import org.springframework.web.bind.annotation.*;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Controller
@RequestMapping("/admin")
public class ProductsController extends Base {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductService productService;

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

        model.addAttribute("globalMenu", "products");

        return "admin/products/dashboard";
    }

    @GetMapping("/product/{productId}")
    public String product(@PathVariable("productId") Long productId, Model model) {

        ProductEntity product = productService.getProductByProductId(productId);

        model.addAttribute("product", product);

        model.addAttribute("globalMenu", "products");

        return "admin/products/product";
    }
}
