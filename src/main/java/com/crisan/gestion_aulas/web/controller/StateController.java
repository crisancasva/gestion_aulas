package com.crisan.gestion_aulas.web.controller;

import com.crisan.gestion_aulas.common.util.Entities;
import com.crisan.gestion_aulas.domain.model.State;
import com.crisan.gestion_aulas.domain.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/states")
@RequiredArgsConstructor
public class StateController {
    private final StateService stateService;

    @GetMapping
    public ResponseEntity<List<State>> getAll(){
        return ResponseEntity.ok(stateService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> getById(@PathVariable Long id){
        State state = Entities.getOrThrow(
                stateService.getById(id), "Estado no encontrado");
        return ResponseEntity.ok(state);
    }
}
