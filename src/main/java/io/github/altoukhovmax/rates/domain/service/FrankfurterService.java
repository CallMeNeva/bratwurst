package io.github.altoukhovmax.rates.domain.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.altoukhovmax.rates.domain.entity.Currency;
import io.github.altoukhovmax.rates.domain.entity.ExchangeRate;
import io.github.altoukhovmax.rates.domain.service.dto.response.SpecificDateExchangeRatesDTO;
import io.github.altoukhovmax.rates.domain.service.dto.response.TimeSeriesExchangeRatesDTO;
import io.github.altoukhovmax.rates.domain.service.dto.response.conversion.AvailableCurrenciesDTOConverter;
import io.github.altoukhovmax.rates.domain.service.dto.response.conversion.SpecificDateExchangeRatesDTOConverter;
import io.github.altoukhovmax.rates.domain.service.dto.response.conversion.TimeSeriesExchangeRatesDTOConverter;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.URIScheme;
import org.apache.hc.core5.net.URIBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class FrankfurterService // TODO: Unit tests
        implements ExchangeRatesService, Closeable {

    private static final HttpHost PUBLIC_HOST = new HttpHost(URIScheme.HTTPS.getId(), "api.frankfurter.app");

    private final HttpHost host;
    private final CloseableHttpClient client;
    private final ObjectMapper parser;
    private final ModelMapper mapper;
    private final Logger logger;

    public FrankfurterService(@NotNull HttpHost host) {
        this.host = host;

//        RequestConfig requestConfig = RequestConfig.custom()
//                .setConnectTimeout(Timeout.ofSeconds(8))
//                .setResponseTimeout(Timeout.ofSeconds(8))
//                .build();
//        client = HttpClientBuilder.create()
//                .setDefaultRequestConfig(requestConfig)
//                .build();
        client = HttpClients.createMinimal();

        parser = new ObjectMapper();
        parser.registerModule(new JavaTimeModule());
        parser.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        mapper = new ModelMapper();
        mapper.addConverter(new SpecificDateExchangeRatesDTOConverter());
        mapper.addConverter(new TimeSeriesExchangeRatesDTOConverter());
        mapper.addConverter(new AvailableCurrenciesDTOConverter());

        logger = LoggerFactory.getLogger(getClass());
        logger.info("Initialized service");
    }

    public FrankfurterService() {
        this(PUBLIC_HOST);
    }

    @Override
    public Optional<List<ExchangeRate>> fetchExchangeRates(@NotNull String baseCurrencyCode,
                                                           @NotNull String... targetCurrencyCodes) {
        return fetchExchangeRatesData(
                new TypeReference<SpecificDateExchangeRatesDTO>(){},
                "latest",
                baseCurrencyCode,
                targetCurrencyCodes
        );
    }

    @Override
    public Optional<List<ExchangeRate>> fetchExchangeRates(@NotNull String baseCurrencyCode,
                                                           @NotNull LocalDate date,
                                                           @NotNull String... targetCurrencyCodes) {
        return fetchExchangeRatesData(
                new TypeReference<SpecificDateExchangeRatesDTO>(){},
                date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                baseCurrencyCode,
                targetCurrencyCodes
        );
    }

    @Override
    public Optional<List<ExchangeRate>> fetchExchangeRates(@NotNull String baseCurrencyCode,
                                                           @NotNull LocalDate startDate,
                                                           @Nullable LocalDate endDate,
                                                           @NotNull String... targetCurrencyCodes) {
        String endpoint = (startDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + "..");
        if (endDate != null) {
            endpoint += endDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        return fetchExchangeRatesData(
                new TypeReference<TimeSeriesExchangeRatesDTO>(){},
                endpoint,
                baseCurrencyCode,
                targetCurrencyCodes
        );
    }

    @Override
    public Optional<List<Currency>> fetchAvailableCurrencies() {
        return fetchData(
                "currencies",
                null,
                new TypeReference<HashMap<String, String>>(){},
                new TypeToken<List<Currency>>(){}.getType()
        );
    }

    @Override
    public void close() throws IOException {
        client.close();
    }

    ////////////////////////////
    //        Internal        //
    ////////////////////////////

    private <D, M> Optional<M> fetchData(@NotNull String endpoint,
                                         @Nullable Map<String, String> queryParameters,
                                         @NotNull TypeReference<D> dataTransferObjectType,
                                         @NotNull Type modelType) {
        URIBuilder resourceIdentifierBuilder = new URIBuilder()
                .setHttpHost(host)
                .appendPath(endpoint);
        if (queryParameters != null) {
            queryParameters.forEach(resourceIdentifierBuilder::addParameter);
        }
        try {
            URI resourceIdentifier = resourceIdentifierBuilder.build();
            String responseBody = Request.get(resourceIdentifier)
                    .execute(client)
                    .returnContent()
                    .asString(StandardCharsets.UTF_8);
            D dataTransferObject = parser.readValue(responseBody, dataTransferObjectType);
            M convertedData = mapper.map(dataTransferObject, modelType);
            logger.info("Fetched data from " + resourceIdentifier);
            return Optional.of(convertedData);
        } catch (URISyntaxException | IOException | MappingException e) {
            logger.error("Failed to fetch data: " + e.getMessage());
            return Optional.empty();
        }
    }

    private <D> Optional<List<ExchangeRate>> fetchExchangeRatesData(@NotNull TypeReference<D> dataTransferObjectType,
                                                                    @NotNull String endpoint,
                                                                    @NotNull String baseCurrencyCode,
                                                                    @NotNull String... targetCurrencyCodes) {
        Map<String, String> parameters = (targetCurrencyCodes.length > 0) ?
                Map.of("from", baseCurrencyCode, "to", String.join(",", targetCurrencyCodes)) :
                Map.of("from", baseCurrencyCode);
        return fetchData(endpoint, parameters, dataTransferObjectType, new TypeToken<List<ExchangeRate>>(){}.getType());
    }
}
