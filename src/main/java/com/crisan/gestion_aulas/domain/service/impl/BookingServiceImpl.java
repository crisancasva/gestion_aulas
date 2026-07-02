package com.crisan.gestion_aulas.domain.service.impl;

import com.crisan.gestion_aulas.domain.model.Booking;
import com.crisan.gestion_aulas.domain.repository.BookingRepository;
import com.crisan.gestion_aulas.domain.repository.ClassroomRepository;
import com.crisan.gestion_aulas.domain.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;


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
        validateBookingTime(booking);
        validateBookingDate(booking);
        return bookingRepository.save(booking);
    }

    @Override
    public void delete(Long bookingId) {
        bookingRepository.delete(bookingId);
    }

    private void validateBookingTime(Booking booking) {
        if(!booking.getStartTime().isBefore(booking.getEndTime())) {
            throw new IllegalArgumentException(
                    "LA hora de inicio debe ser menor a la hora de fin. "
            );
        }
    }

    private void validateBookingDate(Booking booking) {
        if(!booking.getBookingDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "No se pueden hacer reservas en fechas pasadas. "
            );
        }
    }
}
