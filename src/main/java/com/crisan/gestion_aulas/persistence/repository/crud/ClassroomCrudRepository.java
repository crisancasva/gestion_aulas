package com.crisan.gestion_aulas.persistence.repository.crud;

import com.crisan.gestion_aulas.persistence.entity.ClassroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomCrudRepository extends JpaRepository<ClassroomEntity, Long> {
}
