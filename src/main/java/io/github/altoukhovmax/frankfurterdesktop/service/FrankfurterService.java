/*
 * Copyright 2021 Maxim Altoukhov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.altoukhovmax.frankfurterdesktop.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.altoukhovmax.frankfurterdesktop.model.ExchangeRate;
import io.github.altoukhovmax.frankfurterdesktop.service.request.AbstractExchangeRatesRequest;
import io.github.altoukhovmax.frankfurterdesktop.service.request.DataRequest;
import io.github.altoukhovmax.frankfurterdesktop.service.request.HistoricalExchangeRatesRequest;
import io.github.altoukhovmax.frankfurterdesktop.service.request.LatestExchangeRatesRequest;
import io.github.altoukhovmax.frankfurterdesktop.service.request.TimeSeriesExchangeRatesRequest;
import io.github.altoukhovmax.frankfurterdesktop.service.response.SpecificDateExchangeRatesDTO;
import io.github.altoukhovmax.frankfurterdesktop.service.response.TimeSeriesExchangeRatesDTO;
import io.github.altoukhovmax.frankfurterdesktop.service.response.mapper.DTOMapper;
import io.github.altoukhovmax.frankfurterdesktop.service.response.mapper.DTOMappingException;
import io.github.altoukhovmax.frankfurterdesktop.service.response.mapper.SpecificDateExchangeRatesDTOMapper;
import io.github.altoukhovmax.frankfurterdesktop.service.response.mapper.TimeSeriesExchangeRatesDTOMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

public final class FrankfurterService implements ExchangeRatesService {

    private static final Logger LOGGER = Logger.getLogger(FrankfurterService.class.getName());
    private static final ObjectMapper JSON_PARSER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(8);

    private final String resourceIdentifierBase;
    private final HttpClient client;

    private FrankfurterService(String hostname, boolean enableSecureConnection) {
        this.resourceIdentifierBase = String.format("%s://%s/", enableSecureConnection ? "https" : "http", hostname);
        this.client = HttpClient.newHttpClient();
    }

    public static FrankfurterService withPublicHost() {
        return new FrankfurterService("api.frankfurter.app", true);
    }

    public static FrankfurterService withCustomHost(String hostname, boolean enableSecureConnection) {
        /* Hostname is validated at request time instead of construction time */
        return new FrankfurterService(Objects.requireNonNull(hostname), enableSecureConnection);
    }

    @Override
    public Set<ExchangeRate> serve(AbstractExchangeRatesRequest request) throws ServiceException {
        /*
         * Since the currency dataset is defined statically with no way to update it at runtime, a DTOMappingException
         * will occur in the (unlikely) event that it is not up-to-date.
         */
        return switch (request) {
            case LatestExchangeRatesRequest ignored ->
                    fetchData(request, SpecificDateExchangeRatesDTO.class, SpecificDateExchangeRatesDTOMapper.INSTANCE);
            /* TODO: Merge this with case above when (if) it becomes possible */
            case HistoricalExchangeRatesRequest ignored ->
                    fetchData(request, SpecificDateExchangeRatesDTO.class, SpecificDateExchangeRatesDTOMapper.INSTANCE);
            case TimeSeriesExchangeRatesRequest ignored ->
                    fetchData(request, TimeSeriesExchangeRatesDTO.class, TimeSeriesExchangeRatesDTOMapper.INSTANCE);
            case null -> throw new IllegalArgumentException("Request is null");
        };
    }

    private <D, T> T fetchData(DataRequest dataRequest,
                               Class<D> dataObjClass,
                               DTOMapper<D, T> dataObjMapper) throws ServiceException {
        try {
            URI resourceIdentifier = new URI(resourceIdentifierBase + dataRequest.toURIPath());
            HttpRequest request = HttpRequest.newBuilder(resourceIdentifier)
                    .GET()
                    .timeout(TIMEOUT_DURATION)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            D dataObj = JSON_PARSER.readValue(response.body(), dataObjClass);
            T data = dataObjMapper.map(dataObj);
            LOGGER.info("Fetched: " + resourceIdentifier);
            return data;
        } catch (URISyntaxException | IOException | InterruptedException | SecurityException | DTOMappingException e) {
            LOGGER.warning("Failed to fetch data (" + e.getCause() + ')');
            throw new ServiceException(e);
        }
    }
}
