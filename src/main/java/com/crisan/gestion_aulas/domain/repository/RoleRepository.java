package com.crisan.gestion_aulas.domain.repository;

import com.crisan.gestion_aulas.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    List<Role> getAll();
    Optional<Role> getById(Long roleId);
}
