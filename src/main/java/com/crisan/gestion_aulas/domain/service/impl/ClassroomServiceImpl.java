package com.crisan.gestion_aulas.domain.service.impl;

import com.crisan.gestion_aulas.domain.model.Classroom;
import com.crisan.gestion_aulas.domain.repository.ClassroomRepository;
import com.crisan.gestion_aulas.domain.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassroomRepository classroomRepository;

    @Override
    public List<Classroom> getAll() {
        return classroomRepository.getAll();
    }

    @Override
    public Optional<Classroom> getById(Long classroomId) {
        return classroomRepository.getById(classroomId);
    }

    @Override
    public Classroom createClassroom(Classroom classroom) {
        validateCapacity(classroom);
        validateClassroomExists(classroom);
        return classroomRepository.save(classroom);
    }

    @Override
    public void delete(Long classroomId) {
        classroomRepository.delete(classroomId);
    }
    private void validateClassroomExists(Classroom classroom) {

        classroomRepository
                .getByNameAndLocation(
                        classroom.getName(),
                        classroom.getLocation()
                )
                .ifPresent(c -> {
                    throw new IllegalArgumentException(
                            "Ya existe un aula con ese nombre en esa ubicación."
                    );
                });
    }
    private void validateCapacity(Classroom classroom) {

        if (classroom.getCapacity() == null || classroom.getCapacity() <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad del aula debe ser mayor que cero."
            );
        }
    }

}
