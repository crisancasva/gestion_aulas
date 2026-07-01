package com.crisan.gestion_aulas.persistence.mapper;

import com.crisan.gestion_aulas.domain.model.Role;
import com.crisan.gestion_aulas.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleEntity roleEntity);
    RoleEntity toRoleEntity(Role role);
}
