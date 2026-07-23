package com.crisan.gestion_aulas.web.controller;

import com.crisan.gestion_aulas.domain.model.Classroom;
import com.crisan.gestion_aulas.domain.service.ClassroomService;
import com.crisan.gestion_aulas.web.dto.classroom.ClassroomResponse;
import com.crisan.gestion_aulas.web.dto.classroom.CreateClassroomRequest;
import com.crisan.gestion_aulas.web.dto.classroom.UpdateClassroomRequest;
import com.crisan.gestion_aulas.web.mapper.ClassroomWebMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClassroomControllerTest {

    @Mock
    private ClassroomService classroomService;

    @Mock
    private ClassroomWebMapper classroomWebMapper;

    @InjectMocks
    private ClassroomController controller;

    private final ClassroomResponse response = ClassroomResponse.builder().id(1L).build();

    @Test
    void getAllReturnsMappedResponses() {
        Classroom classroom = Classroom.builder().classroomId(1L).build();
        when(classroomService.getAll()).thenReturn(List.of(classroom));
        when(classroomWebMapper.toResponse(classroom)).thenReturn(response);

        assertThat(controller.getAll().getBody()).containsExactly(response);
    }

    @Test
    void getByIdReturnsResponse() {
        Classroom classroom = Classroom.builder().classroomId(1L).build();
        when(classroomService.getById(1L)).thenReturn(Optional.of(classroom));
        when(classroomWebMapper.toResponse(classroom)).thenReturn(response);

        assertThat(controller.getById(1L).getBody()).isEqualTo(response);
    }

    @Test
    void getByIdThrowsWhenMissing() {
        when(classroomService.getById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> controller.getById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Aula no encontrada");
    }

    @Test
    void createReturnsCreatedStatus() {
        CreateClassroomRequest request = new CreateClassroomRequest();
        Classroom domain = Classroom.builder().build();
        Classroom saved = Classroom.builder().classroomId(1L).build();
        when(classroomWebMapper.toDomain(request)).thenReturn(domain);
        when(classroomService.create(domain)).thenReturn(saved);
        when(classroomWebMapper.toResponse(saved)).thenReturn(response);

        ResponseEntity<ClassroomResponse> result = controller.create(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void updateReturnsOk() {
        UpdateClassroomRequest request = new UpdateClassroomRequest();
        Classroom domain = Classroom.builder().build();
        Classroom updated = Classroom.builder().classroomId(1L).build();
        when(classroomWebMapper.toDomain(request)).thenReturn(domain);
        when(classroomService.update(1L, domain)).thenReturn(updated);
        when(classroomWebMapper.toResponse(updated)).thenReturn(response);

        assertThat(controller.update(1L, request).getBody()).isEqualTo(response);
    }

    @Test
    void deleteReturnsNoContent() {
        ResponseEntity<Void> result = controller.delete(2L);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(classroomService).delete(2L);
    }
}
