package com.crisan.gestion_aulas.domain.service.impl;

import com.crisan.gestion_aulas.domain.model.Classroom;
import com.crisan.gestion_aulas.domain.repository.ClassroomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClassroomServiceImplTest {

    @Mock
    private ClassroomRepository classroomRepository;

    @InjectMocks
    private ClassroomServiceImpl classroomService;

    private Classroom classroom(Long id, Integer capacity) {
        return Classroom.builder()
                .classroomId(id)
                .name("A1")
                .capacity(capacity)
                .location("Building 1")
                .build();
    }

    @Test
    void getAllDelegatesToRepository() {
        when(classroomRepository.getAll()).thenReturn(List.of(classroom(1L, 10)));
        assertThat(classroomService.getAll()).hasSize(1);
        verify(classroomRepository).getAll();
    }

    @Test
    void getByIdDelegatesToRepository() {
        when(classroomRepository.getById(1L)).thenReturn(Optional.of(classroom(1L, 10)));
        assertThat(classroomService.getById(1L)).isPresent();
    }

    @Test
    void deleteDelegatesToRepository() {
        classroomService.delete(3L);
        verify(classroomRepository).delete(3L);
    }

    @Test
    void createSavesWhenValid() {
        Classroom c = classroom(null, 20);
        when(classroomRepository.getByNameAndLocation("A1", "Building 1"))
                .thenReturn(Optional.empty());
        when(classroomRepository.save(c)).thenReturn(classroom(1L, 20));

        assertThat(classroomService.create(c).getClassroomId()).isEqualTo(1L);
        verify(classroomRepository).save(c);
    }

    @Test
    void createRejectsNullCapacity() {
        assertThatThrownBy(() -> classroomService.create(classroom(null, null)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("capacidad");
        verify(classroomRepository, never()).save(any());
    }

    @Test
    void createRejectsNonPositiveCapacity() {
        assertThatThrownBy(() -> classroomService.create(classroom(null, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("mayor que cero");
    }

    @Test
    void createRejectsDuplicateNameAndLocation() {
        Classroom c = classroom(null, 20);
        when(classroomRepository.getByNameAndLocation("A1", "Building 1"))
                .thenReturn(Optional.of(classroom(9L, 20)));

        assertThatThrownBy(() -> classroomService.create(c))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ya existe un aula");
        verify(classroomRepository, never()).save(any());
    }

    @Test
    void updateThrowsWhenNotFound() {
        when(classroomRepository.getById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> classroomService.update(1L, classroom(1L, 20)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Aula no encontrada");
    }

    @Test
    void updateChangesFieldsAndSaves() {
        Classroom existing = classroom(1L, 5);
        Classroom incoming = Classroom.builder()
                .name("B2").capacity(30).location("Building 2").build();
        when(classroomRepository.getById(1L)).thenReturn(Optional.of(existing));
        when(classroomRepository.getByNameAndLocation("B2", "Building 2"))
                .thenReturn(Optional.empty());
        when(classroomRepository.save(any(Classroom.class))).thenAnswer(inv -> inv.getArgument(0));

        Classroom result = classroomService.update(1L, incoming);

        ArgumentCaptor<Classroom> captor = ArgumentCaptor.forClass(Classroom.class);
        verify(classroomRepository).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("B2");
        assertThat(captor.getValue().getCapacity()).isEqualTo(30);
        assertThat(result.getLocation()).isEqualTo("Building 2");
    }

    @Test
    void updateAllowsSameClassroomKeepingName() {
        Classroom existing = classroom(1L, 5);
        Classroom incoming = classroom(1L, 15);
        when(classroomRepository.getById(1L)).thenReturn(Optional.of(existing));
        when(classroomRepository.getByNameAndLocation("A1", "Building 1"))
                .thenReturn(Optional.of(classroom(1L, 5)));
        when(classroomRepository.save(any(Classroom.class))).thenAnswer(inv -> inv.getArgument(0));

        assertThat(classroomService.update(1L, incoming)).isNotNull();
    }

    @Test
    void updateRejectsNameUsedByAnotherClassroom() {
        Classroom existing = classroom(1L, 5);
        Classroom incoming = classroom(1L, 15);
        when(classroomRepository.getById(1L)).thenReturn(Optional.of(existing));
        when(classroomRepository.getByNameAndLocation("A1", "Building 1"))
                .thenReturn(Optional.of(classroom(2L, 5)));

        assertThatThrownBy(() -> classroomService.update(1L, incoming))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ya existe un aula");
        verify(classroomRepository, never()).save(any());
    }
}
