package com.sastore.web.repositories;

import com.sastore.web.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Repository
public interface RolesRepository extends JpaRepository<RoleEntity, String> {

  RoleEntity findByRolename(String rolename);

  @Override
  List<RoleEntity> findAll();
}
