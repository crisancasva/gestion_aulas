package com.crisan.gestion_aulas.web.mapper;

import com.crisan.gestion_aulas.domain.model.Classroom;
import com.crisan.gestion_aulas.domain.model.State;
import com.crisan.gestion_aulas.web.dto.classroom.ClassroomResponse;
import com.crisan.gestion_aulas.web.dto.classroom.CreateClassroomRequest;
import com.crisan.gestion_aulas.web.dto.classroom.UpdateClassroomRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClassroomWebMapperTest {

    private final ClassroomWebMapper mapper = new ClassroomWebMapper();

    @Test
    void toDomainFromCreateRequestMapsAllFields() {
        CreateClassroomRequest request = new CreateClassroomRequest();
        request.setName("A1");
        request.setCapacity(30);
        request.setLocation("Building 1");
        request.setStateId(1L);

        Classroom classroom = mapper.toDomain(request);

        assertThat(classroom.getName()).isEqualTo("A1");
        assertThat(classroom.getCapacity()).isEqualTo(30);
        assertThat(classroom.getLocation()).isEqualTo("Building 1");
        assertThat(classroom.getState().getStateId()).isEqualTo(1L);
    }

    @Test
    void toDomainFromUpdateRequestMapsAllFields() {
        UpdateClassroomRequest request = new UpdateClassroomRequest();
        request.setName("B2");
        request.setCapacity(15);
        request.setLocation("Building 2");
        request.setStateId(2L);

        Classroom classroom = mapper.toDomain(request);

        assertThat(classroom.getName()).isEqualTo("B2");
        assertThat(classroom.getCapacity()).isEqualTo(15);
        assertThat(classroom.getState().getStateId()).isEqualTo(2L);
    }

    @Test
    void toResponseMapsStateDescription() {
        Classroom classroom = Classroom.builder()
                .classroomId(9L)
                .name("A1")
                .capacity(20)
                .location("Building 1")
                .state(State.builder().stateDescription("ACTIVE").build())
                .build();

        ClassroomResponse response = mapper.toResponse(classroom);

        assertThat(response.getId()).isEqualTo(9L);
        assertThat(response.getName()).isEqualTo("A1");
        assertThat(response.getCapacity()).isEqualTo(20);
        assertThat(response.getState()).isEqualTo("ACTIVE");
    }
}
