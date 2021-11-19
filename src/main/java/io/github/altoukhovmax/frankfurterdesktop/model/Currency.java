package io.github.altoukhovmax.frankfurterdesktop.model;

import org.jetbrains.annotations.NotNull;

/*
 * Models a currency. Each one is represented by:
 *     - an ISO-4217 code, e.g. "USD";
 *     - a display name, e.g. "United Stated Dollar".
 * None of the values may be null. This class is immutable.
 */
public record Currency(@NotNull String code, @NotNull String displayName) {
}
