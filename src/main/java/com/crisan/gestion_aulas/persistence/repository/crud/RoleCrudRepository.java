package com.crisan.gestion_aulas.persistence.repository.crud;

import com.crisan.gestion_aulas.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleCrudRepository extends JpaRepository<RoleEntity, Long> {

}
