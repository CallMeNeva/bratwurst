// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service;

import io.github.callmeneva.bratwurst.service.request.AbstractDataRequest;

@FunctionalInterface
public interface DataFetcher<T, R extends AbstractDataRequest> {

    T fetch(R request) throws DataFetchFailureException;
}
