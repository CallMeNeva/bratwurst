// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

import org.apache.commons.lang3.Validate;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.URIScheme;
import org.apache.hc.core5.net.Ports;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public abstract class AbstractDataRequest {

    private URIScheme scheme;
    private String hostname;
    private int port;

    protected AbstractDataRequest(URIScheme scheme, String hostname, int port) {
        setScheme(scheme);
        setHostname(hostname);
        setPort(port);
    }

    public final URIScheme getScheme() {
        return scheme;
    }

    public final void setScheme(URIScheme scheme) {
        this.scheme = Validate.notNull(scheme);
    }

    public final String getHostname() {
        return hostname;
    }

    public final void setHostname(String hostname) {
        this.hostname = Validate.notBlank(hostname);
    }

    public final int getPort() {
        return port;
    }

    public final void setPort(int port) {
        this.port = Ports.checkWithDefault(port);
    }

    public final URI toURI() throws URISyntaxException {
        HttpHost host = new HttpHost(scheme.getId(), hostname, port);

        URIBuilder builder = new URIBuilder()
                .setHttpHost(host)
                .appendPath(getEndpointName());

        // No fitting overload in builder
        Map<String, String> parameters = getParameters();
        parameters.forEach(builder::addParameter);

        return builder.build();
    }

    protected abstract String getEndpointName();

    // Non-final for inheritors
    protected Map<String, String> getParameters() {
        return Map.of();
    }
}
