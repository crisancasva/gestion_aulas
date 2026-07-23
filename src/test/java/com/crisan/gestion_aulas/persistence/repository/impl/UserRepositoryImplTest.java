package com.crisan.gestion_aulas.persistence.repository.impl;

import com.crisan.gestion_aulas.domain.model.User;
import com.crisan.gestion_aulas.persistence.entity.UserEntity;
import com.crisan.gestion_aulas.persistence.mapper.UserMapper;
import com.crisan.gestion_aulas.persistence.repository.crud.UserCrudRepository;
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
class UserRepositoryImplTest {

    @Mock
    private UserCrudRepository crud;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserRepositoryImpl repository;

    @Test
    void getAllMapsEntities() {
        List<UserEntity> entities = List.of(new UserEntity());
        when(crud.findAll()).thenReturn(entities);
        when(mapper.toUsers(entities)).thenReturn(List.of(User.builder().build()));

        assertThat(repository.getAll()).hasSize(1);
    }

    @Test
    void getByIdMapsPresentEntity() {
        UserEntity entity = new UserEntity();
        User user = User.builder().userId(1L).build();
        when(crud.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toUser(entity)).thenReturn(user);

        assertThat(repository.getById(1L)).contains(user);
    }

    @Test
    void getByEmailMapsResult() {
        UserEntity entity = new UserEntity();
        User user = User.builder().userId(1L).email("a@b.com").build();
        when(crud.findByEmail("a@b.com")).thenReturn(Optional.of(entity));
        when(mapper.toUser(entity)).thenReturn(user);

        assertThat(repository.getByEmail("a@b.com")).contains(user);
    }

    @Test
    void saveMapsBothDirections() {
        User user = User.builder().userId(1L).build();
        UserEntity entity = new UserEntity();
        UserEntity saved = new UserEntity();
        when(mapper.toEntity(user)).thenReturn(entity);
        when(crud.save(entity)).thenReturn(saved);
        when(mapper.toUser(saved)).thenReturn(user);

        assertThat(repository.save(user)).isEqualTo(user);
    }

    @Test
    void deleteDelegatesToCrud() {
        repository.delete(2L);
        verify(crud).deleteById(2L);
    }
}
