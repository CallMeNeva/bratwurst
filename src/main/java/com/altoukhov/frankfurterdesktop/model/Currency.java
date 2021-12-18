package com.altoukhov.frankfurterdesktop.model;

import java.util.Arrays;
import java.util.Objects;

/*
 * Models a currency. Each one is represented by:
 *     - an ISO-4217 code, e.g. "USD";
 *     - an official display name, e.g. "United Stated Dollar".
 *
 * Values last updated on: 20.11.2021
 */
public enum Currency {

    AUSTRALIAN_DOLLAR("AUD", "Australian Dollar"),
    BULGARIAN_LEV("BGN", "Bulgarian Lev"),
    BRAZILIAN_REAL("BRL", "Brazilian Real"),
    CANADIAN_DOLLAR("CAD", "Canadian Dollar"),
    SWISS_FRANC("CHF", "Swiss Franc"),
    CHINESE_RENMINBI_YUAN("CNY", "Chinese Renminbi Yuan"),
    CZECH_KORUNA("CZK", "Czech Koruna"),
    DANISH_KRONE("DKK", "Danish Krone"),
    EURO("EUR", "Euro"),
    BRITISH_POUND("GBP", "British Pound"),
    HONG_KONG_DOLLAR("HKD", "Hong Kong Dollar"),
    CROATIAN_KUNA("HRK", "Croatian Kuna"),
    HUNGARIAN_FORINT("HUF", "Hungarian Forint"),
    INDONESIAN_RUPIAH("IDR", "Indonesian Rupiah"),
    ISRAELI_NEW_SHEQEL("ILS", "Israeli New Sheqel"),
    INDIAN_RUPEE("INR", "Indian Rupee"),
    ICELANDIC_KRONA("ISK", "Icelandic Króna"),
    JAPANESE_YEN("JPY", "Japanese Yen"),
    SOUTH_KOREAN_WON("KRW", "South Korean Won"),
    MEXICAN_PESO("MXN", "Mexican Peso"),
    MALAYSIAN_RINGGIT("MYR", "Malaysian Ringgit"),
    NORWEGIAN_KRONE("NOK", "Norwegian Krone"),
    NEW_ZEALAND_DOLLAR("NZD", "New Zealand Dollar"),
    PHILIPPINE_PESO("PHP", "Philippine Peso"),
    POLISH_ZLOTY("PLN", "Polish Złoty"),
    ROMANIAN_LEU("RON", "Romanian Leu"),
    RUSSIAN_RUBLE("RUB", "Russian Ruble"),
    SWEDISH_KRONA("SEK", "Swedish Krona"),
    SINGAPORE_DOLLAR("SGD", "Singapore Dollar"),
    THAI_BAHT("THB", "Thai Baht"),
    TURKISH_LIRA("TRY", "Turkish Lira"),
    UNITED_STATES_DOLLAR("USD", "United States Dollar"),
    SOUTH_AFRICAN_RAND("ZAR", "South African Rand");

    private final String code;
    private final String displayName;

    Currency(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public static Currency ofCode(String code) throws IllegalArgumentException {
        return Arrays.stream(values())
                .filter(currency -> Objects.equals(code, currency.code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No currency with code '" + code + '\''));
    }

    public String code() {
        return code;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
