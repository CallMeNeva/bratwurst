package io.github.altoukhovmax.rates.domain.service.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Map;

@JsonIgnoreProperties(value = {"amount"})
public record SpecificDateExchangeRatesDTO(@NotNull String base,
                                           @NotNull LocalDate date,
                                           @NotNull Map<String, Double> rates) {

}
