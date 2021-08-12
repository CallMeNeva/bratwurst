package io.github.altoukhovmax.rates.model;

import org.jetbrains.annotations.NotNull;

public record Currency(@NotNull String code,
                       @NotNull String displayName) {
}
