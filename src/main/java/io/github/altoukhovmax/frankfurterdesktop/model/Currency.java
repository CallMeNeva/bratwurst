package io.github.altoukhovmax.frankfurterdesktop.model;

import org.jetbrains.annotations.NotNull;

public record Currency(@NotNull String code,
                       @NotNull String displayName) {
}
