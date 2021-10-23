package com.sastore.web.repositories;

import com.sastore.web.entities.ProductVariationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
@Repository
public interface ProductVariationsRepository extends JpaRepository<ProductVariationEntity, String> {

  List<ProductVariationEntity> findAllByProductProductId(String productId);
  
  List<ProductVariationEntity> findAllByProductProductIdAndIsColorTrue(String productId);
  
  List<ProductVariationEntity> findAllByProductProductIdAndIsDimensionTrue(String productId);
}
