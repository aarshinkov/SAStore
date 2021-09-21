package com.sastore.web.services;

import com.sastore.web.collections.ObjCollection;
import com.sastore.web.entities.ProductEntity;
import com.sastore.web.filters.ProductFilter;
import com.sastore.web.models.ProductCreateModel;
import com.sastore.web.uploader.domain.FileName;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public interface ProductService {

  ObjCollection<ProductEntity> getProducts(Integer page, Integer limit, ProductFilter filter);

  ProductEntity getProductByProductId(String productId);

  ProductEntity createProduct(ProductCreateModel pcm) throws Exception;

  void approveProduct(String productId) throws Exception;

  void deactivateProduct(String productId) throws Exception;

  void deleteProduct(String productId) throws Exception;

  void addImage(FileName file, String productId) throws Exception;
}