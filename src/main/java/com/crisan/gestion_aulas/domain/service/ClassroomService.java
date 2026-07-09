package com.crisan.gestion_aulas.domain.service;

import com.crisan.gestion_aulas.domain.model.Classroom;

import java.util.List;
import java.util.Optional;

public interface ClassroomService {
    List<Classroom> getAll();
    Optional<Classroom> getById(Long classroomId);
    Classroom createClassroom(Classroom classroom);
    void delete(Long classroomId);
}
