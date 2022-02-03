// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

import io.github.callmeneva.bratwurst.util.Arguments;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public interface DataRequest {

    String getEndpointName();

    default Map<String, String> getParameters() {
        return Map.of();
    }

    default URI toURIWithHost(HttpHost host) throws URISyntaxException {
        Arguments.checkNull(host, "host");

        URIBuilder builder = new URIBuilder()
                .setHttpHost(host)
                .appendPath(getEndpointName());

        // No fitting overload in builder
        Map<String, String> parameters = getParameters();
        parameters.forEach(builder::addParameter);

        return builder.build();
    }
}
