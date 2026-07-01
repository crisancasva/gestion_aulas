package com.crisan.gestion_aulas.persistence.repository.impl;

import com.crisan.gestion_aulas.domain.model.Role;
import com.crisan.gestion_aulas.domain.repository.RoleRepository;
import com.crisan.gestion_aulas.persistence.mapper.RoleMapper;
import com.crisan.gestion_aulas.persistence.repository.crud.RoleCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    private final RoleCrudRepository roleCrudRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<Role> getAll() {
        return roleMapper.toRoles(roleCrudRepository.findAll());
    }

    @Override
    public Optional<Role> getById(Long roleId) {
        return roleCrudRepository.findById(roleId).map(roleMapper::toRole);
    }
}
