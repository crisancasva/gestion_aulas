package com.crisan.gestion_aulas.domain.service;

import com.crisan.gestion_aulas.domain.model.State;

import java.util.List;
import java.util.Optional;

public interface StateService {
    List<State> getAll();
    Optional<State> getById(Long stateId);
}
