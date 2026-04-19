package com.ness.automation.pages;

import org.openqa.selenium.By;

public class ItemPage extends BasePage {

    By addItemToCart = By.id("add-to-cart-button");

    public ItemPage addItemToCart() {
        click(addItemToCart);
        return this;
    }

}
