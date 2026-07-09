package com.crisan.gestion_aulas.domain.service;

import com.crisan.gestion_aulas.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAll();
    Optional<Role> getById(Long roleId);
}
