package com.ness.automation.models;

public class SearchResult {
    private final String url;
    private final String title;
    private final double price;

    public SearchResult(String url, String title, double price) {
        this.url   = url;
        this.title = title;
        this.price = price;
    }

    public String getUrl()   { return url; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return String.format("SearchResult{title='%s', price=%.2f, url='%s'}", title, price, url);
    }
}
