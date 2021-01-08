package com.sastore.web.services;

import com.sastore.web.entities.UserEntity;
import com.sastore.web.models.SignupModel;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public interface UserService extends UserDetailsService {

    UserEntity getUserByEmail(String email);

    UserEntity createUser(SignupModel signupModel);

    boolean isUserExistByEmail(String email);

    boolean isPasswordMatch(String userId, String password);
}