package com.crisan.gestion_aulas.persistence.repository.crud;

import com.crisan.gestion_aulas.persistence.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateCrudRepository extends JpaRepository<StateEntity, Long> {
}
