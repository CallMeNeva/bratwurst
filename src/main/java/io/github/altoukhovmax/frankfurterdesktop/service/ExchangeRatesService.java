package io.github.altoukhovmax.frankfurterdesktop.service;

import io.github.altoukhovmax.frankfurterdesktop.model.ExchangeRate;
import io.github.altoukhovmax.frankfurterdesktop.service.request.AbstractExchangeRatesRequest;

import java.util.Set;

public interface ExchangeRatesService {

    Set<ExchangeRate> serve(AbstractExchangeRatesRequest request) throws ServiceException;
}
