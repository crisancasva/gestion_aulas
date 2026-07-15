package com.crisan.gestion_aulas.domain.service.impl;

import com.crisan.gestion_aulas.domain.model.Booking;
import com.crisan.gestion_aulas.domain.repository.BookingRepository;
import com.crisan.gestion_aulas.domain.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    @Override
    public List<Booking> getAll() {
        return bookingRepository.getAll();
    }

    @Override
    public Optional<Booking> getById(Long bookingId) {
        return bookingRepository.getById(bookingId);
    }

    @Override
    public List<Booking> getByDate(LocalDate bookingDate) {
        return bookingRepository.getByDate(bookingDate);
    }

    @Override
    public Booking createBooking(Booking booking) {
        validateBookingDate(booking);
        validateBookingTime(booking);
        validateAvailability(booking);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBooking(Long id, Booking booking) {
        Booking existingBooking = bookingRepository.getById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Reserva no encontrada"));

        validateBookingDate(booking);
        validateBookingTime(booking);
        validateAvailabilityForUpdate(id, booking);

        existingBooking.setBookingDate(booking.getBookingDate());
        existingBooking.setStartTime(booking.getStartTime());
        existingBooking.setEndTime(booking.getEndTime());
        existingBooking.setClassroom(booking.getClassroom());
        existingBooking.setState(booking.getState());

        return bookingRepository.save(existingBooking);
    }

    @Override
    public void delete(Long bookingId) {
        bookingRepository.delete(bookingId);
    }

    private void validateBookingTime(Booking booking) {

        if (booking.getStartTime() == null || booking.getEndTime() == null) {
            throw new IllegalArgumentException(
                    "Debe ingresar la hora de inicio y la hora de fin."
            );
        }

        if (!booking.getStartTime().isBefore(booking.getEndTime())) {
            throw new IllegalArgumentException(
                    "La hora de inicio debe ser menor que la hora de fin."
            );
        }
    }

    private void validateBookingDate(Booking booking) {

        if (booking.getBookingDate() == null) {
            throw new IllegalArgumentException(
                    "Debe ingresar la fecha de la reserva."
            );
        }

        if (booking.getBookingDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "No se pueden hacer reservas en fechas pasadas."
            );
        }
    }

    private void validateAvailability(Booking booking) {

        List<Booking> bookings = bookingRepository.getByClassroomAndDate(
                booking.getClassroom().getClassroomId(),
                booking.getBookingDate()
        );

        for (Booking existingBooking : bookings) {

            boolean overlap =
                    booking.getStartTime().isBefore(existingBooking.getEndTime())
                            && booking.getEndTime().isAfter(existingBooking.getStartTime());

            if (overlap) {
                throw new IllegalArgumentException(
                        "El aula ya está reservada en ese horario."
                );
            }
        }
    }

    private void validateAvailabilityForUpdate(Long bookingId, Booking booking){
        List<Booking> bookings = bookingRepository.getByClassroomAndDate(
                booking.getClassroom().getClassroomId(),
                booking.getBookingDate()
        );

        for (Booking existing : bookings) {

            if (existing.getBookingId().equals(bookingId)) {
                continue;
            }

            boolean overlap =
                    booking.getStartTime().isBefore(existing.getEndTime())
                            && booking.getEndTime().isAfter(existing.getStartTime());

            if (overlap) {
                throw new IllegalArgumentException(
                        "El aula ya está reservada en ese horario."
                );
            }
        }
    }
}
