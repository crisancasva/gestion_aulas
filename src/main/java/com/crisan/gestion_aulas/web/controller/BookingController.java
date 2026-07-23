package com.crisan.gestion_aulas.web.controller;

import com.crisan.gestion_aulas.common.util.Entities;
import com.crisan.gestion_aulas.common.util.Mappers;
import com.crisan.gestion_aulas.domain.model.Booking;
import com.crisan.gestion_aulas.domain.service.BookingService;
import com.crisan.gestion_aulas.web.dto.booking.BookingResponse;
import com.crisan.gestion_aulas.web.dto.booking.CreateBookingRequest;
import com.crisan.gestion_aulas.web.dto.booking.UpdateBookingRequest;
import com.crisan.gestion_aulas.web.mapper.BookingWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final BookingWebMapper bookingWebMapper;

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAll(){
        List<BookingResponse> bookings = Mappers.mapList(
                bookingService.getAll(), bookingWebMapper::toResponse);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getById(@PathVariable Long id){
        Booking booking = Entities.getOrThrow(
                bookingService.getById(id), "Reserva no encontrada");
        return ResponseEntity.ok(bookingWebMapper.toResponse(booking));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<BookingResponse>> getByDate(@PathVariable LocalDate date){
        List<BookingResponse> bookings = Mappers.mapList(
                bookingService.getByDate(date), bookingWebMapper::toResponse);

        return ResponseEntity.ok(bookings);
    }

    @PostMapping
    public ResponseEntity<BookingResponse> create(@Valid @RequestBody CreateBookingRequest request){
        Booking booking = bookingWebMapper.toDomain(request);
        Booking saved = bookingService.createBooking(booking);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingWebMapper.toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateBookingRequest request) {

        Booking booking = bookingWebMapper.toDomain(request);
        Booking updated = bookingService.updateBooking(id, booking);

        return ResponseEntity.ok(
                bookingWebMapper.toResponse(updated)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
