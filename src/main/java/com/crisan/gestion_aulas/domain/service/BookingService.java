package com.crisan.gestion_aulas.domain.service;

import com.crisan.gestion_aulas.domain.model.Booking;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    List<Booking> getAll();

    Optional<Booking> getById(Long bookingId);

    List<Booking> getByDate(LocalDate bookingDate);

    Booking createBooking(Booking booking);

    Booking updateBooking(Long id, Booking booking);

    void delete(Long bookingId);
}
