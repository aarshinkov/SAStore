package com.sastore.web.services;

import com.sastore.web.entities.RoleEntity;
import com.sastore.web.entities.UserEntity;
import com.sastore.web.enums.Roles;
import com.sastore.web.models.SignupModel;
import com.sastore.web.repositories.RolesRepository;
import com.sastore.web.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity getUserByEmail(String email) {

        return usersRepository.findByEmail(email);
    }

    @Override
    public UserEntity createUser(SignupModel signupModel) {

        UserEntity user = new UserEntity();
        user.setUserId(UUID.randomUUID().toString());
        user.setFirstName(signupModel.getFirstName());
        user.setLastName(signupModel.getLastName());
        user.setEmail(signupModel.getEmail());

        String encodedPassword = passwordEncoder.encode(signupModel.getPassword());

        user.setPassword(encodedPassword);

        Set<RoleEntity> roles = new HashSet<>();

        RoleEntity userRole = rolesRepository.findByRolename(Roles.USER.getRole());

        roles.add(userRole);

        user.setRoles(roles);

        return usersRepository.save(user);
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        UserEntity storedUser = usersRepository.findByEmail(email);

        return storedUser != null;
    }

    @Override
    public boolean isPasswordMatch(String userId, String password) {

        UserEntity storedUser = usersRepository.findByUserId(userId);

        return passwordEncoder.matches(password, storedUser.getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = usersRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return user;
    }
}
