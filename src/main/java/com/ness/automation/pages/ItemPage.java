package com.ness.automation.pages;

import org.openqa.selenium.By;

import com.ness.automation.core.report.AllureAttachments;

import io.qameta.allure.Step;

public class ItemPage extends BasePage {

    By addItemToCart = By.id("add-to-cart-button");

    @Step("Add current item to cart")
    public ItemPage addItemToCart() {
        AllureAttachments.screenshot(getDriver(), "item screen shot");
        getOpration().click(addItemToCart);
        return this;
    }

}
