package com.crisan.gestion_aulas.web.controller;
import com.crisan.gestion_aulas.domain.model.User;
import com.crisan.gestion_aulas.domain.service.UserService;
import com.crisan.gestion_aulas.web.dto.user.CreateUserRequest;
import com.crisan.gestion_aulas.web.dto.user.UpdateUserRequest;
import com.crisan.gestion_aulas.web.dto.user.UserResponse;
import com.crisan.gestion_aulas.web.mapper.UserWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserWebMapper userWebMapper;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll(){
        List<UserResponse> response = userService.getAll()
                .stream()
                .map(userWebMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id){
        User user = userService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return ResponseEntity.ok(userWebMapper.toResponse(user));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getByEmail(@PathVariable String email) {
        User user = userService.getByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        return ResponseEntity.ok(userWebMapper.toResponse(user));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(
            @Valid @RequestBody CreateUserRequest request) {

        User user = userWebMapper.toDomain(request);

        User saved = userService.create(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userWebMapper.toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        User user = userWebMapper.toDomain(request);
        User updated = userService.update(id, user);
        return ResponseEntity.ok(userWebMapper.toResponse(updated));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}