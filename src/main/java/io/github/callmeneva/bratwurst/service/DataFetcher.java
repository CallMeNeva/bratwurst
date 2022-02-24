// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.callmeneva.bratwurst.model.Currency;
import io.github.callmeneva.bratwurst.model.Exchange;
import io.github.callmeneva.bratwurst.service.request.CurrencyDataRequest;
import io.github.callmeneva.bratwurst.service.request.DataRequest;
import io.github.callmeneva.bratwurst.service.request.HistoricalExchangeDataRequest;
import io.github.callmeneva.bratwurst.service.request.LatestExchangeDataRequest;
import io.github.callmeneva.bratwurst.service.request.TimeSeriesExchangeDataRequest;
import io.github.callmeneva.bratwurst.service.response.CurrencyDTO;
import io.github.callmeneva.bratwurst.service.response.SpecificDateExchangeDTO;
import io.github.callmeneva.bratwurst.service.response.TimeSeriesExchangeDTO;
import io.github.callmeneva.bratwurst.service.response.mapper.CurrencyDTOMapper;
import io.github.callmeneva.bratwurst.service.response.mapper.DTOMapper;
import io.github.callmeneva.bratwurst.service.response.mapper.SpecificDateExchangeDTOMapper;
import io.github.callmeneva.bratwurst.service.response.mapper.TimeSeriesExchangeDTOMapper;

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

public final class DataFetcher<T, R extends DataRequest, D> {

    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(30);
    private static final Duration RESPONSE_TIMEOUT = Duration.ofSeconds(15);
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(CONNECT_TIMEOUT)
            .version(HttpClient.Version.HTTP_1_1)
            .build();
    private static final ObjectMapper JSON_PARSER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private final Class<D> dataClass;
    private final DTOMapper<D, T> mapper;
    private final Logger logger;

    private boolean connectionSecure;
    private String host;

    public DataFetcher(Class<D> dataClass, DTOMapper<D, T> mapper, boolean connectionSecure, String host) {
        this.dataClass = Objects.requireNonNull(dataClass, "Fetcher data class must not be null");
        this.mapper = Objects.requireNonNull(mapper, "Fetcher data mapper must not be null");
        logger = Logger.getLogger(getClass().getName());

        setConnectionSecure(connectionSecure);
        setHost(host);
    }

    public DataFetcher(Class<D> dataClass, DTOMapper<D, T> mapper, String host) {
        this(dataClass, mapper, true, host);
    }

    public static DataFetcher<Set<Currency>, CurrencyDataRequest, CurrencyDTO> ofCurrencies(String host) {
        return new DataFetcher<>(CurrencyDTO.class, new CurrencyDTOMapper(), host);
    }

    public static DataFetcher<Set<Exchange>, LatestExchangeDataRequest, SpecificDateExchangeDTO> ofLatestExchanges(String host) {
        return new DataFetcher<>(SpecificDateExchangeDTO.class, new SpecificDateExchangeDTOMapper(), host);
    }

    public static DataFetcher<Set<Exchange>, HistoricalExchangeDataRequest, SpecificDateExchangeDTO> ofHistoricalExchanges(String host) {
        return new DataFetcher<>(SpecificDateExchangeDTO.class, new SpecificDateExchangeDTOMapper(), host);
    }

    public static DataFetcher<Set<Exchange>, TimeSeriesExchangeDataRequest, TimeSeriesExchangeDTO> ofTimeSeriesExchanges(String host) {
        return new DataFetcher<>(TimeSeriesExchangeDTO.class, new TimeSeriesExchangeDTOMapper(), host);
    }

    public boolean isConnectionSecure() {
        return connectionSecure;
    }

    public void setConnectionSecure(boolean connectionSecure) {
        this.connectionSecure = connectionSecure;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public T fetch(R dataRequest) throws DataFetchFailureException {
        Objects.requireNonNull(dataRequest, "Data request must not be null");

        StringBuilder dataSourceBuilder = new StringBuilder()
                .append(connectionSecure ? "https" : "http")
                .append("://")
                .append(host)
                .append(dataRequest.toPathForm());

        try {
            // Since a lot of this URI's subcomponents (including the host) are very weakly validated (if at all), using the main URI constructor
            // specifically is very important as the validation provided with it is practically the only time when we actually filter out bad input.
            URI dataSource = new URI(dataSourceBuilder.toString());
            HttpRequest request = HttpRequest.newBuilder(dataSource)
                    .GET()
                    .timeout(RESPONSE_TIMEOUT)
                    .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            D dataObject = JSON_PARSER.readValue(response.body(), dataClass);
            T mappedData = mapper.map(dataObject);

            logger.info("Fetched from: " + dataSource);
            return mappedData;

        } catch (URISyntaxException | IllegalArgumentException | IOException | InterruptedException e) {
            // Update to the comment above: "empirical evidence" suggests that URI's string constructor doesn't actually validate the host string
            // strictly enough for the request, i.e. an "https://Hello!" would pass. However, HttpRequest itself seems to help with that and throws
            // an IAE in case of a bad URI, which is why it's caught here.
            logger.warning("Failed to fetch (exception: " + e + ')');
            throw new DataFetchFailureException(e);
        }
    }
}
