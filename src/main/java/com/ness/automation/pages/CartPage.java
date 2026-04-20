package com.ness.automation.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import com.ness.automation.utils.NumberParser;

public class CartPage extends BasePage {

    By cartSummary = By.xpath("//*[@id='sc-subtotal-amount-activecart']/span");

    @Step("Read cart summary amount")
    public int getCartSummary() {
        String text = getText(cartSummary);
        return(int) NumberParser.parseToNumber(text);
    }

}
