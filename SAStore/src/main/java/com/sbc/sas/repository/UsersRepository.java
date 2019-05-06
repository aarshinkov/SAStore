package com.sbc.sas.repository;

import com.sbc.sas.entity.*;
import java.util.*;
import org.springframework.data.repository.*;

public interface UsersRepository extends CrudRepository<User, Integer>
{
  List<User> findAllByOrderByUserIdDesc();

  User findUserByUsername(String username);

  User findByUserId(Integer userId);
}
