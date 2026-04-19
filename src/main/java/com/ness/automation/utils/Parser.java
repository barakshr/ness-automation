package com.ness.automation.utils;

public class Parser {
    

    public static int parseAsInt(String raw) {
        if (raw == null || raw.isBlank()) return 0;
        try {
            String digitsOnly = raw.replaceAll("[^0-9]", "");
            return digitsOnly.isBlank() ? 0 : Integer.parseInt(digitsOnly);
        } catch (NumberFormatException e) {
            throw new RuntimeException(String.format("can converet this string %s to int ", raw));
        }
    }
}
