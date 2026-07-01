package com.crisan.gestion_aulas.persistence.repository.impl;

import com.crisan.gestion_aulas.domain.model.State;
import com.crisan.gestion_aulas.domain.repository.StateRepository;
import com.crisan.gestion_aulas.persistence.mapper.StateMapper;
import com.crisan.gestion_aulas.persistence.repository.crud.StateCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StateRepositoryImpl implements StateRepository {
    private final StateCrudRepository stateCrudRepository;
    private final StateMapper stateMapper;
    @Override
    public List<State> getAll() {
        return stateMapper.toStates(stateCrudRepository.findAll());
    }

    @Override
    public Optional<State> getById(Long stateId) {
        return stateCrudRepository.findById(stateId).map(stateMapper::toState);
    }
}
