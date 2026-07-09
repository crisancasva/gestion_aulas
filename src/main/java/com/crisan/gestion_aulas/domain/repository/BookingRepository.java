package com.crisan.gestion_aulas.domain.repository;

import com.crisan.gestion_aulas.domain.model.Booking;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    List<Booking> getAll();
    Optional<Booking> getById(Long bookingId);
    List<Booking> getByDate(LocalDate bookingDate);
    Booking save(Booking booking);
    List<Booking> getByClassroomAndDate(Long classroomId, LocalDate bookingDate);
    void delete(Long bookingId);
}
