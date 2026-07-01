package com.crisan.gestion_aulas.domain.repository;


import com.crisan.gestion_aulas.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> getAll();
    Optional<User> getById(Long userId);
    Optional<User> getByEmail(String email);
    User save(User user);
    void delete(Long userId);
}
