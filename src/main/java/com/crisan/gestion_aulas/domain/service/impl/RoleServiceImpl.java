package com.crisan.gestion_aulas.domain.service.impl;

import com.crisan.gestion_aulas.domain.model.Role;
import com.crisan.gestion_aulas.domain.repository.RoleRepository;
import com.crisan.gestion_aulas.domain.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.getAll();
    }

    @Override
    public Optional<Role> getById(Long roleId) {
        return roleRepository.getById(roleId);
    }
}
