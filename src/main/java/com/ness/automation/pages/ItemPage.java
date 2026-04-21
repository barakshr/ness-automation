package com.ness.automation.pages;

import org.openqa.selenium.By;

import com.ness.automation.core.config.ConfigManager;
import com.ness.automation.core.report.AllureAttachments;

import io.qameta.allure.Step;

public class ItemPage extends BasePage {

    By addItemToCart = By.id("add-to-cart-button");


    public ItemPage() {
        super();
        // getWait().waitForVisible(addItemToCart);
        // if (ConfigManager.SCREENSHOT_ON_EXPLICIT_OPERATION) {
        //     AllureAttachments.screenshot(getDriver(), "item screen shot");
        // }
    }

    @Step("Add current item to cart")
    public ItemPage addItemToCart() {
        getOpration().click(addItemToCart);
        if (ConfigManager.SCREENSHOT_ON_EXPLICIT_OPERATION) {
            AllureAttachments.screenshot(getDriver(), "item added to cart");
        }
        return this;
    }

}
