package com.ness.automation.workflows;


import java.util.List;

import com.ness.automation.pages.SearchResultsPage;

public class ItemsWorkFlows {

    public static void addItemsToCart(List<String> itemsLinks, SearchResultsPage searchResultsPage) {
        for (String itemLink : itemsLinks) {
            searchResultsPage
                    .openItemInNewTab(itemLink)
                    .addItemToCart()
                    .closeTab();
        }
    }
}