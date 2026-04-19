package com.ness.automation.pages;

import org.openqa.selenium.By;

public class SearchResultsPage extends BasePage {

    By filterPriceId = By.id("low-price");

    public void filterByPrice(double price) {
        getDriver().findElement(filterPriceId);

    }

}
