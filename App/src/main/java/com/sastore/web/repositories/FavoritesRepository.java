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
  
  List<FavoriteEntity> findAllByUserUserIdOrderByAddedOnDesc(String userId);
  
  FavoriteEntity findByUserUserIdAndProductProductId(String userId, String productId);
  
  FavoriteEntity findByFavoriteId(String favoriteId);
  
  boolean existsFavoriteEntityByUserUserIdAndProductProductId(String userId, String productId);
  
  Long countByUserUserId(String userId);
}
