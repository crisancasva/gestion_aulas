package com.crisan.gestion_aulas.web.controller;

import com.crisan.gestion_aulas.domain.model.Role;
import com.crisan.gestion_aulas.domain.service.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController controller;

    @Test
    void getAllReturnsRoles() {
        List<Role> roles = List.of(Role.builder().roleId(1L).build());
        when(roleService.getAll()).thenReturn(roles);

        assertThat(controller.getAll().getBody()).isEqualTo(roles);
    }

    @Test
    void getByIdReturnsRole() {
        Role role = Role.builder().roleId(1L).build();
        when(roleService.getById(1L)).thenReturn(Optional.of(role));

        assertThat(controller.getById(1L).getBody()).isEqualTo(role);
    }

    @Test
    void getByIdThrowsWhenMissing() {
        when(roleService.getById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> controller.getById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Rol no encontrado");
    }
}
