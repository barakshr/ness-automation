package com.ness.automation.pages;

import com.ness.automation.core.config.ConfigManager;
import com.ness.automation.pages.components.TopBarComponent;
import io.qameta.allure.Step;

public class HomePage extends BasePage {

    private final TopBarComponent topBarComponent = new TopBarComponent();

    public HomePage() {
        super(ConfigManager.AUT_BASE_URL);
    }

    @Step("Search for item: {itemName}")
    public SearchResultsPage searchItem(String itemName) {
        topBarComponent.searchForItem(itemName);
        return new SearchResultsPage();
    }

}
