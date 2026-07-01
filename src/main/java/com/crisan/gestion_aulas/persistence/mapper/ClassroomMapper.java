package com.crisan.gestion_aulas.persistence.mapper;

import com.crisan.gestion_aulas.domain.model.Classroom;
import com.crisan.gestion_aulas.persistence.entity.ClassroomEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {StateMapper.class})
public interface ClassroomMapper {
    Classroom toClasroom(ClassroomEntity classroomEntity);
    ClassroomEntity toClassroomEntity(Classroom classroom);
}
