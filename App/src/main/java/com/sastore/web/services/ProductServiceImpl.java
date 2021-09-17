package com.sastore.web.services;

import com.sastore.web.collections.ObjCollection;
import com.sastore.web.dao.ProductsDao;
import com.sastore.web.entities.ProductEntity;
import com.sastore.web.entities.ProductImageEntity;
import com.sastore.web.enums.ProductStatuses;
import com.sastore.web.filters.ProductFilter;
import com.sastore.web.models.ProductCreateModel;
import com.sastore.web.repositories.ProductImagesRepository;
import com.sastore.web.repositories.ProductsRepository;
import com.sastore.web.uploader.domain.FileName;
import java.sql.Timestamp;
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

    @Autowired
    private ProductImagesRepository productImagesRepository;

    @Override
    public ObjCollection<ProductEntity> getProducts(Integer page, Integer limit, ProductFilter filter) {

        return productsDao.getProducts(page, limit, filter);
    }

    @Override
    public ProductEntity getProductByProductId(Long productId) {
        return productsRepository.findByProductId(productId);
    }

    @Override
    public ProductEntity createProduct(ProductCreateModel pcm) throws Exception {

        ProductEntity product = new ProductEntity();
        product.setTitle(pcm.getTitle());
        product.setPrice(pcm.getPrice());
        product.setAvailableQuantity(pcm.getAvailableQuantity());
        product.setDescription(pcm.getDescription());

        ProductEntity createdProduct = productsRepository.save(product);

        return createdProduct;
    }

    @Override
    public void approveProduct(Long productId) throws Exception {
        ProductEntity product = productsRepository.findByProductId(productId);
        product.setStatus(ProductStatuses.ACTIVE.getStatus());
        product.setApprovedOn(new Timestamp(System.currentTimeMillis()));

        productsRepository.save(product);
    }

    @Override
    public void deactivateProduct(Long productId) throws Exception {
        ProductEntity product = productsRepository.findByProductId(productId);
        product.setStatus(ProductStatuses.INACTIVE.getStatus());
        product.setEditedOn(new Timestamp(System.currentTimeMillis()));

        productsRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) throws Exception {
        ProductEntity product = productsRepository.findByProductId(productId);
        product.setStatus(ProductStatuses.DELETED.getStatus());
        product.setEditedOn(new Timestamp(System.currentTimeMillis()));

        productsRepository.save(product);
    }

    @Override
    public void addImage(FileName file, Long productId) throws Exception {

        ProductImageEntity pi = new ProductImageEntity();
        pi.setImageId(file.getFullName());

        //TODO implement is main logic
        pi.setIsMain(true);

        ProductEntity product = productsRepository.findByProductId(productId);
        product.setMainImage(file.getFullName());

        pi.setProduct(product);

        productImagesRepository.save(pi);
        productsRepository.save(product);
    }
}
