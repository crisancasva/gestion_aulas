package com.crisan.gestion_aulas.web.exception;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handlesIllegalArgumentExceptionAsBadRequest() {
        ResponseEntity<ErrorResponse> response =
                handler.handleIllegalArgumentException(new IllegalArgumentException("invalid"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(400);
        assertThat(response.getBody().getMessage()).isEqualTo("invalid");
        assertThat(response.getBody().getTimestamp()).isNotNull();
    }

    @Test
    void handlesEntityNotFoundAsNotFound() {
        ResponseEntity<ErrorResponse> response =
                handler.handleEntityNotFound(new EntityNotFoundException("missing"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(404);
        assertThat(response.getBody().getMessage()).isEqualTo("missing");
    }

    @Test
    void handlesGenericExceptionAsInternalServerError() {
        ResponseEntity<ErrorResponse> response =
                handler.handleException(new RuntimeException("boom"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(500);
        assertThat(response.getBody().getMessage()).isEqualTo("Ha ocurrido un error interno.");
    }
}
