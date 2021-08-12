package io.github.altoukhovmax.rates.service;

import io.github.altoukhovmax.rates.model.Currency;
import io.github.altoukhovmax.rates.model.ExchangeRate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExchangeRatesService {

    Optional<List<ExchangeRate>> fetchExchangeRates(@NotNull final String baseCurrencyCode,
                                                    @NotNull final String... targetCurrencyCodes);

    Optional<List<ExchangeRate>> fetchExchangeRates(@NotNull final String baseCurrencyCode,
                                                    @NotNull final LocalDate date,
                                                    @NotNull final String... targetCurrencyCodes);

    Optional<List<ExchangeRate>> fetchExchangeRates(@NotNull final String baseCurrencyCode,
                                                    @NotNull final LocalDate startDate,
                                                    @Nullable final LocalDate endDate,
                                                    @NotNull final String... targetCurrencyCodes);

    Optional<List<Currency>> fetchAvailableCurrencies();
}
