package com.sastore.web.services;

import com.sastore.web.collections.ObjCollection;
import com.sastore.web.entities.UserEntity;
import com.sastore.web.enums.Roles;
import com.sastore.web.filters.UserFilter;
import com.sastore.web.models.auth.SignupModel;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public interface UserService extends UserDetailsService {

  ObjCollection<UserEntity> getUsers(Integer page, Integer limit, UserFilter filter);

  UserEntity getUserByEmail(String email);

  UserEntity getUserByUserId(String userId);

  UserEntity createUser(SignupModel signupModel);

  Long getUsersCountByRole(String rolename);

  UserEntity addRole(String userId, Roles role) throws Exception;

  UserEntity removeRole(String userId, Roles role) throws Exception;
  
  boolean removeUser(String userId);

  boolean isUserExistByEmail(String email);

  boolean isPasswordMatch(String userId, String password);
}
