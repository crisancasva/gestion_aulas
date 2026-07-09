package com.crisan.gestion_aulas.domain.service;

import com.crisan.gestion_aulas.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();
    Optional<User> getById(Long userId);
    Optional<User> getByEmail(String email);
    User createUser(User user);
    void delete(Long userId);
}
