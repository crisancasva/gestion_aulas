package com.crisan.gestion_aulas.domain.service.impl;

import com.crisan.gestion_aulas.domain.model.User;
import com.crisan.gestion_aulas.domain.repository.UserRepository;
import com.crisan.gestion_aulas.security.JwtService;
import com.crisan.gestion_aulas.web.dto.AuthenticationRequest;
import com.crisan.gestion_aulas.web.dto.AuthenticationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    void authenticateReturnsTokenOnSuccess() {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("user@b.com").password("pass").build();
        when(authenticationManager.authenticate(any())).thenReturn(mock());
        when(userRepository.getByEmail("user@b.com"))
                .thenReturn(Optional.of(User.builder().email("user@b.com").build()));
        when(jwtService.generateToken("user@b.com")).thenReturn("jwt-token");

        AuthenticationResponse response = authenticationService.autenthicate(request);

        assertThat(response.getToken()).isEqualTo("jwt-token");
        verify(authenticationManager).authenticate(any());
    }

    @Test
    void authenticatePropagatesAuthenticationFailure() {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("user@b.com").password("wrong").build();
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("bad"));

        assertThatThrownBy(() -> authenticationService.autenthicate(request))
                .isInstanceOf(BadCredentialsException.class);
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void authenticateThrowsWhenUserNotFound() {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("missing@b.com").password("pass").build();
        when(authenticationManager.authenticate(any())).thenReturn(mock());
        when(userRepository.getByEmail("missing@b.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authenticationService.autenthicate(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuario no encontrado");
    }

    private static Authentication mock() {
        return org.mockito.Mockito.mock(Authentication.class);
    }
}
