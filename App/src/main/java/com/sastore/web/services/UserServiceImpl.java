package com.sastore.web.services;

import com.sastore.web.collections.ObjCollection;
import com.sastore.web.dao.UsersDao;
import com.sastore.web.entities.RoleEntity;
import com.sastore.web.entities.UserEntity;
import com.sastore.web.enums.Roles;
import com.sastore.web.filters.UserFilter;
import com.sastore.web.models.auth.SignupModel;
import com.sastore.web.repositories.RolesRepository;
import com.sastore.web.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private UsersDao usersDao;

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private RolesRepository rolesRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public ObjCollection<UserEntity> getUsers(Integer page, Integer limit, UserFilter filter) {

    return usersDao.getUsers(page, limit, filter);
  }

  @Override
  public UserEntity getUserByEmail(String email) {

    return usersRepository.findByEmail(email);
  }

  @Override
  public UserEntity getUserByUserId(String userId) {

    return usersRepository.findByUserId(userId);
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

    List<RoleEntity> roles = new ArrayList<>();

    RoleEntity userRole = rolesRepository.findByRolename(Roles.USER.getRole());

    roles.add(userRole);

    user.setRoles(roles);

    return usersRepository.save(user);
  }

  @Override
  public Long getUsersCountByRole(String rolename) {

    return usersDao.getUsersCountByRole(rolename);
  }

  @Override
  public UserEntity addRole(String userId, Roles role) throws Exception {
    UserEntity user = usersRepository.findByUserId(userId);

    if (user == null) {
      throw new Exception("User with ID " + userId + " not found!");
    }

    RoleEntity newRole = rolesRepository.findByRolename(role.getRole().toUpperCase());

    user.getRoles().add(newRole);

    return usersRepository.save(user);
  }

  @Override
  public UserEntity removeRole(String userId, Roles role) throws Exception {
    UserEntity user = usersRepository.findByUserId(userId);

    if (user == null) {
      throw new Exception("User with ID " + userId + " not found!");
    }

    RoleEntity storedRole = rolesRepository.findByRolename(role.getRole().toUpperCase());

    List<RoleEntity> userRoles = user.getRoles();

    for (int i = 0; i < userRoles.size(); i++) {
      if (userRoles.get(i).getRolename().equals(storedRole.getRolename())) {
        userRoles.remove(i);
      }
    }

    user.setRoles(userRoles);

    return usersRepository.save(user);
  }

  @Override
  public boolean removeUser(String userId) {

    try {
      UserEntity user = usersRepository.findByUserId(userId);
      usersRepository.delete(user);
    } catch (Exception e) {
      return false;
    }

    return true;
  }

  @Override
  public boolean isUserExistByEmail(String email) {

    return usersRepository.findByEmail(email) != null;
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
