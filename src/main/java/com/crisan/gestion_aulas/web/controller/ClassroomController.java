package com.crisan.gestion_aulas.web.controller;

import com.crisan.gestion_aulas.domain.model.Classroom;
import com.crisan.gestion_aulas.domain.service.ClassroomService;
import com.crisan.gestion_aulas.web.dto.classroom.ClassroomResponse;
import com.crisan.gestion_aulas.web.dto.classroom.CreateClassroomRequest;
import com.crisan.gestion_aulas.web.dto.classroom.UpdateClassroomRequest;
import com.crisan.gestion_aulas.web.mapper.ClassroomWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
public class ClassroomController {
    private final ClassroomService classroomService;
    private final ClassroomWebMapper classroomWebMapper;

    @GetMapping
    public ResponseEntity<List<ClassroomResponse>> getAll(){
        List<ClassroomResponse> response = classroomService.getAll()
                .stream()
                .map(classroomWebMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomResponse> getById(@PathVariable Long id){

        Classroom classroom = classroomService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aula no encontrada"));
        return ResponseEntity.ok(classroomWebMapper.toResponse(classroom));
    }

    @PostMapping
    public ResponseEntity<ClassroomResponse> create(
            @Valid @RequestBody CreateClassroomRequest request){
        Classroom classroomCreated = classroomWebMapper.toDomain(request);
        Classroom classroomSaved = classroomService.create(classroomCreated);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(classroomWebMapper.toResponse(classroomSaved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassroomResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateClassroomRequest request){
        Classroom classroom = classroomWebMapper.toDomain(request);
        Classroom update = classroomService.update(id, classroom);

        return ResponseEntity.ok(classroomWebMapper.toResponse(update));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        classroomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
