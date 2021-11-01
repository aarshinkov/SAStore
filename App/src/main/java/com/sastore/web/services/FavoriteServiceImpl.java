package com.sastore.web.services;

import com.sastore.web.entities.FavoriteEntity;
import com.sastore.web.entities.ProductEntity;
import com.sastore.web.entities.UserEntity;
import com.sastore.web.repositories.FavoritesRepository;
import com.sastore.web.repositories.ProductsRepository;
import com.sastore.web.repositories.UsersRepository;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private FavoritesRepository favoritesRepository;

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private ProductsRepository productsRepository;

  @Override
  public List<FavoriteEntity> getUserFavorites(String userId) {
    List<FavoriteEntity> favorites = favoritesRepository.findAllByUserUserIdOrderByAddedOnDesc(userId);

    for (FavoriteEntity favorite : favorites) {

      Calendar cal = Calendar.getInstance();
      cal.setTime(favorite.getProduct().getApprovedOn());
      cal.add(Calendar.WEEK_OF_YEAR, 1);
      Date expiryDate = cal.getTime();

      if (new Date().before(expiryDate)) {
        favorite.getProduct().setIsNew(true);
      } else {
        favorite.getProduct().setIsNew(false);
      }
    }

    return favorites;
  }

  @Override
  public Long getUserFavoritesCount(String userId) {
    return favoritesRepository.countByUserUserId(userId);
  }

  @Override
  public FavoriteEntity createFavorite(String userId, String productId) {
    FavoriteEntity favorite = new FavoriteEntity();
    favorite.setFavoriteId(UUID.randomUUID().toString());

    UserEntity user = usersRepository.findByUserId(userId);

    if (user == null) {
      return null;
    }

    favorite.setUser(user);

    ProductEntity product = productsRepository.findByProductId(productId);

    if (product == null) {
      return null;
    }

    favorite.setProduct(product);

    return favoritesRepository.save(favorite);
  }

  @Override
  public FavoriteEntity removeFavorite(String userId, String productId) {

    FavoriteEntity favorite = favoritesRepository.findByUserUserIdAndProductProductId(userId, productId);

    favoritesRepository.delete(favorite);

    return favorite;
  }

  @Override
  public FavoriteEntity deleteFavorite(String favoriteId) {

    FavoriteEntity favorite = favoritesRepository.findByFavoriteId(favoriteId);

    favoritesRepository.delete(favorite);

    return favorite;
  }
}
