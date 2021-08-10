package io.github.altoukhovmax.rates.domain.entity;

import org.jetbrains.annotations.NotNull;

public record Currency(@NotNull String code,
                       @NotNull String displayName) {
}
