package com.crisan.gestion_aulas.domain.service.impl;

import com.crisan.gestion_aulas.domain.model.Role;
import com.crisan.gestion_aulas.domain.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void getAllDelegatesToRepository() {
        List<Role> roles = List.of(Role.builder().roleId(1L).roleDescription("ADMIN").build());
        when(roleRepository.getAll()).thenReturn(roles);

        assertThat(roleService.getAll()).isEqualTo(roles);
        verify(roleRepository).getAll();
    }

    @Test
    void getByIdDelegatesToRepository() {
        Role role = Role.builder().roleId(2L).roleDescription("USER").build();
        when(roleRepository.getById(2L)).thenReturn(Optional.of(role));

        assertThat(roleService.getById(2L)).contains(role);
        verify(roleRepository).getById(2L);
    }
}
