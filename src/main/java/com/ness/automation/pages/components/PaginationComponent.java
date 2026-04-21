package com.ness.automation.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ness.automation.core.driver.DriverManager;
import com.ness.automation.pages.BasePage;
import com.ness.automation.utils.NumberParser;

import io.qameta.allure.Step;

public class PaginationComponent extends BasePage {

    By lastPageButton = By.xpath(
            "//*[@class='s-pagination-item s-pagination-next s-pagination-button s-pagination-button-accessibility s-pagination-separator']/parent::*/parent::*/preceding-sibling::*[1]");

    By nextButton = By.xpath(
            "//*[@class='s-pagination-item s-pagination-next s-pagination-button s-pagination-button-accessibility s-pagination-separator']");
    protected final WebDriver driver;

    @Step("Read number of pagination pages")
    public int getNumberOfPage() {
        try {
            getWait().waitForVisible(nextButton);
            String lastPage = getOpration().getText(lastPageButton);
            return (int) NumberParser.parseToNumber(lastPage);
        } catch (Exception e) {
            return 1;
        }
    }

    public PaginationComponent() {
        this.driver = DriverManager.getDriver();
    }

    @Step("Click next page in pagination")
    public PaginationComponent clickNextButton() {
        getOpration().click(nextButton);
        return this;
    }

    @Step("Check if next page button is enabled")
    public Boolean isNextButtonEnabled() {
        try {
            getWait().waitForClickable(nextButton, 7);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
