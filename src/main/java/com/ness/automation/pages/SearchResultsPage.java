package com.ness.automation.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SearchResultsPage extends BasePage {

    By filterPrice = By.className("textbox__control");

    public SearchResultsPage filterMaxByPrice(String price) {
        List<WebElement> pricesTextBox = getDriver().findElements(filterPrice);
        WebElement maxPriceElement = pricesTextBox.get(1);
        type(maxPriceElement, price);
        return this;
    }

}
