package com.crisan.gestion_aulas.web.controller;

import com.crisan.gestion_aulas.domain.model.State;
import com.crisan.gestion_aulas.domain.service.StateService;
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
class StateControllerTest {

    @Mock
    private StateService stateService;

    @InjectMocks
    private StateController controller;

    @Test
    void getAllReturnsStates() {
        List<State> states = List.of(State.builder().stateId(1L).build());
        when(stateService.getAll()).thenReturn(states);

        assertThat(controller.getAll().getBody()).isEqualTo(states);
    }

    @Test
    void getByIdReturnsState() {
        State state = State.builder().stateId(1L).build();
        when(stateService.getById(1L)).thenReturn(Optional.of(state));

        assertThat(controller.getById(1L).getBody()).isEqualTo(state);
    }

    @Test
    void getByIdThrowsWhenMissing() {
        when(stateService.getById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> controller.getById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Estado no encontrado");
    }
}
