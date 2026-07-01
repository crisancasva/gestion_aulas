package com.crisan.gestion_aulas.persistence.mapper;

import com.crisan.gestion_aulas.domain.model.Classroom;
import com.crisan.gestion_aulas.domain.model.Role;
import com.crisan.gestion_aulas.persistence.entity.ClassroomEntity;
import com.crisan.gestion_aulas.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleEntity roleEntity);
    RoleEntity toRoleEntity(Role role);
    List<Role> toRoles(List<RoleEntity> roleEntities);
    List<RoleEntity> toRoleEntities(List<Role> roles);
}
