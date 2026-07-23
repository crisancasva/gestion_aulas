package com.crisan.gestion_aulas.web.controller;

import com.crisan.gestion_aulas.domain.model.Booking;
import com.crisan.gestion_aulas.domain.service.BookingService;
import com.crisan.gestion_aulas.web.dto.booking.BookingResponse;
import com.crisan.gestion_aulas.web.dto.booking.CreateBookingRequest;
import com.crisan.gestion_aulas.web.dto.booking.UpdateBookingRequest;
import com.crisan.gestion_aulas.web.mapper.BookingWebMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @Mock
    private BookingWebMapper bookingWebMapper;

    @InjectMocks
    private BookingController controller;

    private final BookingResponse response = BookingResponse.builder().id(1L).build();

    @Test
    void getAllReturnsMappedResponses() {
        Booking booking = Booking.builder().bookingId(1L).build();
        when(bookingService.getAll()).thenReturn(List.of(booking));
        when(bookingWebMapper.toResponse(booking)).thenReturn(response);

        ResponseEntity<List<BookingResponse>> result = controller.getAll();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).containsExactly(response);
    }

    @Test
    void getByIdReturnsResponse() {
        Booking booking = Booking.builder().bookingId(1L).build();
        when(bookingService.getById(1L)).thenReturn(Optional.of(booking));
        when(bookingWebMapper.toResponse(booking)).thenReturn(response);

        assertThat(controller.getById(1L).getBody()).isEqualTo(response);
    }

    @Test
    void getByIdThrowsWhenMissing() {
        when(bookingService.getById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> controller.getById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Reserva no encontrada");
    }

    @Test
    void getByDateReturnsMappedResponses() {
        LocalDate date = LocalDate.of(2030, 1, 1);
        Booking booking = Booking.builder().build();
        when(bookingService.getByDate(date)).thenReturn(List.of(booking));
        when(bookingWebMapper.toResponse(booking)).thenReturn(response);

        assertThat(controller.getByDate(date).getBody()).containsExactly(response);
    }

    @Test
    void createReturnsCreatedStatus() {
        CreateBookingRequest request = new CreateBookingRequest();
        Booking domain = Booking.builder().build();
        Booking saved = Booking.builder().bookingId(1L).build();
        when(bookingWebMapper.toDomain(request)).thenReturn(domain);
        when(bookingService.createBooking(domain)).thenReturn(saved);
        when(bookingWebMapper.toResponse(saved)).thenReturn(response);

        ResponseEntity<BookingResponse> result = controller.create(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void updateReturnsOk() {
        UpdateBookingRequest request = new UpdateBookingRequest();
        Booking domain = Booking.builder().build();
        Booking updated = Booking.builder().bookingId(1L).build();
        when(bookingWebMapper.toDomain(request)).thenReturn(domain);
        when(bookingService.updateBooking(1L, domain)).thenReturn(updated);
        when(bookingWebMapper.toResponse(updated)).thenReturn(response);

        assertThat(controller.update(1L, request).getBody()).isEqualTo(response);
    }

    @Test
    void deleteReturnsNoContent() {
        ResponseEntity<Void> result = controller.delete(3L);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(bookingService).delete(3L);
    }
}
