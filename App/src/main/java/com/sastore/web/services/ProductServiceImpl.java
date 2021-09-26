package com.sastore.web.services;

import com.sastore.web.collections.ObjCollection;
import com.sastore.web.dao.ProductsDao;
import com.sastore.web.entities.ProductEntity;
import com.sastore.web.entities.ProductImageEntity;
import com.sastore.web.enums.ProductStatuses;
import com.sastore.web.filters.ProductFilter;
import com.sastore.web.models.ProductCreateModel;
import com.sastore.web.models.ProductImageCreateModel;
import com.sastore.web.repositories.ProductImagesRepository;
import com.sastore.web.repositories.ProductsRepository;
import com.sastore.web.uploader.domain.FileName;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Service
public class ProductServiceImpl implements ProductService {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final String[] openTags = {"<h1>", "<h2>", "<h3>", "<h4>", "<h5>", "<h6>", "<p>"};
  private final String[] closeTags = {"</h1>", "</h2>", "</h3>", "</h4>", "</h5>", "</h6>", "</p>"};
  private final String[] breakTags = {"<br>", "<br/>"};

  @Autowired
  private ProductsDao productsDao;

  @Autowired
  private ProductsRepository productsRepository;

  @Autowired
  private ProductImagesRepository productImagesRepository;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public ObjCollection<ProductEntity> getProducts(Integer page, Integer limit, ProductFilter filter) {

    return productsDao.getProducts(page, limit, filter);
  }

  @Override
  public ObjCollection<ProductEntity> getAdminProducts(Integer page, Integer limit, ProductFilter filter) {

    return productsDao.getAdminProducts(page, limit, filter);
  }

  @Override
  public ProductEntity getProductByProductId(String productId) {
    return productsRepository.findByProductId(productId);
  }

  @Override
  public ProductEntity createProduct(ProductCreateModel pcm) throws Exception {

    ProductEntity product = new ProductEntity();
    product.setProductId(UUID.randomUUID().toString());
    product.setTitle(pcm.getTitle());
    product.setPrice(pcm.getPrice());
    product.setAvailableQuantity(pcm.getAvailableQuantity());
    product.setDescription(pcm.getDescription());

    ProductEntity createdProduct = productsRepository.save(product);

    return createdProduct;
  }

  @Override
  public void approveProduct(String productId) throws Exception {
    ProductEntity product = productsRepository.findByProductId(productId);
    product.setStatus(ProductStatuses.ACTIVE.getStatus());
    product.setApprovedOn(new Timestamp(System.currentTimeMillis()));

    productsRepository.save(product);
  }

  @Override
  public void deactivateProduct(String productId) throws Exception {
    ProductEntity product = productsRepository.findByProductId(productId);
    product.setStatus(ProductStatuses.INACTIVE.getStatus());
    product.setEditedOn(new Timestamp(System.currentTimeMillis()));

    productsRepository.save(product);
  }

  @Override
  public void deleteProduct(String productId) throws Exception {
    ProductEntity product = productsRepository.findByProductId(productId);
    product.setStatus(ProductStatuses.DELETED.getStatus());
    product.setEditedOn(new Timestamp(System.currentTimeMillis()));

    productsRepository.save(product);
  }

  @Override
  public List<ProductImageEntity> getProductAdditionalImages(String productId) {

    return productImagesRepository.findAllByProductProductId(productId);
  }

  @Override
  public void addImage(FileName file, ProductImageCreateModel picm) throws Exception {

    ProductImageEntity pi = new ProductImageEntity();
    pi.setImageId(file.getFullName());
    pi.setIsMain(picm.getIsMain());

    ProductEntity product = productsRepository.findByProductId(picm.getProductId());

    if (picm.getIsMain()) {
      product.setMainImage(file.getFullName());

      final String updateMainImage = "UPDATE prod_images SET is_main = false WHERE product_id = ? AND image_id != ?";
      jdbcTemplate.update(updateMainImage, picm.getProductId(), file.getFullName());
    }

    pi.setProduct(product);

    productImagesRepository.save(pi);
    productsRepository.save(product);
  }

  @Override
  public String getFirstParagraph(String description) {

    final String[] split = description.split("((</)\\w+(>))");

    return split[0].replaceAll("((<)\\w+(>))", "");
  }

  @Override
  public String getProductDescriptionFormatted(String description) {

    for (String tag : openTags) {
      description = description.replace(tag, "");
    }

    for (String tag : closeTags) {
      description = description.replace(tag, "<br/>");
    }

    return description;
  }

  @Override
  public String getProductDescriptionNonFormatted(String description) {
    return description;
  }
}
