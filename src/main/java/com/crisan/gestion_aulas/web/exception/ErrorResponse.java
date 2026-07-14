package com.crisan.gestion_aulas.web.exception;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
}
