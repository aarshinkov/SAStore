package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.collections.ObjCollection;
import com.sastore.web.entities.ProductEntity;
import com.sastore.web.models.ProductCreateModel;
import com.sastore.web.filters.ProductFilter;
import com.sastore.web.services.ProductService;
import com.sastore.web.uploader.Uploader;
import com.sastore.web.uploader.domain.FileName;
import com.sastore.web.uploader.domain.ImageFolder;
import java.io.IOException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

  @Autowired
  private Uploader uploader;

  @GetMapping("/products/pending")
  public String pendingProducts(Model model) {

    model.addAttribute("submenu", "pending");
    model.addAttribute("globalMenu", "products");

    return "admin/products/pending";
  }

  @GetMapping("/products/new")
  public String prepareNewProduct(Model model) {

    model.addAttribute("pcm", new ProductCreateModel());

    model.addAttribute("globalMenu", "products");

    return "admin/products/new";
  }

  @PostMapping("/products/new")
  public String createProduct(@ModelAttribute("pcm") @Valid ProductCreateModel pcm,
          BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

    if (pcm.getPrice() != null) {
      if (pcm.getPrice() <= 0.00) {
        bindingResult.rejectValue("price", "products.new.error.price");
      }
    }

    if (bindingResult.hasErrors()) {

      model.addAttribute("globalMenu", "products");
      return "admin/products/new";
    }

    try {
      ProductEntity createdProduct = productService.createProduct(pcm);

//            redirectAttributes.addFlashAttribute("msgSuccess", getMessage("products.new.success"));
    } catch (Exception e) {
      log.error("Error creating product", e);
//            redirectAttributes.addFlashAttribute("msgError", getMessage("products.new.error"));
    }

    return "redirect:/admin/products";
  }

  @PostMapping("/product/approve")
  public String approveProduct(@RequestParam("productId") Long productId,
          RedirectAttributes redirectAttributes) {

    try {
      productService.approveProduct(productId);

//            redirectAttributes.addFlashAttribute("msgSuccess", "product.approved.success");
    } catch (Exception e) {
      log.error("Error approving a product!", e);
//            redirectAttributes.addFlashAttribute("msgError", "product.approved.error");
    }

    return "redirect:/admin/product/" + productId;
  }

  @PostMapping("/product/deactivate")
  public String deactivateProduct(@RequestParam("productId") Long productId,
          RedirectAttributes redirectAttributes) {

    try {
      productService.deactivateProduct(productId);

//            redirectAttributes.addFlashAttribute("msgSuccess", "product.deactivated.success");
    } catch (Exception e) {
      log.error("Error deactivating a product!", e);
//            redirectAttributes.addFlashAttribute("msgError", "product.deactivated.error");
    }

    return "redirect:/admin/product/" + productId;
  }

  @PostMapping("/product/delete")
  public String deleteProduct(@RequestParam("productId") Long productId,
          RedirectAttributes redirectAttributes) {

    try {
      productService.deleteProduct(productId);

//            redirectAttributes.addFlashAttribute("msgSuccess", "product.deleted.success");
    } catch (Exception e) {
      log.error("Error deleting a product!", e);
//            redirectAttributes.addFlashAttribute("msgError", "product.deleted.error");
    }

    return "redirect:/admin/product/" + productId;
  }

  @PostMapping("/products/image/new")
  public String createImage(@RequestParam("file") MultipartFile file,
          @RequestParam("productId") Long productId,
          RedirectAttributes redirectAttributes) {

    log.debug("Uploading image");

    FileName name = null;

    try {
      name = uploader.uploadFile(file.getBytes(), file.getOriginalFilename(), ImageFolder.PRODUCTS);

      productService.addImage(name, productId);

      redirectAttributes.addFlashAttribute("msgSuccess", "product.image.add.success");

    } catch (Exception e) {
      log.error("Error uploading a file!", e);

      redirectAttributes.addFlashAttribute("msgError", "product.image.add.error");
    }

    return "redirect:/admin/product/" + productId;
  }
}
