package com.altoukhov.frankfurterdesktop.service.response.mapper;

import com.altoukhov.frankfurterdesktop.model.ExchangeRate;
import com.altoukhov.frankfurterdesktop.service.response.TimeSeriesExchangeRatesDTO;

import java.util.Set;
import java.util.stream.Collectors;

public enum TimeSeriesExchangeRatesDTOMapper implements DTOMapper<TimeSeriesExchangeRatesDTO, Set<ExchangeRate>> {
    INSTANCE;

    @Override
    public Set<ExchangeRate> map(TimeSeriesExchangeRatesDTO dataObj) throws DTOMappingException {
        try {
            return dataObj.rates()
                    .entrySet()
                    .stream() /* Date-to-rates mappings/entries */
                    .flatMap(e1 -> e1.getValue()
                            .entrySet()
                            .stream() /* Currency-code-to-rate-value mappings/entries */
                            .map(e2 -> new ExchangeRate(dataObj.base(), e2.getKey(), e2.getValue(), e1.getKey())))
                    .collect(Collectors.toSet());
        } catch (IllegalArgumentException e) {
            throw new DTOMappingException(e);
        }
    }
}
