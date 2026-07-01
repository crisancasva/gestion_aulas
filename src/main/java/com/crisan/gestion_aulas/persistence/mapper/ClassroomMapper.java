package com.crisan.gestion_aulas.persistence.mapper;

import com.crisan.gestion_aulas.domain.model.Classroom;
import com.crisan.gestion_aulas.persistence.entity.ClassroomEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StateMapper.class})
public interface ClassroomMapper {
    Classroom toClasroom(ClassroomEntity classroomEntity);
    ClassroomEntity toClassroomEntity(Classroom classroom);
    List<Classroom> toClassrooms(List<ClassroomEntity> classroomEntities);
    List<ClassroomEntity> toClassroomEntities(List<Classroom> classrooms);
}
