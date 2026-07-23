package com.crisan.gestion_aulas.common.util;

import java.util.List;
import java.util.function.Function;

/**
 * Utilities for mapping collections between layers.
 */
public final class Mappers {

    private Mappers() {
    }

    /**
     * Maps every element of {@code source} with {@code mapper} into a new (unmodifiable) list.
     */
    public static <S, T> List<T> mapList(List<S> source, Function<? super S, T> mapper) {
        return source.stream()
                .map(mapper)
                .toList();
    }
}
