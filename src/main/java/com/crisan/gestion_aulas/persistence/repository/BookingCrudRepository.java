package com.crisan.gestion_aulas.persistence.repository;

import com.crisan.gestion_aulas.persistence.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingCrudRepository extends JpaRepository<BookingEntity, Long> {
}
