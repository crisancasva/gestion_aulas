package com.crisan.gestion_aulas.web.controller;

import com.crisan.gestion_aulas.domain.service.AuthenticationService;
import com.crisan.gestion_aulas.web.dto.AuthenticationRequest;
import com.crisan.gestion_aulas.web.dto.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.autenthicate(request));
    }
}
