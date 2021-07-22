package com.altmax.rates.service.request;

import net.moznion.uribuildertiny.URIBuilderTiny;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class ExchangeRatesRequestURIFactory {

    @NotNull
    public static URI create(@NotNull String appId,
                             @NotNull String baseCurrencyCode,
                             boolean includeAlternativeCurrencies,
                             @NotNull String... targetCurrencyCodes) {
        return create("latest.json", appId, baseCurrencyCode, includeAlternativeCurrencies, targetCurrencyCodes);
    }

    @NotNull
    public static URI create(@NotNull String appId,
                             @NotNull String baseCurrencyCode,
                             @NotNull LocalDate date,
                             boolean includeAlternativeCurrencies,
                             @NotNull String... targetCurrencyCodes) {
        return create("historical/" + date.format(DateTimeFormatter.ISO_LOCAL_DATE) + ".json",
                appId, baseCurrencyCode, includeAlternativeCurrencies, targetCurrencyCodes);
    }

    @NotNull
    private static URI create(@NotNull String endpoint,
                              @NotNull String appId,
                              @NotNull String baseCurrencyCode,
                              boolean includeAlternativeCurrencies,
                              @NotNull String... targetCurrencyCodes) {
        URIBuilderTiny builder = new URIBuilderTiny("https://openexchangerates.org/api/")
                .appendPaths(endpoint)
                .addQueryParameter("app_id", appId)
                .addQueryParameter("base", baseCurrencyCode)
                .addQueryParameter("show_alternative", includeAlternativeCurrencies)
                .addQueryParameter("prettyprint", false);
        if (targetCurrencyCodes.length > 0) {
            builder.addQueryParameter("symbols", String.join(",", targetCurrencyCodes));
        }
        return builder.build();
    }
}
