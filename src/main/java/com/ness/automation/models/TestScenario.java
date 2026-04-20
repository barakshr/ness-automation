package com.ness.automation.models;

public class TestScenario {
    private String id;
    private String description;
    private String query;
    private int maxPricePerItem;
    private int itemsCount;

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getQuery() {
        return query;
    }

    public int getMaxPricePerItem() {
        return maxPricePerItem;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s — query='%s' max=$%d count=%d",
                id, description, query, maxPricePerItem, itemsCount);
    }
}
