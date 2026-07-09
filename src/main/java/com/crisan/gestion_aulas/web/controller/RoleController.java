package com.crisan.gestion_aulas.web.controller;

import com.crisan.gestion_aulas.domain.model.Role;
import com.crisan.gestion_aulas.domain.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getAll(){
        return ResponseEntity.ok(roleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getById(@PathVariable Long id){
        Role role = roleService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
        return ResponseEntity.ok(role);
    }

}
