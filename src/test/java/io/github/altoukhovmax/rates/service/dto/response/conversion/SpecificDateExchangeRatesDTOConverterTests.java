package io.github.altoukhovmax.rates.service.dto.response.conversion;

import io.github.altoukhovmax.rates.model.ExchangeRate;
import io.github.altoukhovmax.rates.service.dto.response.SpecificDateExchangeRatesDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@DisplayName("SpecificDateExchangeRatesDTOConverter tests")
public final class SpecificDateExchangeRatesDTOConverterTests {

    private static final ModelMapper MAPPER = new ModelMapper();

    @BeforeAll
    public static void initialize() {
        MAPPER.addConverter(new SpecificDateExchangeRatesDTOConverter());
    }

    @Test
    @DisplayName("SpecificDateExchangeRatesDTOConverter converts a valid DTO correctly")
    public void testConvertsCorrectly() {
        final LocalDate date = LocalDate.of(2019, 12, 31);
        final SpecificDateExchangeRatesDTO dto = new SpecificDateExchangeRatesDTO("EUR", date, Map.of("GBP", 0.8508, "USD", 1.1234));
        final List<ExchangeRate> result = List.of(
                new ExchangeRate("EUR", "GBP", 0.8508, date),
                new ExchangeRate("EUR", "USD", 1.1234, date)
        );
        Assertions.assertEquals(result, MAPPER.map(dto, new TypeToken<List<ExchangeRate>>(){}.getType()));
    }
}
