package com.crisan.gestion_aulas.persistence.repository.impl;

import com.crisan.gestion_aulas.domain.model.Role;
import com.crisan.gestion_aulas.persistence.entity.RoleEntity;
import com.crisan.gestion_aulas.persistence.mapper.RoleMapper;
import com.crisan.gestion_aulas.persistence.repository.crud.RoleCrudRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleRepositoryImplTest {

    @Mock
    private RoleCrudRepository crud;

    @Mock
    private RoleMapper mapper;

    @InjectMocks
    private RoleRepositoryImpl repository;

    @Test
    void getAllMapsEntities() {
        List<RoleEntity> entities = List.of(new RoleEntity());
        when(crud.findAll()).thenReturn(entities);
        when(mapper.toRoles(entities)).thenReturn(List.of(Role.builder().build()));

        assertThat(repository.getAll()).hasSize(1);
    }

    @Test
    void getByIdMapsPresentEntity() {
        RoleEntity entity = new RoleEntity();
        Role role = Role.builder().roleId(1L).build();
        when(crud.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toRole(entity)).thenReturn(role);

        assertThat(repository.getById(1L)).contains(role);
    }

    @Test
    void getByIdReturnsEmptyWhenAbsent() {
        when(crud.findById(2L)).thenReturn(Optional.empty());
        assertThat(repository.getById(2L)).isEmpty();
    }
}
