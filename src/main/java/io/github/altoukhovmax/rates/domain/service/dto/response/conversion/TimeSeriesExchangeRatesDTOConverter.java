package io.github.altoukhovmax.rates.domain.service.dto.response.conversion;

import io.github.altoukhovmax.rates.domain.entity.ExchangeRate;
import io.github.altoukhovmax.rates.domain.service.dto.response.TimeSeriesExchangeRatesDTO;
import org.modelmapper.AbstractConverter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class TimeSeriesExchangeRatesDTOConverter
        extends AbstractConverter<TimeSeriesExchangeRatesDTO, List<ExchangeRate>> {

    @Override
    protected List<ExchangeRate> convert(TimeSeriesExchangeRatesDTO dto) {
        return dto.rates().entrySet().stream()
                .flatMap(dateToRatesEntry -> dateToRatesEntry.getValue().entrySet().stream()
                        .map(codeToFactorEntry -> new ExchangeRate(
                                dto.base(),
                                codeToFactorEntry.getKey(),
                                codeToFactorEntry.getValue(),
                                dateToRatesEntry.getKey()
                        )))
                .sorted(Comparator.comparing(ExchangeRate::publicationDate).thenComparing(ExchangeRate::targetCurrencyCode))
                .collect(Collectors.toList());
    }
}
