// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.service.response;

import java.time.LocalDate;
import java.util.Map;

public record SpecificDateExchangesDTO(double amount, String base, LocalDate date, Map<String, Double> rates) {}
