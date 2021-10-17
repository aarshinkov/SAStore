package com.sastore.web.repositories;

import com.sastore.web.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Repository
public interface CartsRepository extends JpaRepository<CartEntity, String> {

  CartEntity findByCartId(String cartId);

  CartEntity findByUserUserId(String userId);
}
