package com.ness.automation.workflows;


import java.util.List;

import com.ness.automation.pages.SearchResultsPage;import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class ItemsWorkFlows {

    @Step("Add selected items to cart")
    public static void addItemsToCart(List<String> itemsLinks, SearchResultsPage searchResultsPage) {
        for (String itemLink : itemsLinks) {
            Allure.step("Add item to cart from link: " + itemLink, () -> {
                searchResultsPage
                        .openItemInNewTab(itemLink)
                        .addItemToCart()
                        .closeTab();
            });
        }
    }
}