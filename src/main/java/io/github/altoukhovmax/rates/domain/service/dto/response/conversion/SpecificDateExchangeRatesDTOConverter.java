package io.github.altoukhovmax.rates.domain.service.dto.response.conversion;

import io.github.altoukhovmax.rates.domain.entity.ExchangeRate;
import io.github.altoukhovmax.rates.domain.service.dto.response.SpecificDateExchangeRatesDTO;
import org.modelmapper.AbstractConverter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class SpecificDateExchangeRatesDTOConverter extends AbstractConverter<SpecificDateExchangeRatesDTO, List<ExchangeRate>> {

    @Override
    protected List<ExchangeRate> convert(final SpecificDateExchangeRatesDTO dataTransferObject) {
        return dataTransferObject.rates().entrySet().stream()
                .map(entry -> new ExchangeRate(dataTransferObject.base(), entry.getKey(), entry.getValue(), dataTransferObject.date()))
                .sorted(Comparator.comparing(ExchangeRate::targetCurrencyCode))
                .collect(Collectors.toList());
    }
}
