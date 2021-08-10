package io.github.altoukhovmax.rates.domain.service.dto.response.conversion;

import io.github.altoukhovmax.rates.domain.entity.Currency;
import org.modelmapper.AbstractConverter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

// Maybe I'm missing something, but I think ModelMapper doesn't play nice with non-concrete source types.
// Using a concrete Map type instead seems to solve the problem.
public final class AvailableCurrenciesDTOConverter
        extends AbstractConverter<HashMap<String, String>, List<Currency>> {

    @Override
    protected List<Currency> convert(HashMap<String, String> dto) {
        return dto.entrySet().stream()
                .map(e -> new Currency(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(Currency::code))
                .collect(Collectors.toList());
    }
}
