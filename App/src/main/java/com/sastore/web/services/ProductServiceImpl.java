package com.sastore.web.services;

import com.sastore.web.collections.ObjCollection;
import com.sastore.web.dao.ProductsDao;
import com.sastore.web.entities.ProductEntity;
import com.sastore.web.entities.ProductImageEntity;
import com.sastore.web.enums.ProductStatuses;
import com.sastore.web.filters.ProductFilter;
import com.sastore.web.models.ProductCreateModel;
import com.sastore.web.models.ProductEditModel;
import com.sastore.web.models.ProductImageCreateModel;
import com.sastore.web.repositories.ProductImagesRepository;
import com.sastore.web.repositories.ProductsRepository;
import com.sastore.web.uploader.domain.FileName;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
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
    ProductEntity product = productsRepository.findByProductId(productId);
    product.setIsNew(false);

    if (product.getApprovedOn() != null) {

      Calendar cal = Calendar.getInstance();
      cal.setTime(product.getApprovedOn());
      cal.add(Calendar.WEEK_OF_YEAR, 1);
      Date expiryDate = cal.getTime();

      if (new Date().before(expiryDate)) {
        product.setIsNew(true);
      } else {
        product.setIsNew(false);
      }
    }

    return product;
  }

  @Override
  public boolean registerProductView(ProductEntity product) {

    product.setViews(product.getViews() + 1);

    try {
      productsRepository.save(product);
      return true;
    } catch (Exception e) {
      return false;
    }
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
    createdProduct.setIsNew(true);

    return createdProduct;
  }

  @Override
  public void editProductPrice(String productId, Double mainPrice, Double discount, Integer availableQuantity) throws Exception {

    ProductEntity product = productsRepository.findByProductId(productId);
    product.setPrice(mainPrice);
    product.setDiscount(discount);
    product.setAvailableQuantity(availableQuantity);

    if (mainPrice == null || mainPrice < 0.00) {
      product.setPrice(0.00);
    }

    if (discount == null) {
      product.setDiscount(0.00);
    }

    if (discount >= mainPrice) {
      product.setDiscount(mainPrice);
    }

    if (availableQuantity == null) {
      product.setAvailableQuantity(0);
    }

    if (product.getAvailableQuantity() == 0) {
      product.setStatus(ProductStatuses.INACTIVE.getStatus());
    } else {
      if (product.getApprovedOn() != null) {
        product.setStatus(ProductStatuses.ACTIVE.getStatus());
      }
    }

    productsRepository.save(product);
  }

  @Override
  public ProductEntity editProduct(ProductEditModel pem) {

    ProductEntity product = productsRepository.findByProductId(pem.getProductId());

    product.setTitle(pem.getTitle());
    product.setDescription(pem.getDescription());
    product.setEditedOn(new Timestamp(System.currentTimeMillis()));

    productsRepository.save(product);

    return product;
  }

  @Override
  public void approveProduct(String productId) throws Exception {
    ProductEntity product = productsRepository.findByProductId(productId);
    product.setStatus(ProductStatuses.ACTIVE.getStatus());
    product.setApprovedOn(new Timestamp(System.currentTimeMillis()));

    productsRepository.save(product);
  }

  @Override
  public void activateProduct(String productId) throws Exception {
    ProductEntity product = productsRepository.findByProductId(productId);
    product.setStatus(ProductStatuses.ACTIVE.getStatus());

    productsRepository.save(product);
  }

  @Override
  public void restoreProduct(String productId) throws Exception {
    ProductEntity product = productsRepository.findByProductId(productId);
    product.setStatus(ProductStatuses.ACTIVE.getStatus());

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

    return productImagesRepository.findAllByProductProductIdOrderByCreatedOnAsc(productId);
  }

  @Override
  public void addImage(FileName file, ProductImageCreateModel picm) throws Exception {

    ProductImageEntity pi = new ProductImageEntity();
    pi.setImageId(file.getFullName());

    ProductEntity product = productsRepository.findByProductId(picm.getProductId());

    if (picm.getIsMain() == null) {
      pi.setIsMain(true);
      product.setMainImage(file.getFullName());

      final String updateMainImage = "UPDATE prod_images SET is_main = false WHERE product_id = ? AND image_id != ?";
      jdbcTemplate.update(updateMainImage, picm.getProductId(), file.getFullName());

      product.setStatus(ProductStatuses.PENDING_APPROVAL.getStatus());
    } else {
      pi.setIsMain(picm.getIsMain());

      if (picm.getIsMain()) {
        product.setMainImage(file.getFullName());

        final String updateMainImage = "UPDATE prod_images SET is_main = false WHERE product_id = ? AND image_id != ?";
        jdbcTemplate.update(updateMainImage, picm.getProductId(), file.getFullName());
      }
    }

    pi.setProduct(product);

    productImagesRepository.save(pi);
    productsRepository.save(product);
  }

  @Override
  public boolean deleteImage(String productId, String imageId) throws Exception {

    ProductImageEntity image = productImagesRepository.findByImageId(imageId);

    if (image == null) {
      return false;
    }

    ProductEntity product = productsRepository.findByProductId(productId);

    if (product == null) {
      return false;
    }

    if (image.getIsMain()) {
      //IF the image is a main image
      List<ProductImageEntity> images = productImagesRepository.findAllByProductProductIdOrderByCreatedOnAsc(productId);

      // The image to be deleted is the only one - deactivate product
      if (images.size() == 1) {
        log.debug("The image to be deleted is the only one - deactivating product");
        productImagesRepository.delete(image);
        product.setStatus(ProductStatuses.INACTIVE.getStatus());
        product.setMainImage(null);
        productsRepository.save(product);
        return true;
      }

      if (images.size() > 1) {

        for (ProductImageEntity prodImage : images) {
          if (!prodImage.getIsMain()) {
            product.setMainImage(prodImage.getImageId());
            ProductImageEntity newImage = productImagesRepository.findByImageId(prodImage.getImageId());
            newImage.setIsMain(true);
            productImagesRepository.save(newImage);
            break;
          }
        }
        productsRepository.save(product);
      }
    }

    productImagesRepository.delete(image);
    return true;
  }

  @Override
  public Long getProductsCountByStatus(Integer status) {

    String sql = "SELECT COUNT(product_id) FROM products WHERE status = ?";

    return jdbcTemplate.queryForObject(sql, Long.class, status);
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
