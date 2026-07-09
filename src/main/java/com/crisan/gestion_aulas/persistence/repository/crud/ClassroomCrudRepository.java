package com.crisan.gestion_aulas.persistence.repository.crud;

import com.crisan.gestion_aulas.persistence.entity.ClassroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassroomCrudRepository extends JpaRepository<ClassroomEntity, Long> {
    Optional<ClassroomEntity> findByNameAndLocation(String name, String location);
}
