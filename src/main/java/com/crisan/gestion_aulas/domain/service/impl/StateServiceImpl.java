package com.crisan.gestion_aulas.domain.service.impl;

import com.crisan.gestion_aulas.domain.model.State;
import com.crisan.gestion_aulas.domain.repository.StateRepository;
import com.crisan.gestion_aulas.domain.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StateServiceImpl implements StateService {
    private final StateRepository stateRepository;

    @Override
    public List<State> getAll() {
        return stateRepository.getAll();
    }

    @Override
    public Optional<State> getById(Long stateId) {
        return stateRepository.getById(stateId);
    }
}
