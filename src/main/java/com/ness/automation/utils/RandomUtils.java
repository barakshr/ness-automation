package com.ness.automation.utils;

import java.util.List;
import java.util.Random;

public final class RandomUtils {

    private static final Random RANDOM = new Random();

    private RandomUtils() {}

    public static <T> T pickRandom(List<T> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Cannot pick from empty or null list");
        }
        return items.get(RANDOM.nextInt(items.size()));
    }
}
