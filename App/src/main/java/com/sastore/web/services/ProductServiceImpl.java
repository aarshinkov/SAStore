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

//        ProductEntity product1 = new ProductEntity();
//        product1.setProductId(1001L);
//        product1.setTitle("Продукт 1");
//        product1.setPrice(123.04);
//        product1.setAvailableQuantity(20);
//        product1.setDescription("Описание на продукт 1");
//        product1.setMainImage("1.jpg");
//        product1.setAddedOn(new Timestamp(System.currentTimeMillis()));
//
//        ProductEntity product2 = new ProductEntity();
//        product2.setProductId(1002L);
//        product2.setTitle("Продукт 2");
//        product2.setPrice(99.99);
//        product2.setAvailableQuantity(15);
//        product2.setDescription("Описание на продукт 2");
//        product2.setMainImage("2.jpg");
//        product2.setAddedOn(new Timestamp(System.currentTimeMillis()));
//
//        List<ProductEntity> products = new ArrayList<>();
//        products.add(product1);
//        products.add(product2);
//
//        return products;
        return productsDao.getProducts(page, limit, filter);
    }

    @Override
    public ProductEntity getProductByProductId(Long productId) {
        return productsRepository.findByProductId(productId);
    }
}
