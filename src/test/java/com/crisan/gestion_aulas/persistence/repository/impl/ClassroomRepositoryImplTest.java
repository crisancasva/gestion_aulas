package com.crisan.gestion_aulas.persistence.repository.impl;

import com.crisan.gestion_aulas.domain.model.Classroom;
import com.crisan.gestion_aulas.persistence.entity.ClassroomEntity;
import com.crisan.gestion_aulas.persistence.mapper.ClassroomMapper;
import com.crisan.gestion_aulas.persistence.repository.crud.ClassroomCrudRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClassroomRepositoryImplTest {

    @Mock
    private ClassroomCrudRepository crud;

    @Mock
    private ClassroomMapper mapper;

    @InjectMocks
    private ClassroomRepositoryImpl repository;

    @Test
    void getAllMapsEntities() {
        List<ClassroomEntity> entities = List.of(new ClassroomEntity());
        when(crud.findAll()).thenReturn(entities);
        when(mapper.toClassrooms(entities)).thenReturn(List.of(Classroom.builder().build()));

        assertThat(repository.getAll()).hasSize(1);
    }

    @Test
    void getByIdMapsPresentEntity() {
        ClassroomEntity entity = new ClassroomEntity();
        Classroom classroom = Classroom.builder().classroomId(1L).build();
        when(crud.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toClassroom(entity)).thenReturn(classroom);

        assertThat(repository.getById(1L)).contains(classroom);
    }

    @Test
    void getByNameAndLocationMapsResult() {
        ClassroomEntity entity = new ClassroomEntity();
        Classroom classroom = Classroom.builder().classroomId(1L).build();
        when(crud.findByNameAndLocation("A1", "B1")).thenReturn(Optional.of(entity));
        when(mapper.toClassroom(entity)).thenReturn(classroom);

        assertThat(repository.getByNameAndLocation("A1", "B1")).contains(classroom);
    }

    @Test
    void saveMapsBothDirections() {
        Classroom classroom = Classroom.builder().classroomId(1L).build();
        ClassroomEntity entity = new ClassroomEntity();
        ClassroomEntity saved = new ClassroomEntity();
        when(mapper.toClassroomEntity(classroom)).thenReturn(entity);
        when(crud.save(entity)).thenReturn(saved);
        when(mapper.toClassroom(saved)).thenReturn(classroom);

        assertThat(repository.save(classroom)).isEqualTo(classroom);
    }

    @Test
    void deleteDelegatesToCrud() {
        repository.delete(4L);
        verify(crud).deleteById(4L);
    }
}
