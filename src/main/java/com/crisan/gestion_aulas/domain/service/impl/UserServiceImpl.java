package com.crisan.gestion_aulas.domain.service.impl;

import com.crisan.gestion_aulas.common.util.Entities;
import com.crisan.gestion_aulas.domain.model.User;
import com.crisan.gestion_aulas.domain.repository.UserRepository;
import com.crisan.gestion_aulas.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public Optional<User> getById(Long userId) {
        return userRepository.getById(userId);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public User create(User user) {

        validateEmail(user.getEmail());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        User existingUser = Entities.getOrThrow(
                userRepository.getById(id), "Usuario no encontrado");

        validateEmailForUpdate(id, user.getEmail());

        existingUser.setName(user.getName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        existingUser.setState(user.getState());

        return userRepository.save(existingUser);
    }

    @Override
    public void delete(Long userId) {
        userRepository.delete(userId);
    }


    private void validateEmail(String email) {

        userRepository.getByEmail(email)
                .ifPresent(u -> {
                    throw new IllegalArgumentException(
                            "Ya existe un usuario con ese correo."
                    );
                });
    }

    private void validateEmailForUpdate(Long id, String email){
        userRepository.getByEmail(email)
                .ifPresent(user -> {
                    if(!user.getUserId().equals(id)){
                        throw new IllegalArgumentException("Ya existe un usuario con ese correo");
                    }
                });
    }

  }
