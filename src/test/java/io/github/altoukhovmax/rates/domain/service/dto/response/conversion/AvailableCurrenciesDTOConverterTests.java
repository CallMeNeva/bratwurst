package io.github.altoukhovmax.rates.domain.service.dto.response.conversion;

import io.github.altoukhovmax.rates.domain.entity.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.HashMap;
import java.util.List;

@DisplayName("AvailableCurrenciesDTOConverter tests")
public final class AvailableCurrenciesDTOConverterTests {

    private static final ModelMapper MAPPER = new ModelMapper();

    @BeforeAll
    public static void initialize() {
        MAPPER.addConverter(new AvailableCurrenciesDTOConverter());
    }

    @Test
    @DisplayName("AvailableCurrenciesDTOConverter converts a valid DTO correctly")
    public void testConvertsCorrectly() {
        HashMap<String, String> dto = new HashMap<>();
        dto.put("USD", "United States Dollar");
        dto.put("GBP", "British Pound Sterling");
        dto.put("JPY", "Japanese Yen");
        List<Currency> result = List.of(
                new Currency("GBP", "British Pound Sterling"),
                new Currency("JPY", "Japanese Yen"),
                new Currency("USD", "United States Dollar")
        );
        Assertions.assertEquals(result, MAPPER.map(dto, new TypeToken<List<Currency>>(){}.getType()));
    }
}
