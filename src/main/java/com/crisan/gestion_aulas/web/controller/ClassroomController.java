package com.crisan.gestion_aulas.web.controller;

import com.crisan.gestion_aulas.domain.model.Classroom;
import com.crisan.gestion_aulas.domain.service.ClassroomService;
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

    @GetMapping
    public ResponseEntity<List<Classroom>> getAll(){
        return ResponseEntity.ok(classroomService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classroom> getById(@PathVariable Long id){
        Classroom classroom = classroomService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aula no encontrada"));
        return ResponseEntity.ok(classroom);
    }

    @PostMapping
    public ResponseEntity<Classroom> create(@RequestBody Classroom classroom){
        Classroom classroomCreated = classroomService.createClassroom(classroom);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(classroomCreated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        classroomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
