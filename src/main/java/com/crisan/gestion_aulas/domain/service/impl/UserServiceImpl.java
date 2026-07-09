package com.crisan.gestion_aulas.domain.service.impl;

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
    public User createUser(User user) {
        validateUser(user);
        validateEmail(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public void delete(Long userId) {
        userRepository.delete(userId);
    }


    private void validateUser(User user) {

        if (user.getName() == null || user.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        if (user.getLastName() == null || user.getLastName().isBlank()) {
            throw new IllegalArgumentException("El apellido es obligatorio.");
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("El correo es obligatorio.");
        }

        if (!user.getEmail().contains("@")) {
            throw new IllegalArgumentException("El correo no tiene un formato válido.");
        }
    }

    private void validateEmail(User user) {

        userRepository.getByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException(
                            "Ya existe un usuario con ese correo."
                    );
                });
    }

  }
