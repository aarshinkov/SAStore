package com.sastore.web.services;

import com.sastore.web.entities.FavoriteEntity;
import java.util.List;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public interface FavoriteService {

  List<FavoriteEntity> getUserFavorites(String userId);
}
