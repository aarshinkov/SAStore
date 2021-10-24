package com.sastore.web.dao;

import com.sastore.web.collections.ObjCollection;
import com.sastore.web.entities.ProductEntity;
import com.sastore.web.filters.ProductFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public interface ProductsDao {

  ObjCollection<ProductEntity> getProducts(Integer page, Integer limit, ProductFilter filter, HttpServletRequest request);

  ObjCollection<ProductEntity> getAdminProducts(Integer page, Integer limit, ProductFilter filter);
}
