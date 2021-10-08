package com.sastore.web.services;

import com.sastore.web.entities.RoleEntity;
import com.sastore.web.repositories.RolesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Service
public class RoleServiceImpl implements RoleService {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private RolesRepository rolesRepository;

  @Override
  public List<RoleEntity> getRoles() {
    return rolesRepository.findAll();
  }
}
