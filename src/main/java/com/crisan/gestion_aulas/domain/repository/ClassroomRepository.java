package com.crisan.gestion_aulas.domain.repository;

import com.crisan.gestion_aulas.domain.model.Classroom;

import java.util.List;
import java.util.Optional;

public interface ClassroomRepository {
    List<Classroom> getAll();
    Optional<Classroom> getById(Long classroomId);
    Classroom save(Classroom classroom);
    void delete(Long classroomId);
}
