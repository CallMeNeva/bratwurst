package com.altmax.rates.service;

import com.altmax.rates.model.ExchangeRate;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRatesService {

    @NotNull
    List<ExchangeRate> fetchRates(@NotNull String appId,
                                  @NotNull String baseCurrencyCode,
                                  boolean includeExtras,
                                  @NotNull String... targetCurrencyCodes) throws ServiceException;

    @NotNull
    List<ExchangeRate> fetchRates(@NotNull String appId,
                                  @NotNull String baseCurrencyCode,
                                  @NotNull LocalDate date,
                                  boolean includeExtras,
                                  @NotNull String... targetCurrencyCodes) throws ServiceException;
}
