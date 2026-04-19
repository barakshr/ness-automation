package com.ness.automation.pages;

import org.openqa.selenium.By;

import com.ness.automation.utils.Parser;

public class CartPage extends BasePage {

    By cartSummary = By.xpath("//*[@id='sc-subtotal-amount-activecart']/span");


    public int getCartSummary() {
        String text = getText(cartSummary);
        return Parser.parseAsInt(text);
    }

}
