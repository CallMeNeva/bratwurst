// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

import java.util.Map;
import java.util.stream.Collectors;

public interface DataRequest {

    String getEndpointName();

    default Map<String, String> getParameters() {
        return Map.of();
    }

    default String toPathForm() {
        StringBuilder builder = new StringBuilder()
                .append('/')
                .append(getEndpointName());

        Map<String, String> parameters = getParameters();
        if (!parameters.isEmpty()) {
            String serializedParameters = parameters.entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + '=' + entry.getValue())
                    .collect(Collectors.joining("&"));

            builder.append('?');
            builder.append(serializedParameters);
        }

        return builder.toString();
    }
}
