package com.altoukhov.frankfurterdesktop.service.response.mapper;

import com.altoukhov.frankfurterdesktop.model.ExchangeRate;
import com.altoukhov.frankfurterdesktop.service.response.SpecificDateExchangeRatesDTO;

import java.util.Set;
import java.util.stream.Collectors;

public enum SpecificDateExchangeRatesDTOMapper implements DTOMapper<SpecificDateExchangeRatesDTO, Set<ExchangeRate>> {
    INSTANCE;

    @Override
    public Set<ExchangeRate> map(SpecificDateExchangeRatesDTO dataObj) throws DTOMappingException {
        try {
            return dataObj.rates()
                    .entrySet()
                    .stream()
                    .map(entry -> new ExchangeRate(dataObj.base(), entry.getKey(), entry.getValue(), dataObj.date()))
                    .collect(Collectors.toSet());
        } catch (IllegalArgumentException e) {
            throw new DTOMappingException(e);
        }
    }
}
