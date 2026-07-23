package com.crisan.gestion_aulas.common.util;

import java.util.Optional;

/**
 * Helpers for unwrapping optional lookups of domain entities.
 */
public final class Entities {

    private Entities() {
    }

    /**
     * Returns the value held by {@code optional} or throws an
     * {@link IllegalArgumentException} with {@code notFoundMessage} when empty.
     */
    public static <T> T getOrThrow(Optional<T> optional, String notFoundMessage) {
        return optional.orElseThrow(() -> new IllegalArgumentException(notFoundMessage));
    }
}
