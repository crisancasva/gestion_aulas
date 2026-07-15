package com.crisan.gestion_aulas.web.dto.booking;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class CreateBookingRequest {
    @NotNull(message = "La fecha es obligatoria")
    @FutureOrPresent(message = "La fecha no puede ser anterior al día actual")
    private LocalDate bookingDate;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime startTime;

    @NotNull(message = "La hora de finalización es obligatoria")
    private LocalTime endTime;

    @NotNull(message = "El aula es obligatoria")
    private Long classroomId;

    @NotNull(message = "El usuario es obligatorio")
    private Long userId;

    @NotNull(message = "El estado es obligatorio")
    private Long stateId;
}
