package com.ness.automation.models;

public class CartSummary {
    private final double subtotal;
    private final int    itemCount;

    public CartSummary(double subtotal, int itemCount) {
        this.subtotal  = subtotal;
        this.itemCount = itemCount;
    }

    public double getSubtotal()  { return subtotal; }
    public int    getItemCount() { return itemCount; }

    @Override
    public String toString() {
        return String.format("CartSummary{subtotal=%.2f, itemCount=%d}", subtotal, itemCount);
    }
}
