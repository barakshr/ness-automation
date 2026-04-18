package com.ness.automation.pages;

import com.ness.automation.core.config.ConfigManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class HomePage extends BasePage {

    private static final By SEARCH_BOX = By.id("twotabsearchtextbox");

    @Step("Open Amazon home page")
    public HomePage open() {
        log.info("Opening {}", ConfigManager.AUT_BASE_URL);
        driver.get(ConfigManager.AUT_BASE_URL);
        attachScreenshotIfEveryPageEnabled("Page: Amazon home");
        return this;
    }

    @Step("Go to search")
    public SearchResultsPage goToSearch() {
        // TODO: type query and submit — implement in next iteration
        return new SearchResultsPage();
    }

    public boolean isSearchBoxPresent() {
        return isPresent(SEARCH_BOX);
    }

    public boolean isSearchBoxFocused() {
        return waitForVisible(SEARCH_BOX)
                .equals(driver.switchTo().activeElement());
    }
}
