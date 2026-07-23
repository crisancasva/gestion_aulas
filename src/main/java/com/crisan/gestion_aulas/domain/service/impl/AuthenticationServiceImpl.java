package com.crisan.gestion_aulas.domain.service.impl;

import com.crisan.gestion_aulas.domain.model.User;
import com.crisan.gestion_aulas.domain.repository.UserRepository;
import com.crisan.gestion_aulas.domain.service.AuthenticationService;
import com.crisan.gestion_aulas.security.JwtService;
import com.crisan.gestion_aulas.web.dto.AuthenticationRequest;
import com.crisan.gestion_aulas.web.dto.AuthenticationResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse autenthicate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.getByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        String token = jwtService.generateToken(user.getEmail());

        return AuthenticationResponse.builder()
                        .token(token)
                                .build();
    }
}
