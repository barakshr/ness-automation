package com.ness.automation.models;

public class ItemData {

    private final String link;
    private final double price;

    public ItemData(String link, double price) {
        this.link = link;
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public double getPrice() {
        return price;
    }
}
