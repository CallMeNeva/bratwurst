// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.response.mapper;

@FunctionalInterface
public interface DTOMapper<D, T> {

    T map(D dataObject);
}
