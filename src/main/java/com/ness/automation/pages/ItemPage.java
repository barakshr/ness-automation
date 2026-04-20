package com.ness.automation.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ItemPage extends BasePage {

    By addItemToCart = By.id("add-to-cart-button");

    @Step("Add current item to cart")
    public ItemPage addItemToCart() {
        click(addItemToCart);
        return this;
    }

}
