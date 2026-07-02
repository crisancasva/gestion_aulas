package com.crisan.gestion_aulas.web.controller;

import com.crisan.gestion_aulas.domain.model.Booking;
import com.crisan.gestion_aulas.domain.service.BookingService;
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

    @GetMapping
    public ResponseEntity<List<Booking>> getAll(){
        return ResponseEntity.ok(bookingService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getById(@PathVariable Long id){
        Booking booking = bookingService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Booking>> getByDate(@PathVariable LocalDate date){
        return ResponseEntity.ok(bookingService.getByDate(date));
    }

    @PostMapping
    public ResponseEntity<Booking> create(@RequestBody Booking booking){
        Booking bookingCreated = bookingService.createBooking(booking);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingCreated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
