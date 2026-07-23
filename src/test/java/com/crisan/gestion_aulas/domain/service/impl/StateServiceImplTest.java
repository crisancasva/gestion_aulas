package com.crisan.gestion_aulas.domain.service.impl;

import com.crisan.gestion_aulas.domain.model.State;
import com.crisan.gestion_aulas.domain.repository.StateRepository;
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
class StateServiceImplTest {

    @Mock
    private StateRepository stateRepository;

    @InjectMocks
    private StateServiceImpl stateService;

    @Test
    void getAllDelegatesToRepository() {
        List<State> states = List.of(State.builder().stateId(1L).stateDescription("ACTIVE").build());
        when(stateRepository.getAll()).thenReturn(states);

        assertThat(stateService.getAll()).isEqualTo(states);
        verify(stateRepository).getAll();
    }

    @Test
    void getByIdDelegatesToRepository() {
        State state = State.builder().stateId(3L).stateDescription("INACTIVE").build();
        when(stateRepository.getById(3L)).thenReturn(Optional.of(state));

        assertThat(stateService.getById(3L)).contains(state);
        verify(stateRepository).getById(3L);
    }
}
