package com.ness.automation.pages.components;
import com.ness.automation.pages.BasePage;

import org.openqa.selenium.By;




public class SearchBarComponent extends BasePage {

    private static final By SEARCH_INPUT = By.id("gh-ac");
    private static final By SUBMIT_BUTTON = By.id("gh-search-btn");

    


    

    public void enterTextToSearchBox(String itemName) {
       type(SEARCH_INPUT, itemName);
    }

    public void pressOnSearchButton() {
        click(SUBMIT_BUTTON);
    }

    public void searchForItem(String itemName) {
        enterTextToSearchBox(itemName);
        pressOnSearchButton();
    }
}
