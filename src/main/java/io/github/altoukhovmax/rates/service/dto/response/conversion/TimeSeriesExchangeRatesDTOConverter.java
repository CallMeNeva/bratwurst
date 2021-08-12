package io.github.altoukhovmax.rates.service.dto.response.conversion;

import io.github.altoukhovmax.rates.model.ExchangeRate;
import io.github.altoukhovmax.rates.service.dto.response.TimeSeriesExchangeRatesDTO;
import org.modelmapper.AbstractConverter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class TimeSeriesExchangeRatesDTOConverter extends AbstractConverter<TimeSeriesExchangeRatesDTO, List<ExchangeRate>> {

    @Override
    protected List<ExchangeRate> convert(final TimeSeriesExchangeRatesDTO dataTransferObject) {
        return dataTransferObject.rates().entrySet().stream()
                .flatMap(dateToRatesEntry -> dateToRatesEntry.getValue().entrySet().stream()
                        .map(codeToFactorEntry -> new ExchangeRate(
                                dataTransferObject.base(),
                                codeToFactorEntry.getKey(),
                                codeToFactorEntry.getValue(),
                                dateToRatesEntry.getKey()
                        )))
                .sorted(Comparator.comparing(ExchangeRate::publicationDate).thenComparing(ExchangeRate::targetCurrencyCode))
                .collect(Collectors.toList());
    }
}
