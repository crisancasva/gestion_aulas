package com.crisan.gestion_aulas.persistence.repository.impl;

import com.crisan.gestion_aulas.domain.model.Classroom;
import com.crisan.gestion_aulas.domain.repository.ClassroomRepository;
import com.crisan.gestion_aulas.persistence.mapper.ClassroomMapper;
import com.crisan.gestion_aulas.persistence.repository.crud.ClassroomCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClassroomRepositoryImpl implements ClassroomRepository {
    private final ClassroomCrudRepository classroomCrudRepository;
    private final ClassroomMapper classroomMapper;

    @Override
    public List<Classroom> getAll() {
        return classroomMapper.toClassrooms(classroomCrudRepository.findAll());
    }

    @Override
    public Optional<Classroom> getById(Long classroomId) {
        return classroomCrudRepository.findById(classroomId).map(classroomMapper::toClasroom);
    }

    @Override
    public Classroom save(Classroom classroom) {
        return classroomMapper.toClasroom(classroomCrudRepository.save(classroomMapper.toClassroomEntity(classroom)));
    }

    @Override
    public void delete(Long classroomId) {
        classroomCrudRepository.deleteById(classroomId);
    }
}
