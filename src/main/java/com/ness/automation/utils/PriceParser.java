package com.ness.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PriceParser {

    private static final Logger log = LogManager.getLogger(PriceParser.class);

    private PriceParser() {}

    /**
     * Parses strings like "$12.99", "12.99", "$1,299.00", "$12.99 - $24.99"
     * For ranges, returns the minimum value.
     */
    public static double parse(String raw) {
        if (raw == null || raw.isBlank()) return 0.0;
        String cleaned = raw.trim();
        // Range: take the lower bound
        if (cleaned.contains("-")) {
            String lower = cleaned.split("-")[0];
            return parseSingle(lower);
        }
        return parseSingle(cleaned);
    }

    private static double parseSingle(String raw) {
        try {
            // Remove currency symbols, spaces, commas
            String digits = raw.replaceAll("[^0-9.]", "");
            return digits.isBlank() ? 0.0 : Double.parseDouble(digits);
        } catch (NumberFormatException e) {
            log.warn("Could not parse price from '{}': {}", raw, e.getMessage());
            return 0.0;
        }
    }
}
