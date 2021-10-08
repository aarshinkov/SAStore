package com.sastore.web.repositories;

import com.sastore.web.entities.BasketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Repository
public interface BasketsRepository extends JpaRepository<BasketEntity, String> {

  BasketEntity findByUserUserId(String userId);
}
