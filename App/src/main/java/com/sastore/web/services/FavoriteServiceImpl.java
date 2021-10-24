package com.sastore.web.services;

import com.sastore.web.entities.FavoriteEntity;
import com.sastore.web.repositories.FavoritesRepository;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

  @Override
  public List<FavoriteEntity> getUserFavorites(String userId) {
    List<FavoriteEntity> favorites = favoritesRepository.findAllByUserUserId(userId);

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
}
