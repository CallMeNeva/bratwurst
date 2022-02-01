// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.service;

import com.altoukhov.bratwurst.model.Currency;
import com.altoukhov.bratwurst.model.CurrencyRepository;
import com.altoukhov.bratwurst.model.Exchange;
import com.altoukhov.bratwurst.service.request.AbstractExchangeDataRequest;
import com.altoukhov.bratwurst.service.request.CurrencyDataRequest;
import com.altoukhov.bratwurst.service.request.DataRequest;
import com.altoukhov.bratwurst.service.request.HistoricalExchangeDataRequest;
import com.altoukhov.bratwurst.service.request.LatestExchangeDataRequest;
import com.altoukhov.bratwurst.service.request.TimeSeriesExchangeDataRequest;
import com.altoukhov.bratwurst.service.response.CurrenciesDTO;
import com.altoukhov.bratwurst.service.response.SpecificDateExchangesDTO;
import com.altoukhov.bratwurst.service.response.TimeSeriesExchangesDTO;
import com.altoukhov.bratwurst.service.response.mapper.CurrenciesDTOMapper;
import com.altoukhov.bratwurst.service.response.mapper.DTOMapper;
import com.altoukhov.bratwurst.service.response.mapper.DTOMappingException;
import com.altoukhov.bratwurst.service.response.mapper.SpecificDateExchangesDTOMapper;
import com.altoukhov.bratwurst.service.response.mapper.TimeSeriesExchangesDTOMapper;
import com.altoukhov.bratwurst.util.Arguments;
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
import java.util.Set;

public final class FrankfurterService {

    private static final HttpHost DEFAULT_HOST = new HttpHost(URIScheme.HTTPS.getId(), "api.frankfurter.app");
    private static final Timeout CONNECTION_TIMEOUT = Timeout.ofSeconds(8);
    private static final Timeout RESPONSE_TIMEOUT = Timeout.ofSeconds(8);

    private static final Logger LOG = LoggerFactory.getLogger(FrankfurterService.class);
    private static final ObjectMapper JSON_PARSER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private final CurrencyRepository currencyRepository;
    private final SpecificDateExchangesDTOMapper specificDateMapper;
    private final TimeSeriesExchangesDTOMapper timeSeriesMapper;
    private HttpHost host;

    public static FrankfurterService withCustomHost(HttpHost host) {
        Arguments.checkNull(host, "host");
        return new FrankfurterService(host);
    }

    public static FrankfurterService withDefaultHost() {
        return new FrankfurterService(DEFAULT_HOST);
    }

    private FrankfurterService(HttpHost host) {
        currencyRepository = new CurrencyRepository();
        specificDateMapper = new SpecificDateExchangesDTOMapper(currencyRepository);
        timeSeriesMapper = new TimeSeriesExchangesDTOMapper(currencyRepository);
        this.host = host;
    }

    public CurrencyRepository getCurrencyRepository() {
        return currencyRepository;
    }

    public HttpHost getHost() {
        return host;
    }

    public void setCustomHost(HttpHost host) {
        this.host = Arguments.checkNull(host, "host");
    }

    public void setDefaultHost() {
        host = DEFAULT_HOST;
    }

    public Set<Exchange> fetchExchanges(AbstractExchangeDataRequest request) throws ServiceException {
        Arguments.checkNull(request, "request"); // Used over a switch null case for the generic exception message
        return switch (request) {
            // TODO: Merge first two cases when (if) it becomes possible
            case LatestExchangeDataRequest ignored -> fetch(request, SpecificDateExchangesDTO.class, specificDateMapper);
            case HistoricalExchangeDataRequest ignored -> fetch(request, SpecificDateExchangesDTO.class, specificDateMapper);
            case TimeSeriesExchangeDataRequest ignored -> fetch(request, TimeSeriesExchangesDTO.class, timeSeriesMapper);
            case default -> throw new IllegalArgumentException("Unknown request type");
        };
    }

    public void updateCurrencyRepository() throws ServiceException {
        Set<Currency> currencies = fetch(CurrencyDataRequest.INSTANCE, CurrenciesDTO.class, CurrenciesDTOMapper.INSTANCE);
        currencyRepository.update(currencies);
        LOG.info("Updated currency repository (" + currencies.size() + " items total)");
    }

    private <D, T> T fetch(DataRequest dataRequest, Class<D> dataClass, DTOMapper<D, T> dataMapper) throws ServiceException {
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

            LOG.info("Fetched: " + resourceIdentifier);
            return mappedData;

        } catch (URISyntaxException | IOException | DTOMappingException e) {
            LOG.error("Failed to fetch resource (cause: " + e.getCause() + ')');
            throw new ServiceException(e);
        }
    }
}
