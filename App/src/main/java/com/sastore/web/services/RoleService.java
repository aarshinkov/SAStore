package com.sastore.web.services;

import com.sastore.web.entities.RoleEntity;

import java.util.List;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public interface RoleService {

    List<RoleEntity> getRoles();
}
