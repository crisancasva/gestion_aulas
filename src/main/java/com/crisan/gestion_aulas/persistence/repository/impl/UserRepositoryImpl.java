package com.crisan.gestion_aulas.persistence.repository.impl;

import com.crisan.gestion_aulas.domain.model.User;
import com.crisan.gestion_aulas.domain.repository.UserRepository;
import com.crisan.gestion_aulas.persistence.mapper.UserMapper;
import com.crisan.gestion_aulas.persistence.repository.crud.UserCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserCrudRepository userCrudRepository;
    private final UserMapper userMapper;

    @Override
    public List<User> getAll() {
        return userMapper.toUsers(userCrudRepository.findAll());
    }

    @Override
    public Optional<User> getById(Long userId) {
        return userCrudRepository.findById(userId).map(userMapper::toUser);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userCrudRepository.findByEmail(email).map(userMapper::toUser);
    }

    @Override
    public User save(User user) {
        return userMapper.toUser(userCrudRepository.save(userMapper.toEntity(user)));
    }

    @Override
    public void delete(Long userId) {
        userCrudRepository.deleteById(userId);
    }
}
