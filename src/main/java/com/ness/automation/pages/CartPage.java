package com.ness.automation.pages;

import org.openqa.selenium.By;

import com.ness.automation.core.config.ConfigManager;
import com.ness.automation.core.report.AllureAttachments;
import com.ness.automation.utils.generalUtils.NumberParser;

import io.qameta.allure.Step;

public class CartPage extends BasePage {

    By cartSummary = By.xpath("//*[@id='sc-subtotal-amount-activecart']/span");

    public CartPage() {
        super();
        getWait().waitForVisible(cartSummary);
        if (ConfigManager.SCREENSHOT_ON_EXPLICIT_PAGE) {
            AllureAttachments.screenshot(getDriver(), "cart page");
        }
    }

    @Step("Read cart summary amount")
    public int getCartSummary() {
        String text = getOpration().getText(cartSummary);
        return (int) NumberParser.parseToNumber(text);
    }

}
