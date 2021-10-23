package com.sastore.web.controllers;

import com.sastore.web.api.EcontApi;
import com.sastore.web.base.Base;
import com.sastore.web.collections.ObjCollection;
import com.sastore.web.entities.ProductEntity;
import com.sastore.web.entities.ProductImageEntity;
import com.sastore.web.entities.ProductVariationEntity;
import com.sastore.web.filters.ProductFilter;
import com.sastore.web.integration.econt.nomenclatures.EcontCity;
import com.sastore.web.models.products.ProductCreateModel;
import com.sastore.web.models.products.ProductEditModel;
import com.sastore.web.models.products.ProductImageCreateModel;
import com.sastore.web.repositories.ProductVariationsRepository;
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
  private ProductVariationsRepository productVariationsRepository;

  @Autowired
  private Uploader uploader;

  @Autowired
  private EcontApi econtApi;

  @GetMapping(value = "/products")
  public String getProducts(@ModelAttribute("filter") ProductFilter filter,
          @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
          @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit, Model model) {

    if (page <= 0) {
      return "redirect:/products?page=1" + filter.getPagingParams();
    }

    if (limit <= 0) {
      return "redirect:/products?limit=10" + filter.getPagingParams();
    }

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
    breadcrumbs.add(new Breadcrumb(getMessage("nav.products", null, LocaleContextHolder.getLocale()), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("pageTitle", getMessage("products.title"));
    model.addAttribute("globalMenu", "products");
    model.addAttribute("submenu", "products");

    List<EcontCity> cities = econtApi.getCities("BGR");
    model.addAttribute("econtCities", cities);

    return "products/products";
  }

  @GetMapping(value = "/product")
  public String getProduct(@RequestParam("id") String productId, Model model) {

    ProductEntity product = productService.getProductByProductId(productId);

    product.setFormattedDescription(productService.getFirstParagraph(product.getDescription()));

    model.addAttribute("product", product);

    productService.registerProductView(product);

    List<ProductImageEntity> images = productService.getProductAdditionalImages(productId);

    model.addAttribute("images", images);

//    List<ProductVariationEntity> variations = productVariationsRepository.findAllByProductProductId(productId);
    List<ProductVariationEntity> colorVariations = productVariationsRepository.findAllByProductProductIdAndIsColorTrue(productId);
    model.addAttribute("colorVariations", colorVariations);
    
    List<ProductVariationEntity> dimensionVariations = productVariationsRepository.findAllByProductProductIdAndIsDimensionTrue(productId);
    model.addAttribute("dimensionVariations", dimensionVariations);

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.products", null, LocaleContextHolder.getLocale()), "/products"));
    breadcrumbs.add(new Breadcrumb(product.getTitle(), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("pageTitle", getMessage("products.title"));
    model.addAttribute("globalMenu", "products");
    model.addAttribute("submenu", "products");

    return "products/product";
  }

  // ADMIN
  @GetMapping("/admin/products")
  public String getAdminProducts(@ModelAttribute("filter") ProductFilter filter,
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

    ObjCollection<ProductEntity> products = productService.getAdminProducts(page, limit, filter);

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

    ProductImageCreateModel picm = new ProductImageCreateModel();
    picm.setProductId(productId);
    model.addAttribute("picm", picm);

    List<ProductImageEntity> images = productService.getProductAdditionalImages(productId);

    model.addAttribute("images", images);

    return "admin/products/product";

  }

  @GetMapping("/admin/products/new")
  public String prepareNewProduct(Model model) {

    model.addAttribute("pcm", new ProductCreateModel());

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.managing.products", null, LocaleContextHolder.getLocale()), "/admin/products"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.managing.products.new", null, LocaleContextHolder.getLocale()), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("pageTitle", getMessage("products.new.header"));
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

  @GetMapping("/admin/products/edit")
  public String prepareEditProduct(@RequestParam("id") String productId, Model model) {

    ProductEntity product = productService.getProductByProductId(productId);

    ProductEditModel pem = new ProductEditModel();
    pem.setProductId(productId);
    pem.setTitle(product.getTitle());
    pem.setDescription(product.getDescription());

    model.addAttribute("pem", pem);

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.managing.products", null, LocaleContextHolder.getLocale()), "/admin/products"));
    breadcrumbs.add(new Breadcrumb(product.getTitle(), "/admin/products?id=" + product.getProductId()));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.managing.products.edit", null, LocaleContextHolder.getLocale()), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("pageTitle", getMessage("products.edit.header"));
    model.addAttribute("globalMenu", "products");

    return "admin/products/edit";
  }

  @PostMapping("/admin/products/edit")
  public String editProduct(@ModelAttribute("pem") @Valid ProductEditModel pem, BindingResult bindingResult, Model model) {

    if (bindingResult.hasErrors()) {
      ProductEntity product = productService.getProductByProductId(pem.getProductId());

      List<Breadcrumb> breadcrumbs = new ArrayList<>();
      breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
      breadcrumbs.add(new Breadcrumb(getMessage("nav.managing.products", null, LocaleContextHolder.getLocale()), "/admin/products"));
      breadcrumbs.add(new Breadcrumb(product.getTitle(), "/admin/products?id=" + product.getProductId()));
      breadcrumbs.add(new Breadcrumb(getMessage("nav.managing.products.edit", null, LocaleContextHolder.getLocale()), null));
      model.addAttribute("breadcrumbs", breadcrumbs);

      model.addAttribute("pageTitle", getMessage("products.edit.header"));
      model.addAttribute("globalMenu", "products");

      return "admin/products/edit";
    }

    try {
      productService.editProduct(pem);

//            redirectAttributes.addFlashAttribute("msgSuccess", getMessage("products.edit.success"));
    } catch (Exception e) {
      log.error("Error editing product", e);
//            redirectAttributes.addFlashAttribute("msgError", getMessage("products.edit.error"));
    }

    return "redirect:/admin/products?id=" + pem.getProductId();
  }

  @PostMapping("/admin/product/price")
  public String editProductPrice(@RequestParam("productId") String productId,
          @RequestParam(name = "mainPrice", required = false, defaultValue = "0.00") Double mainPrice,
          @RequestParam(name = "discount", required = false, defaultValue = "0.00") Double discount,
          @RequestParam(name = "availableQuantity", required = false, defaultValue = "0") Integer availableQuantity,
          RedirectAttributes redirectAttributes) {

    log.debug("productId: " + productId);
    log.debug("mainPrice: " + mainPrice);
    log.debug("discount: " + discount);
    log.debug("availableQuantity: " + availableQuantity);

    try {
      productService.editProductPrice(productId, mainPrice, discount, availableQuantity);

//            redirectAttributes.addFlashAttribute("msgSuccess", "product.restored.success");
    } catch (Exception e) {
      log.error("Error editting product price!", e);
//            redirectAttributes.addFlashAttribute("msgError", "product.restored.error");
    }
    return "redirect:/admin/products?id=" + productId;
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

  @PostMapping("/admin/product/activate")
  public String activateProduct(@RequestParam("productId") String productId,
          RedirectAttributes redirectAttributes) {

    try {
      productService.activateProduct(productId);

//            redirectAttributes.addFlashAttribute("msgSuccess", "product.approved.success");
    } catch (Exception e) {
      log.error("Error activating a product!", e);
//            redirectAttributes.addFlashAttribute("msgError", "product.approved.error");
    }

    return "redirect:/admin/products?id=" + productId;
  }

  @PostMapping("/admin/product/restore")
  public String restoreProduct(@RequestParam("productId") String productId,
          RedirectAttributes redirectAttributes) {

    try {
      productService.restoreProduct(productId);

//            redirectAttributes.addFlashAttribute("msgSuccess", "product.restored.success");
    } catch (Exception e) {
      log.error("Error restoring a product!", e);
//            redirectAttributes.addFlashAttribute("msgError", "product.restored.error");
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
  public String createImage(@ModelAttribute("picm") ProductImageCreateModel picm,
          RedirectAttributes redirectAttributes) {

    log.debug("Uploading image");
    log.debug("isMain: " + picm.getIsMain());

    FileName name = null;

    try {
      name = uploader.uploadFile(picm.getFile().getBytes(), picm.getFile().getOriginalFilename(), ImageFolder.PRODUCTS);

      productService.addImage(name, picm);

      redirectAttributes.addFlashAttribute("msgSuccess", "product.image.add.success");

    } catch (Exception e) {
      log.error("Error uploading a file!", e);

      redirectAttributes.addFlashAttribute("msgError", "product.image.add.error");
    }

    return "redirect:/admin/products?id=" + picm.getProductId();
  }

  @PostMapping("/admin/products/image/delete")
  public String deleteImage(@RequestParam(name = "imageId") String imageId,
          @RequestParam(name = "productId") String productId,
          RedirectAttributes redirectAttributes) {

    log.debug("Deleting image: " + imageId + " for product: " + productId);

    uploader.deleteFile(imageId);

    try {
      productService.deleteImage(productId, imageId);
    } catch (Exception e) {

    }

    return "redirect:/admin/products?id=" + productId;
  }
}
