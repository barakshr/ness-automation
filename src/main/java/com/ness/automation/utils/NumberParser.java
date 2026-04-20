package com.ness.automation.utils;

public class NumberParser {

    public static double parseToNumber(String raw) {
        if (raw == null || raw.isBlank()) {
            return 0.0;
        }
        try {
            String cleaned = raw.replaceAll("[^0-9.]", "").trim();
            if (cleaned.isBlank()) {
                return 0.0;
            }
            String normalized = normalizeDecimalSeparators(cleaned);
            return Double.parseDouble(normalized);
        } catch (NumberFormatException e) {
            throw new RuntimeException(String.format("cannot convert this string %s to a number", raw));
        }
    }

    /**
     * Keeps one decimal point: if multiple dots appear (e.g. thousands), the last dot is treated as the
     * decimal separator (so "1.365.55" becomes 1365.55).
     */
    private static String normalizeDecimalSeparators(String cleaned) {
        int lastDot = cleaned.lastIndexOf('.');
        if (lastDot < 0) {
            return cleaned;
        }
        String integerPart = cleaned.substring(0, lastDot).replace(".", "");
        String fractionalPart = cleaned.substring(lastDot + 1).replace(".", "");
        if (integerPart.isBlank() && fractionalPart.isBlank()) {
            return "0";
        }
        if (integerPart.isBlank()) {
            return "0." + fractionalPart;
        }
        if (fractionalPart.isEmpty()) {
            return integerPart;
        }
        return integerPart + "." + fractionalPart;
    }
}
