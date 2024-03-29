package com.sastore.web.services;

import com.sastore.web.collections.ObjCollection;
import com.sastore.web.entities.ProductEntity;
import com.sastore.web.entities.ProductImageEntity;
import com.sastore.web.filters.ProductFilter;
import com.sastore.web.models.products.ProductCreateModel;
import com.sastore.web.models.products.ProductEditModel;
import com.sastore.web.models.products.ProductImageCreateModel;
import com.sastore.web.uploader.domain.FileName;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public interface ProductService {

  ObjCollection<ProductEntity> getProducts(Integer page, Integer limit, ProductFilter filter, HttpServletRequest request);

  ObjCollection<ProductEntity> getAdminProducts(Integer page, Integer limit, ProductFilter filter);

  ProductEntity getProductByProductId(String productId);

  boolean registerProductView(ProductEntity product);

  ProductEntity createProduct(ProductCreateModel pcm) throws Exception;

  void editProductPrice(String productId, Double mainPrice, Double discount, Integer availableQuantity) throws Exception;

  ProductEntity editProduct(ProductEditModel pem);

  void approveProduct(String productId) throws Exception;

  void activateProduct(String productId) throws Exception;

  void restoreProduct(String productId) throws Exception;

  void deactivateProduct(String productId) throws Exception;

  void deleteProduct(String productId) throws Exception;

  List<ProductImageEntity> getProductAdditionalImages(String productId);

  void addImage(FileName file, ProductImageCreateModel picm) throws Exception;

  boolean deleteImage(String productId, String imageId) throws Exception;

  Long getProductsCountByStatus(Integer status);

  // Utils
  String getFirstParagraph(String description);

  String getProductDescriptionFormatted(String description);

  String getProductDescriptionNonFormatted(String description);
}
