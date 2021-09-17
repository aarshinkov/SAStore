package com.sastore.web.dao;

import com.sastore.web.collections.ObjCollection;
import com.sastore.web.entities.UserEntity;
import com.sastore.web.filters.UserFilter;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public interface UsersDao {

  ObjCollection<UserEntity> getUsers(Integer page, Integer limit, UserFilter filter);

  Long getUsersCountByRole(String rolename);
}
