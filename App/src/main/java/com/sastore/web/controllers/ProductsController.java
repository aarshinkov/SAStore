package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.collections.ObjCollection;
import com.sastore.web.entities.ProductEntity;
import com.sastore.web.filters.ProductFilter;
import com.sastore.web.models.ProductCreateModel;
import com.sastore.web.services.ProductService;
import com.sastore.web.uploader.Uploader;
import com.sastore.web.uploader.domain.FileName;
import com.sastore.web.uploader.domain.ImageFolder;
import com.sastore.web.utils.Breadcrumb;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
public class ProductsController extends Base {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private ProductService productService;

  @Autowired
  private Uploader uploader;

  @GetMapping(value = "/products")
  public String getProducts(@RequestParam(name = "id") String productId, Model model) {

    return "products/product";
  }

  // ADMIN
  @GetMapping("/admin/products")
  public String getProducts(@ModelAttribute("filter") ProductFilter filter,
          @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
          @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit, Model model) {

    if (page <= 0) {
      return "redirect:/admin/products?page=1" + filter.getPagingParams();
    }

    if (limit <= 0) {
      return "redirect:/admin/products?limit=10" + filter.getPagingParams();
    }

    boolean isSearched = false;

    if (!filter.isFilterEmpty()) {
      isSearched = true;
    }

    model.addAttribute("isSearched", isSearched);

    ObjCollection<ProductEntity> products = productService.getProducts(page, limit, filter);

    model.addAttribute("products", products);

    String otherParams = "";

    otherParams = "&limit=" + limit;

    model.addAttribute("otherParameters", otherParams);

    model.addAttribute("pageWrapper", products.getPage());
    model.addAttribute("maxPagesPerView", 3);

    model.addAttribute("limit", limit);

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.managing.products", null, LocaleContextHolder.getLocale()), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("pageTitle", getMessage("products.title"));
    model.addAttribute("globalMenu", "products");
    model.addAttribute("submenu", "products");

    return "admin/products/dashboard";
  }

  @GetMapping(value = "/admin/products", params = {"id"})
  public String product(@RequestParam(name = "id") String productId, Model model) {

    ProductEntity product = productService.getProductByProductId(productId);

    model.addAttribute("product", product);

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.managing.products", null, LocaleContextHolder.getLocale()), "/admin/products"));
    breadcrumbs.add(new Breadcrumb(product.getTitle(), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("pageTitle", getMessage("products.title"));

    model.addAttribute("pageTitle", product.getTitle());
    model.addAttribute("globalMenu", "products");

    return "admin/products/product";

  }

  @GetMapping("/admin/products/new")
  public String prepareNewProduct(Model model) {

    model.addAttribute("pcm", new ProductCreateModel());

    model.addAttribute("globalMenu", "products");

    return "admin/products/new";
  }

  @PostMapping("/admin/products/new")
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

  @GetMapping("/admin/products/pending")
  public String pendingProducts(Model model) {

    model.addAttribute("submenu", "pending");
    model.addAttribute("globalMenu", "products");

    return "admin/products/pending";
  }

  @PostMapping("/admin/product/approve")
  public String approveProduct(@RequestParam("productId") String productId,
          RedirectAttributes redirectAttributes) {

    try {
      productService.approveProduct(productId);

//            redirectAttributes.addFlashAttribute("msgSuccess", "product.approved.success");
    } catch (Exception e) {
      log.error("Error approving a product!", e);
//            redirectAttributes.addFlashAttribute("msgError", "product.approved.error");
    }

    return "redirect:/admin/products?id=" + productId;
  }

  @PostMapping("/admin/product/deactivate")
  public String deactivateProduct(@RequestParam("productId") String productId,
          RedirectAttributes redirectAttributes) {

    try {
      productService.deactivateProduct(productId);

//            redirectAttributes.addFlashAttribute("msgSuccess", "product.deactivated.success");
    } catch (Exception e) {
      log.error("Error deactivating a product!", e);
//            redirectAttributes.addFlashAttribute("msgError", "product.deactivated.error");
    }

    return "redirect:/admin/products?id=" + productId;
  }

  @PostMapping("/admin/product/delete")
  public String deleteProduct(@RequestParam("productId") String productId,
          RedirectAttributes redirectAttributes) {

    try {
      productService.deleteProduct(productId);

//            redirectAttributes.addFlashAttribute("msgSuccess", "product.deleted.success");
    } catch (Exception e) {
      log.error("Error deleting a product!", e);
//            redirectAttributes.addFlashAttribute("msgError", "product.deleted.error");
    }

    return "redirect:/admin/products?id=" + productId;
  }

  @PostMapping("/admin/products/image/new")
  public String createImage(@RequestParam("file") MultipartFile file,
          @RequestParam("productId") String productId,
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

    return "redirect:/admin/products?id=" + productId;
  }
}
