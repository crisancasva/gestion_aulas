package com.crisan.gestion_aulas.persistence.repository.crud;

import com.crisan.gestion_aulas.persistence.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingCrudRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findByBookingDate(LocalDate bookingDate);
    List<BookingEntity> findByClassroomClassroomIdAndBookingDate(
            Long classroomId,
            LocalDate bookingDate
    );
    List<BookingEntity> findByUserUserId(Long userId);
}
