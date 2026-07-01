package com.crisan.gestion_aulas.domain.repository;

import com.crisan.gestion_aulas.domain.model.State;

import java.util.List;
import java.util.Optional;

public interface StateRepository {
    List<State> getAll();
    Optional<State> getById(Long stateId);
}
