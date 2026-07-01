package com.crisan.gestion_aulas.persistence.mapper;

import com.crisan.gestion_aulas.domain.model.State;
import com.crisan.gestion_aulas.persistence.entity.StateEntity;
import jakarta.persistence.Entity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StateMapper {
    State toState(StateEntity stateEntity);
    StateEntity toStateEntity(Entity entity);
}
