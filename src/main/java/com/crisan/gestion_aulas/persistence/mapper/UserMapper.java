package com.crisan.gestion_aulas.persistence.mapper;

import com.crisan.gestion_aulas.domain.model.User;
import com.crisan.gestion_aulas.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoleMapper.class, StateMapper.class})
public interface UserMapper {
    User toUser (UserEntity entity);
    UserEntity toEntity(User user);
    List<User> toUsers(List<UserEntity> entities);
    List<UserEntity> toUserEntities(List<User> users);
}
