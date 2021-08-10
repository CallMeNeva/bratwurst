package io.github.altoukhovmax.rates.domain.service;

import io.github.altoukhovmax.rates.domain.entity.Currency;
import io.github.altoukhovmax.rates.domain.entity.ExchangeRate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExchangeRatesService {

    Optional<List<ExchangeRate>> fetchExchangeRates(@NotNull String baseCurrencyCode,
                                                    @NotNull String... targetCurrencyCodes);

    Optional<List<ExchangeRate>> fetchExchangeRates(@NotNull String baseCurrencyCode,
                                                    @NotNull LocalDate date,
                                                    @NotNull String... targetCurrencyCodes);

    Optional<List<ExchangeRate>> fetchExchangeRates(@NotNull String baseCurrencyCode,
                                                    @NotNull LocalDate startDate,
                                                    @Nullable LocalDate endDate,
                                                    @NotNull String... targetCurrencyCodes);

    Optional<List<Currency>> fetchAvailableCurrencies();
}
