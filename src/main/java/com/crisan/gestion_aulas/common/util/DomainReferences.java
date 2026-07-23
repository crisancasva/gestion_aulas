package com.crisan.gestion_aulas.common.util;

import com.crisan.gestion_aulas.domain.model.Classroom;
import com.crisan.gestion_aulas.domain.model.Role;
import com.crisan.gestion_aulas.domain.model.State;
import com.crisan.gestion_aulas.domain.model.User;

/**
 * Factories for lightweight, id-only domain references.
 *
 * <p>Web request mappers only carry the identifier of related aggregates, so they
 * build stub domain objects populated solely with that id. These helpers centralize
 * that construction.
 */
public final class DomainReferences {

    private DomainReferences() {
    }

    public static State state(Long stateId) {
        return State.builder()
                .stateId(stateId)
                .build();
    }

    public static Role role(Long roleId) {
        return Role.builder()
                .roleId(roleId)
                .build();
    }

    public static Classroom classroom(Long classroomId) {
        return Classroom.builder()
                .classroomId(classroomId)
                .build();
    }

    public static User user(Long userId) {
        return User.builder()
                .userId(userId)
                .build();
    }
}
