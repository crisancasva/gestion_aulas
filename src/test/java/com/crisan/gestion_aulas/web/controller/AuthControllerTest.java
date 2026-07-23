package com.crisan.gestion_aulas.web.controller;

import com.crisan.gestion_aulas.domain.service.AuthenticationService;
import com.crisan.gestion_aulas.web.dto.AuthenticationRequest;
import com.crisan.gestion_aulas.web.dto.AuthenticationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthController controller;

    @Test
    void loginReturnsTokenResponse() {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("a@b.com").password("pass").build();
        AuthenticationResponse response = AuthenticationResponse.builder().token("jwt").build();
        when(authenticationService.autenthicate(request)).thenReturn(response);

        ResponseEntity<AuthenticationResponse> result = controller.login(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
    }
}
