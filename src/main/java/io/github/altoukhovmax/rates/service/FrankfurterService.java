package io.github.altoukhovmax.rates.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.altoukhovmax.rates.model.Currency;
import io.github.altoukhovmax.rates.model.ExchangeRate;
import io.github.altoukhovmax.rates.service.dto.response.SpecificDateExchangeRatesDTO;
import io.github.altoukhovmax.rates.service.dto.response.TimeSeriesExchangeRatesDTO;
import io.github.altoukhovmax.rates.service.dto.response.conversion.AvailableCurrenciesDTOConverter;
import io.github.altoukhovmax.rates.service.dto.response.conversion.SpecificDateExchangeRatesDTOConverter;
import io.github.altoukhovmax.rates.service.dto.response.conversion.TimeSeriesExchangeRatesDTOConverter;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.URIScheme;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.util.Timeout;
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

public final class FrankfurterService implements ExchangeRatesService, Closeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(FrankfurterService.class);

    private final HttpHost host;
    private final CloseableHttpClient client;
    private final ObjectMapper parser;
    private final ModelMapper mapper;

    public FrankfurterService() {
        this.host = new HttpHost(URIScheme.HTTPS.getId(), "api.frankfurter.app");

        client = HttpClients.createMinimal();

        parser = new ObjectMapper();
        parser.registerModule(new JavaTimeModule());
        parser.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        mapper = new ModelMapper();
        mapper.addConverter(new SpecificDateExchangeRatesDTOConverter());
        mapper.addConverter(new TimeSeriesExchangeRatesDTOConverter());
        mapper.addConverter(new AvailableCurrenciesDTOConverter());

        LOGGER.info("Initialized service");
    }

    @Override
    public Optional<List<ExchangeRate>> fetchExchangeRates(@NotNull final String baseCurrencyCode,
                                                           @NotNull final String... targetCurrencyCodes) {
        return fetchExchangeRatesData(
                new TypeReference<SpecificDateExchangeRatesDTO>(){},
                "latest",
                baseCurrencyCode,
                targetCurrencyCodes
        );
    }

    @Override
    public Optional<List<ExchangeRate>> fetchExchangeRates(@NotNull final String baseCurrencyCode,
                                                           @NotNull final LocalDate date,
                                                           @NotNull final String... targetCurrencyCodes) {
        return fetchExchangeRatesData(
                new TypeReference<SpecificDateExchangeRatesDTO>(){},
                date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                baseCurrencyCode,
                targetCurrencyCodes
        );
    }

    @Override
    public Optional<List<ExchangeRate>> fetchExchangeRates(@NotNull final String baseCurrencyCode,
                                                           @NotNull final LocalDate startDate,
                                                           @Nullable final LocalDate endDate,
                                                           @NotNull final String... targetCurrencyCodes) {
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

    private <D, M> Optional<M> fetchData(@NotNull final String endpoint,
                                         @Nullable final Map<String, String> queryParameters,
                                         @NotNull final TypeReference<D> dataTransferObjectType,
                                         @NotNull final Type modelType) {
        final URIBuilder resourceIdentifierBuilder = new URIBuilder()
                .setHttpHost(host)
                .appendPath(endpoint);
        if (queryParameters != null) {
            queryParameters.forEach(resourceIdentifierBuilder::addParameter);
        }
        try {
            final URI resourceIdentifier = resourceIdentifierBuilder.build();
            final String responseBody = Request.get(resourceIdentifier)
                    .connectTimeout(Timeout.ofSeconds(8))
                    .responseTimeout(Timeout.ofSeconds(5))
                    .execute(client)
                    .returnContent()
                    .asString(StandardCharsets.UTF_8);
            final D dataTransferObject = parser.readValue(responseBody, dataTransferObjectType);
            final M convertedData = mapper.map(dataTransferObject, modelType);
            LOGGER.info("Fetched data from " + resourceIdentifier);
            return Optional.of(convertedData);
        } catch (final URISyntaxException | IOException | MappingException e) {
            LOGGER.error("Failed to fetch data: " + e.getMessage());
            return Optional.empty();
        }
    }

    private <D> Optional<List<ExchangeRate>> fetchExchangeRatesData(@NotNull final TypeReference<D> dataTransferObjectType,
                                                                    @NotNull final String endpoint,
                                                                    @NotNull final String baseCurrencyCode,
                                                                    @NotNull final String... targetCurrencyCodes) {
        final Map<String, String> parameters = (targetCurrencyCodes.length > 0) ?
                Map.of("from", baseCurrencyCode, "to", String.join(",", targetCurrencyCodes)) :
                Map.of("from", baseCurrencyCode);
        return fetchData(endpoint, parameters, dataTransferObjectType, new TypeToken<List<ExchangeRate>>(){}.getType());
    }
}
