package io.github.altoukhovmax.frankfurterdesktop.service.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public record TimeSeriesExchangeRatesDTO(String base, Map<LocalDate, Map<String, BigDecimal>> rates) {
}
