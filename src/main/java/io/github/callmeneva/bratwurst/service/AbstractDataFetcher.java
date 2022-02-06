// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.callmeneva.bratwurst.service.request.AbstractDataRequest;
import io.github.callmeneva.bratwurst.service.response.mapper.DTOMapper;
import org.apache.commons.lang3.Validate;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public abstract class AbstractDataFetcher<T, R extends AbstractDataRequest> implements DataFetcher<T, R> {

    private static final Timeout CONNECTION_TIMEOUT = Timeout.ofSeconds(8);
    private static final Timeout RESPONSE_TIMEOUT = Timeout.ofSeconds(8);
    private static final ObjectMapper JSON_PARSER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private final Logger logger;

    protected AbstractDataFetcher() {
        logger = LoggerFactory.getLogger(getClass());
    }

    protected final <D> T fetch(R dataRequest, Class<D> dataClass, DTOMapper<D, T> mapper) throws DataFetchFailureException {
        // Null-check once here instead of everytime in inheritors
        Validate.notNull(dataRequest);
        try {
            URI location = dataRequest.toURI();
            String responseContent = Request.get(location)
                    .connectTimeout(CONNECTION_TIMEOUT)
                    .responseTimeout(RESPONSE_TIMEOUT)
                    .execute()
                    .returnContent()
                    .asString();

            D dataObject = JSON_PARSER.readValue(responseContent, dataClass);
            T mappedData = mapper.map(dataObject);

            logger.info("Fetched: " + location);
            return mappedData;
        } catch (URISyntaxException | IOException e) {
            logger.error("Failed to fetch (cause: " + e.getCause() + ')');
            throw new DataFetchFailureException(e);
        }
    }
}
