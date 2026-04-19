package com.ness.automation.pages.components;

import org.openqa.selenium.By;

import com.ness.automation.pages.BasePage;

public class TopBarComponent extends BasePage {

    private static final By SEARCH_INPUT = By.id("twotabsearchtextbox");
    private static final By SUBMIT_BUTTON = By.id("nav-search-submit-button");
    private static final By CART_BUTTON = By.id("nav-cart-text-container");

    public void enterTextToSearchBox(String itemName) {
        type(SEARCH_INPUT, itemName);
    }

    public void pressOnSearchButton() {
        click(SUBMIT_BUTTON);
    }

    public void searchForItem(String itemName) {
        enterTextToSearchBox(itemName);
        pressOnSearchButton();
    }

    public void openCart() {
        click(CART_BUTTON);
    }

}
