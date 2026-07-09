package com.crisan.gestion_aulas.web.controller;
import com.crisan.gestion_aulas.domain.model.User;
import com.crisan.gestion_aulas.domain.service.UserService;
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

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        User user = userService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getByEmail(@PathVariable String email) {
        User user = userService.getByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user){
        User userCreated = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userCreated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}