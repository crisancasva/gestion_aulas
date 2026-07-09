package com.crisan.gestion_aulas.persistence.mapper;

import com.crisan.gestion_aulas.domain.model.Role;
import com.crisan.gestion_aulas.domain.model.State;
import com.crisan.gestion_aulas.persistence.entity.RoleEntity;
import com.crisan.gestion_aulas.persistence.entity.StateEntity;
import jakarta.persistence.Entity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StateMapper {
    State toState(StateEntity stateEntity);
    StateEntity toStateEntity(State estate);
    List<State> toStates(List<StateEntity> estateEntities);
    List<StateEntity> toStateEntities(List<State> states);
}
