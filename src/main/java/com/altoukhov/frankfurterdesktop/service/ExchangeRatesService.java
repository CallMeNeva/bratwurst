package com.altoukhov.frankfurterdesktop.service;

import com.altoukhov.frankfurterdesktop.service.request.AbstractExchangeRatesRequest;
import com.altoukhov.frankfurterdesktop.model.ExchangeRate;

import java.util.Set;

@FunctionalInterface
public interface ExchangeRatesService {

    Set<ExchangeRate> serve(AbstractExchangeRatesRequest request) throws ServiceException;
}
