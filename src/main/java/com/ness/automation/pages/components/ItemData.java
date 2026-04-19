package com.ness.automation.pages.components;

public class ItemData {

    private final String link;
    private final int price;

    public ItemData(String link, int price) {
        this.link = link;
        this.price = price;
    }


    public String getLink() {
        return link;
    }

    public int getPrice() {
        return price;
    }
}
