package com.sastore.web.services;

import com.sastore.web.entities.FavoriteEntity;
import java.util.List;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public interface FavoriteService {

  List<FavoriteEntity> getUserFavorites(String userId);
  
  Long getUserFavoritesCount(String userId);
  
  FavoriteEntity createFavorite(String userId, String productId);
  
  FavoriteEntity removeFavorite(String userId, String productId);
  
  FavoriteEntity deleteFavorite(String favoriteId);
}
