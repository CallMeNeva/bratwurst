package io.github.altoukhovmax.frankfurterdesktop.service.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public record SpecificDateExchangeRatesDTO(String base, LocalDate date, Map<String, BigDecimal> rates) {
}
