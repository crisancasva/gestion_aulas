package com.crisan.gestion_aulas.web.dto.classroom;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateClassroomRequest {
    @NotBlank(message = "La descripción es obligatoria")
    private String name;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser mayor que cero")
    private Integer capacity;

    @NotBlank(message = "La ubicación es obligatoria")
    private String location;

    @NotNull(message = "El estado es obligatorio")
    private Long stateId;
}
