package com.sastore.web.services;

import com.sastore.web.collections.ObjCollection;
import com.sastore.web.entities.ProductEntity;
import com.sastore.web.entities.ProductImageEntity;
import com.sastore.web.filters.ProductFilter;
import com.sastore.web.models.ProductCreateModel;
import com.sastore.web.models.ProductImageCreateModel;
import com.sastore.web.uploader.domain.FileName;
import java.util.List;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public interface ProductService {

  ObjCollection<ProductEntity> getProducts(Integer page, Integer limit, ProductFilter filter);

  ObjCollection<ProductEntity> getAdminProducts(Integer page, Integer limit, ProductFilter filter);

  ProductEntity getProductByProductId(String productId);

  ProductEntity createProduct(ProductCreateModel pcm) throws Exception;

  void approveProduct(String productId) throws Exception;

  void deactivateProduct(String productId) throws Exception;

  void deleteProduct(String productId) throws Exception;

  List<ProductImageEntity> getProductAdditionalImages(String productId);

  void addImage(FileName file, ProductImageCreateModel picm) throws Exception;

  // Utils
  String getFirstParagraph(String description);

  String getProductDescriptionFormatted(String description);

  String getProductDescriptionNonFormatted(String description);
}
