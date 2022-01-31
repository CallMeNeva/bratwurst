// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.service.request;

import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

/**
 * Base class for data requests to a providing service.
 */
public abstract class AbstractDataRequest {

    /**
     * Returns the name of the endpoint for which this request is intended (i.e. for <code>localhost:8080/currencies</code> this would be
     * <code>currencies</code>) or null if request is intended for the root endpoint.
     *
     * @return a string representing the endpoint's name (may be null)
     */
    protected abstract String getEndpointName();

    /**
     * Returns this request's query parameters.
     *
     * @return a map containing parameter name and value mappings (not null)
     * @implNote Default implementation returns an empty map for cases where request does not include any parameters.
     */
    protected Map<String, String> getParameters() {
        return Map.of();
    }

    /**
     * Returns this request's URI representation.
     *
     * @param host the HTTP host to be used when constructing the URI
     * @return a URI representing this request (not null)
     * @throws NullPointerException if host is null
     * @throws URISyntaxException if an error occurred while constructing the URI
     */
    public final URI toURIWithHost(HttpHost host) throws URISyntaxException {
        Objects.requireNonNull(host, "Provided HTTP host is null");

        URIBuilder builder = new URIBuilder()
                .setHttpHost(host)
                .appendPath(getEndpointName());
        getParameters().forEach(builder::addParameter); /* If only there was an overload for a map :( */

        return builder.build();
    }
}
