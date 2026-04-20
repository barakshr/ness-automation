package com.ness.automation.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ness.automation.core.driver.DriverManager;
import com.ness.automation.pages.BasePage;

public class PaginationComponent extends BasePage {

    By nextButton = By.xpath(
            "//*[@class='s-pagination-item s-pagination-next s-pagination-button s-pagination-button-accessibility s-pagination-separator']");
    protected final WebDriver driver;

    public PaginationComponent() {
        this.driver = DriverManager.getDriver();
    }

    public PaginationComponent clickNextButton() {
        click(nextButton);
        return this;
    }

    public Boolean isNextButtonEnabled() {
        try {
            waitForClickable(nextButton, 7);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
