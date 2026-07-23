package com.crisan.gestion_aulas.persistence.repository.impl;

import com.crisan.gestion_aulas.domain.model.State;
import com.crisan.gestion_aulas.persistence.entity.StateEntity;
import com.crisan.gestion_aulas.persistence.mapper.StateMapper;
import com.crisan.gestion_aulas.persistence.repository.crud.StateCrudRepository;
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
class StateRepositoryImplTest {

    @Mock
    private StateCrudRepository crud;

    @Mock
    private StateMapper mapper;

    @InjectMocks
    private StateRepositoryImpl repository;

    @Test
    void getAllMapsEntities() {
        List<StateEntity> entities = List.of(new StateEntity());
        when(crud.findAll()).thenReturn(entities);
        when(mapper.toStates(entities)).thenReturn(List.of(State.builder().build()));

        assertThat(repository.getAll()).hasSize(1);
    }

    @Test
    void getByIdMapsPresentEntity() {
        StateEntity entity = new StateEntity();
        State state = State.builder().stateId(1L).build();
        when(crud.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toState(entity)).thenReturn(state);

        assertThat(repository.getById(1L)).contains(state);
    }

    @Test
    void getByIdReturnsEmptyWhenAbsent() {
        when(crud.findById(2L)).thenReturn(Optional.empty());
        assertThat(repository.getById(2L)).isEmpty();
    }
}
