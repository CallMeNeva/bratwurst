package io.github.altoukhovmax.frankfurterdesktop.service;

import io.github.altoukhovmax.frankfurterdesktop.model.Currency;
import io.github.altoukhovmax.frankfurterdesktop.model.ExchangeRate;
import io.github.altoukhovmax.frankfurterdesktop.service.request.AbstractExchangeRatesRequest;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ExchangeRatesService {

    List<ExchangeRate> getExchangeRates(@NotNull AbstractExchangeRatesRequest request) throws ServiceException;

    List<Currency> getSupportedCurrencies() throws ServiceException;
}
