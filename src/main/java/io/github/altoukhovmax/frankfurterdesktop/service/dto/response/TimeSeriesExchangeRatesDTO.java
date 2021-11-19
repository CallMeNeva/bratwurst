package io.github.altoukhovmax.frankfurterdesktop.service.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Map;

@JsonIgnoreProperties(value = {"amount", "start_date", "end_date"})
public record TimeSeriesExchangeRatesDTO(@NotNull String base,
                                         @NotNull Map<LocalDate, Map<String, Double>> rates) {
}
