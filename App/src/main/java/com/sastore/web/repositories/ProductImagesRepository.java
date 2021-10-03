package com.sastore.web.repositories;

import com.sastore.web.entities.ProductImageEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImageEntity, String> {

  List<ProductImageEntity> findAllByProductProductId(String productId);

  List<ProductImageEntity> findAllByProductProductIdOrderByCreatedOnAsc(String productId);

  List<ProductImageEntity> findAllByProductProductIdOrderByCreatedOnDesc(String productId);

  ProductImageEntity findByImageId(String imageId);
}
