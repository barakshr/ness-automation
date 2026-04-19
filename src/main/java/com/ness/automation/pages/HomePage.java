package com.ness.automation.pages;

import com.ness.automation.core.config.ConfigManager;
import com.ness.automation.pages.components.SearchBarComponent;

public class HomePage extends BasePage {

    private static final SearchBarComponent searchBarComponent = new SearchBarComponent();

    public HomePage() {
        super(ConfigManager.AUT_BASE_URL);
    }

    public SearchResultsPage searchItem(String itemName) {
        searchBarComponent.searchForItem(itemName);
        return new SearchResultsPage();
    }

}
