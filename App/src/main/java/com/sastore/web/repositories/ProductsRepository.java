package com.sastore.web.repositories;

import com.sastore.web.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Repository
public interface ProductsRepository extends JpaRepository<ProductEntity, Long> {

  @Override
  List<ProductEntity> findAll();

  ProductEntity findByProductId(Long productId);
}
