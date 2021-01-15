package com.sastore.web.services;

import com.sastore.web.collections.ObjCollection;
import com.sastore.web.dao.ProductsDao;
import com.sastore.web.entities.ProductEntity;
import com.sastore.web.filters.ProductFilter;
import com.sastore.web.repositories.ProductsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductsDao productsDao;

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public ObjCollection<ProductEntity> getProducts(Integer page, Integer limit, ProductFilter filter) {

        return productsDao.getProducts(page, limit, filter);
    }

    @Override
    public ProductEntity getProductByProductId(Long productId) {
        return productsRepository.findByProductId(productId);
    }
}
