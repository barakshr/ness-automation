package com.ness.automation.pages.components;

import org.openqa.selenium.By;

import com.ness.automation.pages.BasePage;
import com.ness.automation.pages.CartPage;

import io.qameta.allure.Step;

public class TopBarComponent extends BasePage {

    private static final By SEARCH_INPUT = By.id("twotabsearchtextbox");
    private static final By SUBMIT_BUTTON = By.id("nav-search-submit-button");
    private static final By CART_BUTTON = By.id("nav-cart-text-container");

    @Step("Enter search text: {itemName}")
    public void enterTextToSearchBox(String itemName) {
        getOpration().type(SEARCH_INPUT, itemName);
    }

    @Step("Submit search")
    public void pressOnSearchButton() {
        getOpration().click(SUBMIT_BUTTON);
    }

    @Step("Search for item from top bar: {itemName}")
    public void searchForItem(String itemName) {
        enterTextToSearchBox(itemName);
        pressOnSearchButton();
    }

    @Step("Open cart from top bar")
    public CartPage openCart() {
        getOpration().click(CART_BUTTON);
        return new CartPage();
    }

}
