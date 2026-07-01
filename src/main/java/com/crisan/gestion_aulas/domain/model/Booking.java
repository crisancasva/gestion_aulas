package com.crisan.gestion_aulas.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    private Long bookingId;
    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDateTime createdAt;

    private User user;
    private Classroom classroom;
    private State state;
}
