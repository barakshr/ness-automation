package com.ness.automation.workflows;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import com.ness.automation.pages.SearchResultsPage;
import com.ness.automation.pages.components.ItemData;

public class SearchWorkFlowScenarios {

    public static Optional<List<String>> searchItemsByNameUnderPrice(String query, int maxPrice, int count, SearchResultsPage searchResultsPage) {

        int numberOfPages = searchResultsPage.getNumberOfPages();
        List<ItemData> itemsUnderPrice = new ArrayList<>();

        if (numberOfPages == 1) {
            List<ItemData> tempFilteredItems = searchResultsPage.getItemsFromPage().stream()
                    .filter(x -> x.getPrice() < maxPrice).toList();
            itemsUnderPrice.addAll(tempFilteredItems);

        } else {
            for (int i = 1; i < numberOfPages; i++) {
                if (itemsUnderPrice.size() >= count) {
                    break;
                }
                List<ItemData> tempFilteredItems = searchResultsPage.getItemsFromPage().stream()
                        .filter(x -> x.getPrice() < maxPrice).toList();
                itemsUnderPrice.addAll(tempFilteredItems);
                searchResultsPage.clickNextButton();
            }
        }

        if (itemsUnderPrice.size() == 0) {
            return Optional.empty();
        }

        List<ItemData> itemLimitList;
        if (itemsUnderPrice.size() >= count) {
            itemLimitList = itemsUnderPrice.subList(0, count);
        } else {
            itemLimitList = itemsUnderPrice;
        }

        List<String> itemsLinks = itemLimitList.stream().map(x -> x.getLink()).toList();
        return Optional.of(itemsLinks);
    }

}
