package com.crisan.gestion_aulas.persistence.repository.impl;

import com.crisan.gestion_aulas.domain.model.Booking;
import com.crisan.gestion_aulas.persistence.entity.BookingEntity;
import com.crisan.gestion_aulas.persistence.mapper.BookingMapper;
import com.crisan.gestion_aulas.persistence.repository.crud.BookingCrudRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingRepositoryImplTest {

    @Mock
    private BookingCrudRepository crud;

    @Mock
    private BookingMapper mapper;

    @InjectMocks
    private BookingRepositoryImpl repository;

    @Test
    void getAllMapsEntities() {
        List<BookingEntity> entities = List.of(new BookingEntity());
        List<Booking> domain = List.of(Booking.builder().bookingId(1L).build());
        when(crud.findAll()).thenReturn(entities);
        when(mapper.toBookings(entities)).thenReturn(domain);

        assertThat(repository.getAll()).isEqualTo(domain);
    }

    @Test
    void getByIdMapsPresentEntity() {
        BookingEntity entity = new BookingEntity();
        Booking booking = Booking.builder().bookingId(1L).build();
        when(crud.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toBooking(entity)).thenReturn(booking);

        assertThat(repository.getById(1L)).contains(booking);
    }

    @Test
    void getByIdReturnsEmptyWhenAbsent() {
        when(crud.findById(2L)).thenReturn(Optional.empty());
        assertThat(repository.getById(2L)).isEmpty();
    }

    @Test
    void getByDateMapsEntities() {
        LocalDate date = LocalDate.of(2030, 1, 1);
        List<BookingEntity> entities = List.of(new BookingEntity());
        when(crud.findByBookingDate(date)).thenReturn(entities);
        when(mapper.toBookings(entities)).thenReturn(List.of(Booking.builder().build()));

        assertThat(repository.getByDate(date)).hasSize(1);
    }

    @Test
    void getByClassroomAndDateMapsEntities() {
        LocalDate date = LocalDate.of(2030, 1, 1);
        List<BookingEntity> entities = List.of(new BookingEntity());
        when(crud.findByClassroomClassroomIdAndBookingDate(5L, date)).thenReturn(entities);
        when(mapper.toBookings(entities)).thenReturn(List.of(Booking.builder().build()));

        assertThat(repository.getByClassroomAndDate(5L, date)).hasSize(1);
    }

    @Test
    void saveMapsBothDirections() {
        Booking booking = Booking.builder().bookingId(1L).build();
        BookingEntity entity = new BookingEntity();
        BookingEntity saved = new BookingEntity();
        when(mapper.toBookingEntity(booking)).thenReturn(entity);
        when(crud.save(entity)).thenReturn(saved);
        when(mapper.toBooking(saved)).thenReturn(booking);

        assertThat(repository.save(booking)).isEqualTo(booking);
    }

    @Test
    void deleteDelegatesToCrud() {
        repository.delete(9L);
        verify(crud).deleteById(9L);
    }
}
