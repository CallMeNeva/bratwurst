package io.github.altoukhovmax.rates.domain.service.dto.response.conversion;

import io.github.altoukhovmax.rates.domain.entity.ExchangeRate;
import io.github.altoukhovmax.rates.domain.service.dto.response.SpecificDateExchangeRatesDTO;
import org.modelmapper.AbstractConverter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class SpecificDateExchangeRatesDTOConverter
        extends AbstractConverter<SpecificDateExchangeRatesDTO, List<ExchangeRate>> {

    @Override
    protected List<ExchangeRate> convert(SpecificDateExchangeRatesDTO dto) {
        return dto.rates().entrySet().stream()
                .map(e -> new ExchangeRate(dto.base(), e.getKey(), e.getValue(), dto.date()))
                .sorted(Comparator.comparing(ExchangeRate::targetCurrencyCode))
                .collect(Collectors.toList());
    }
}
