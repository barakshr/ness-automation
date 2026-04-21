package com.ness.automation.pages;

import org.openqa.selenium.By;

import com.ness.automation.utils.NumberParser;

import io.qameta.allure.Step;

public class CartPage extends BasePage {

    By cartSummary = By.xpath("//*[@id='sc-subtotal-amount-activecart']/span");

    @Step("Read cart summary amount")
    public int getCartSummary() {
        String text = getOpration().getText(cartSummary);
        return (int) NumberParser.parseToNumber(text);
    }

}
