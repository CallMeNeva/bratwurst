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
import io.github.altoukhovmax.frankfurterdesktop.model.Currency;
import io.github.altoukhovmax.frankfurterdesktop.model.CurrencyRegistry;
import io.github.altoukhovmax.frankfurterdesktop.model.ExchangeRate;
import io.github.altoukhovmax.frankfurterdesktop.service.request.AbstractDataRequest;
import io.github.altoukhovmax.frankfurterdesktop.service.request.AbstractExchangeRatesRequest;
import io.github.altoukhovmax.frankfurterdesktop.service.request.CurrenciesRequest;
import io.github.altoukhovmax.frankfurterdesktop.service.request.HistoricalExchangeRatesRequest;
import io.github.altoukhovmax.frankfurterdesktop.service.request.LatestExchangeRatesRequest;
import io.github.altoukhovmax.frankfurterdesktop.service.request.TimeSeriesExchangeRatesRequest;
import io.github.altoukhovmax.frankfurterdesktop.service.response.CurrenciesDTO;
import io.github.altoukhovmax.frankfurterdesktop.service.response.SpecificDateExchangeRatesDTO;
import io.github.altoukhovmax.frankfurterdesktop.service.response.TimeSeriesExchangeRatesDTO;
import io.github.altoukhovmax.frankfurterdesktop.service.response.mapper.CurrenciesDTOMapper;
import io.github.altoukhovmax.frankfurterdesktop.service.response.mapper.DTOMapper;
import io.github.altoukhovmax.frankfurterdesktop.service.response.mapper.DTOMappingException;
import io.github.altoukhovmax.frankfurterdesktop.service.response.mapper.SpecificDateExchangeRatesDTOMapper;
import io.github.altoukhovmax.frankfurterdesktop.service.response.mapper.TimeSeriesExchangeRatesDTOMapper;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Set;

public final class FrankfurterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FrankfurterService.class);
    private static final ObjectMapper JSON_PARSER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private static final Timeout CONNECTION_TIMEOUT = Timeout.ofSeconds(8);
    private static final Timeout RESPONSE_TIMEOUT = Timeout.ofSeconds(8);

    private HttpHost host;

    public FrankfurterService(HttpHost host) {
        setHost(host);
    }

    public HttpHost getHost() {
        return host;
    }

    public void setHost(HttpHost host) {
        this.host = Objects.requireNonNull(host, "Provided HTTP host is null");
    }

    /* Registry should be updated at least once prior to calling this */
    public Set<ExchangeRate> fetchExchangeRates(AbstractExchangeRatesRequest request) throws ServiceException {
        return switch (request) {
            case LatestExchangeRatesRequest ignored ->
                    fetchData(request, SpecificDateExchangeRatesDTO.class, SpecificDateExchangeRatesDTOMapper.INSTANCE);
            case HistoricalExchangeRatesRequest ignored -> /* TODO: Merge this with case above when (if) it becomes possible */
                    fetchData(request, SpecificDateExchangeRatesDTO.class, SpecificDateExchangeRatesDTOMapper.INSTANCE);
            case TimeSeriesExchangeRatesRequest ignored ->
                    fetchData(request, TimeSeriesExchangeRatesDTO.class, TimeSeriesExchangeRatesDTOMapper.INSTANCE);
            case default -> throw new IllegalArgumentException("Unknown request type");
            case null -> throw new IllegalArgumentException("Request is null");
        };
    }

    public void updateCurrencyRegistry() {
        Set<Currency> currencies = fetchData(CurrenciesRequest.getInstance(), CurrenciesDTO.class, CurrenciesDTOMapper.INSTANCE);
        CurrencyRegistry.INSTANCE.overwrite(currencies);
    }

    private <D, T> T fetchData(AbstractDataRequest dataRequest, Class<D> dataClass, DTOMapper<D, T> dataMapper) throws ServiceException {
        try {
            URI resourceIdentifier = dataRequest.toURIWithHost(host);
            String responseContent = Request.get(resourceIdentifier)
                    .connectTimeout(CONNECTION_TIMEOUT)
                    .responseTimeout(RESPONSE_TIMEOUT)
                    .execute()
                    .returnContent()
                    .asString();

            D dataObject = JSON_PARSER.readValue(responseContent, dataClass);
            T mappedData = dataMapper.map(dataObject);

            LOGGER.info("Fetched: " + resourceIdentifier);
            return mappedData;

        } catch (URISyntaxException | IOException | DTOMappingException e) {
            LOGGER.error("Failed to fetch resource (cause: " + e.getCause() + ')');
            throw new ServiceException(e);
        }
    }
}
