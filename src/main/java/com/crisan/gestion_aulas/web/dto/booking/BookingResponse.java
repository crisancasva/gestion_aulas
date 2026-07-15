package com.crisan.gestion_aulas.web.dto.booking;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class BookingResponse {
    private Long id;

    private LocalDate bookingDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String classroom;

    private String user;

    private String state;
}
