package com.crisan.gestion_aulas.persistence.repository.impl;

import com.crisan.gestion_aulas.domain.model.Booking;
import com.crisan.gestion_aulas.domain.repository.BookingRepository;
import com.crisan.gestion_aulas.persistence.mapper.BookingMapper;
import com.crisan.gestion_aulas.persistence.repository.crud.BookingCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookingRepositoryImpl implements BookingRepository {

    private final BookingCrudRepository bookingCrudRepository;
    private final BookingMapper bookingMapper;

    @Override
    public List<Booking> getAll() {
        return bookingMapper.toBookings(bookingCrudRepository.findAll());
    }

    @Override
    public Optional<Booking> getById(Long bookingId) {
        return bookingCrudRepository.findById(bookingId).map(bookingMapper::toBooking);
    }

    @Override
    public List<Booking> getByDate(LocalDate bookingDate) {

        return bookingMapper.toBookings(
                bookingCrudRepository.findByBookingDate(bookingDate));
    }

    @Override
    public Booking save(Booking booking) {
         return bookingMapper.toBooking(bookingCrudRepository.save(bookingMapper.toBookingEntity(booking)));
    }

    @Override
    public List<Booking> getByClassroomAndDate(Long classroomId, LocalDate bookingDate) {

        return bookingMapper.toBookings(
                bookingCrudRepository.findByClassroomClassroomIdAndBookingDate(
                        classroomId,
                        bookingDate
                )
        );
    }

    @Override
    public List<Booking> getByUser(Long userId) {
        return bookingMapper.toBookings(
                bookingCrudRepository.findByUserUserId(userId));
    }

    @Override
    public void delete(Long bookingId) {
        bookingCrudRepository.deleteById(bookingId);
    }
}
