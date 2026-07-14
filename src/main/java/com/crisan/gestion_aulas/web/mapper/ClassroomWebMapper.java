package com.crisan.gestion_aulas.web.mapper;

import com.crisan.gestion_aulas.domain.model.Classroom;
import com.crisan.gestion_aulas.domain.model.State;
import com.crisan.gestion_aulas.web.dto.classroom.ClassroomResponse;
import com.crisan.gestion_aulas.web.dto.classroom.CreateClassroomRequest;
import com.crisan.gestion_aulas.web.dto.classroom.UpdateClassroomRequest;
import org.springframework.stereotype.Component;

@Component
public class ClassroomWebMapper {
    public Classroom toDomain(CreateClassroomRequest request) {
        return Classroom.builder()
                .name(request.getName())
                .capacity(request.getCapacity())
                .location(request.getLocation())
                .state(State.builder()
                        .stateId(request.getStateId())
                        .build())
                .build();
    }

    public Classroom toDomain(UpdateClassroomRequest request){
        return Classroom.builder()
                .name(request.getName())
                .capacity(request.getCapacity())
                .location(request.getLocation())
                .state(State.builder()
                        .stateId(request.getStateId())
                        .build())
                .build();
    }

    public ClassroomResponse toResponse(Classroom classroom) {
        return ClassroomResponse.builder()
                .id(classroom.getClassroomId())
                .name(classroom.getName())
                .capacity(classroom.getCapacity())
                .location(classroom.getLocation())
                .state(classroom.getState().getStateDescription())
                .build();
    }

}
