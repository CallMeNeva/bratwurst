package io.github.altoukhovmax.frankfurterdesktop.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.altoukhovmax.frankfurterdesktop.model.ExchangeRate;
import io.github.altoukhovmax.frankfurterdesktop.service.request.*;
import io.github.altoukhovmax.frankfurterdesktop.service.response.SpecificDateExchangeRatesDTO;
import io.github.altoukhovmax.frankfurterdesktop.service.response.TimeSeriesExchangeRatesDTO;
import io.github.altoukhovmax.frankfurterdesktop.service.response.mapper.DTOMapper;
import io.github.altoukhovmax.frankfurterdesktop.service.response.mapper.DTOMappingException;
import io.github.altoukhovmax.frankfurterdesktop.service.response.mapper.SpecificDateExchangeRatesDTOMapper;
import io.github.altoukhovmax.frankfurterdesktop.service.response.mapper.TimeSeriesExchangeRatesDTOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Set;

public final class FrankfurterService implements ExchangeRatesService {

    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(8);

    private static final Logger LOGGER = LoggerFactory.getLogger(FrankfurterService.class);
    private static final ObjectMapper JSON_PARSER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private final String resourceIdentifierBase;
    private final HttpClient client;

    private FrankfurterService(String host, boolean connectionSecure) {
        this.resourceIdentifierBase = String.format("%s://%s", connectionSecure ? "https" : "http", host);
        this.client = HttpClient.newHttpClient();
    }

    public static FrankfurterService withPublicHost() {
        return new FrankfurterService("api.frankfurter.app", true);
    }

    /* TODO: Static factory method for user-provided hosts with validation */

    @Override
    public Set<ExchangeRate> serve(AbstractExchangeRatesRequest request) throws ServiceException {
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
        /* It is assumed that the URI base was validated at construction time */
        URI resourceIdentifier = URI.create(resourceIdentifierBase + '/' + dataRequest.toURIPath());
        HttpRequest request = HttpRequest.newBuilder(resourceIdentifier)
                .GET()
                .timeout(TIMEOUT_DURATION)
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            D dataObj = JSON_PARSER.readValue(response.body(), dataObjClass);
            T data = dataObjMapper.map(dataObj);
            LOGGER.info("Fetched: " + resourceIdentifier);
            return data;
        } catch (IOException | InterruptedException | SecurityException | DTOMappingException e) {
            LOGGER.error("Failed to fetch " + resourceIdentifier + ", reason: " + e.getMessage());
            throw new ServiceException(e);
        }
    }
}