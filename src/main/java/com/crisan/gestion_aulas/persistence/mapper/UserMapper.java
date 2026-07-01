package com.crisan.gestion_aulas.persistence.mapper;

import com.crisan.gestion_aulas.domain.model.User;
import com.crisan.gestion_aulas.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoleMapper.class, StateMapper.class})
public interface UserMapper {
    User toUser (UserEntity entity);
    UserEntity toEntity(User user);
}
