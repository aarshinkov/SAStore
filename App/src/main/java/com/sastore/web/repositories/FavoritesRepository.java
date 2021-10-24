package com.sastore.web.repositories;

import com.sastore.web.entities.FavoriteEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
@Repository
public interface FavoritesRepository extends JpaRepository<FavoriteEntity, String> {

  List<FavoriteEntity> findAllByUserUserId(String userId);
  
  FavoriteEntity findByUserUserIdAndProductProductId(String userId, String productId);
  
  boolean existsFavoriteEntityByUserUserIdAndProductProductId(String userId, String productId);
}
