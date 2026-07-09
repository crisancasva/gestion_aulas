package com.crisan.gestion_aulas.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApiError {
    private LocalDateTime timeStamp;
    private int status;
    private String error;
    private String message;

}
