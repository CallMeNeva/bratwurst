// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.converter;

import io.github.callmeneva.bratwurst.model.Sum;
import javafx.util.StringConverter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class SumStringConverter extends StringConverter<Sum> {

    private static final String DELIMITER = " ";

    @Override
    public String toString(Sum sum) {
        return (sum == null) ? StringUtils.EMPTY : StringUtils.joinWith(DELIMITER, sum.amount(), sum.currencyCode());
    }

    @Override
    public Sum fromString(String string) {
        // This approach limits our selection of alternative delimiters as it must not be a character that might be contained in a double's toString()
        String[] tokens = StringUtils.splitByWholeSeparator(string, DELIMITER);

        if (ArrayUtils.isEmpty(tokens)) {
            return null;
        }

        double amount = Double.parseDouble(tokens[0]);
        String currencyCode = tokens[1];
        return new Sum(currencyCode, amount);
    }
}
