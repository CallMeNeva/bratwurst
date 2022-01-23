// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.frankfurterdesktop.service.response;

import java.time.LocalDate;
import java.util.Map;

public record TimeSeriesExchangesDTO(double amount, String base, Map<LocalDate, Map<String, Double>> rates) {}
