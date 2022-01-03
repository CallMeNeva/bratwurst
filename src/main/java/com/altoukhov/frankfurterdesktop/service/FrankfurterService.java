/*
 * Copyright 2021, 2022 Maxim Altoukhov
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

package com.altoukhov.frankfurterdesktop.service;

import com.altoukhov.frankfurterdesktop.model.Currency;
import com.altoukhov.frankfurterdesktop.model.Exchange;
import com.altoukhov.frankfurterdesktop.service.request.AbstractDataRequest;
import com.altoukhov.frankfurterdesktop.service.request.AbstractExchangeDataRequest;
import com.altoukhov.frankfurterdesktop.service.request.CurrencyDataRequest;
import com.altoukhov.frankfurterdesktop.service.request.HistoricalExchangeDataRequest;
import com.altoukhov.frankfurterdesktop.service.request.LatestExchangeDataRequest;
import com.altoukhov.frankfurterdesktop.service.request.TimeSeriesExchangeDataRequest;
import com.altoukhov.frankfurterdesktop.service.response.CurrenciesDTO;
import com.altoukhov.frankfurterdesktop.service.response.SpecificDateExchangesDTO;
import com.altoukhov.frankfurterdesktop.service.response.TimeSeriesExchangesDTO;
import com.altoukhov.frankfurterdesktop.service.response.mapper.CurrenciesDTOMapper;
import com.altoukhov.frankfurterdesktop.service.response.mapper.DTOMapper;
import com.altoukhov.frankfurterdesktop.service.response.mapper.DTOMappingException;
import com.altoukhov.frankfurterdesktop.service.response.mapper.SpecificDateExchangesDTOMapper;
import com.altoukhov.frankfurterdesktop.service.response.mapper.TimeSeriesExchangesDTOMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.URIScheme;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Set;

public final class FrankfurterService {

    public static final HttpHost PUBLIC_HOST = new HttpHost(URIScheme.HTTPS.getId(), "api.frankfurter.app");

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

    public FrankfurterService() {
        host = PUBLIC_HOST;
    }

    public HttpHost getHost() {
        return host;
    }

    public void setHost(HttpHost host) {
        this.host = Objects.requireNonNull(host, "Provided HTTP host is null");
    }

    /* NOTE: Global registry should be updated at least once prior to calling this */
    public Set<Exchange> serveExchanges(AbstractExchangeDataRequest request) throws ServiceException {
        return switch (request) {
            /* FIXME: Merge first two cases when (if) it becomes possible */
            case LatestExchangeDataRequest r -> serve(request, SpecificDateExchangesDTO.class, SpecificDateExchangesDTOMapper.INSTANCE);
            case HistoricalExchangeDataRequest r -> serve(request, SpecificDateExchangesDTO.class, SpecificDateExchangesDTOMapper.INSTANCE);
            case TimeSeriesExchangeDataRequest r -> serve(request, TimeSeriesExchangesDTO.class, TimeSeriesExchangesDTOMapper.INSTANCE);
            case default -> throw new IllegalArgumentException("Unknown request type");
            case null -> throw new IllegalArgumentException("Provided request is null");
        };
    }

    public Set<Currency> serveCurrencies() throws ServiceException {
        return serve(CurrencyDataRequest.INSTANCE, CurrenciesDTO.class, CurrenciesDTOMapper.INSTANCE);
    }

    private <D, T> T serve(AbstractDataRequest dataRequest, Class<D> dataClass, DTOMapper<D, T> dataMapper) throws ServiceException {
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
