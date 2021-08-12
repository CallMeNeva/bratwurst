package io.github.altoukhovmax.rates.service.dto.response.conversion;

import io.github.altoukhovmax.rates.model.ExchangeRate;
import io.github.altoukhovmax.rates.service.dto.response.TimeSeriesExchangeRatesDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@DisplayName("TimeSeriesExchangeRatesDTOConverter tests")
public final class TimeSeriesExchangeRatesDTOConverterTests {

    private static final ModelMapper MAPPER = new ModelMapper();

    @BeforeAll
    public static void initialize() {
        MAPPER.addConverter(new TimeSeriesExchangeRatesDTOConverter());
    }

    @Test
    @DisplayName("TimeSeriesExchangeRatesDTOConverter converts a valid DTO correctly")
    public void testConvertsCorrectly() {
        final String base = "EUR";
        final LocalDate startDate = LocalDate.of(2020, 1, 2);
        final LocalDate endDate = LocalDate.of(2020, 1, 3);
        final TimeSeriesExchangeRatesDTO dto = new TimeSeriesExchangeRatesDTO(base, Map.of(
                startDate, Map.of("GBP", 0.84828, "USD", 1.1193),
                endDate, Map.of("GBP", 0.85115, "USD", 1.1147)
        ));
        final List<ExchangeRate> result = List.of(
                new ExchangeRate(base, "GBP", 0.84828, startDate),
                new ExchangeRate(base, "USD", 1.1193, startDate),
                new ExchangeRate(base, "GBP", 0.85115, endDate),
                new ExchangeRate(base, "USD", 1.1147, endDate)
        );
        Assertions.assertEquals(result, MAPPER.map(dto, new TypeToken<List<ExchangeRate>>(){}.getType()));
    }
}
