package com.ness.automation.workflows;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import com.ness.automation.models.ItemData;
import com.ness.automation.pages.SearchResultsPage;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class SearchWorkFlowScenarios {

    @Step("Find up to {count} items under price {maxPrice} for query: {query}")
    public static Optional<List<String>> searchItemsByNameUnderPrice( int maxPrice, int count, SearchResultsPage searchResultsPage) {

        int numberOfPages = searchResultsPage.getNumberOfPages();
        List<ItemData> itemsUnderPrice = new ArrayList<>();

        if (numberOfPages == 1) {
            Allure.step("Filter current page items under price");
            List<ItemData> tempFilteredItems = searchResultsPage.getItemsFromPage().stream()
                    .filter(x -> x.getPrice() < maxPrice).toList();
            itemsUnderPrice.addAll(tempFilteredItems);

        } else {
            for (int i = 1; i < numberOfPages; i++) {
                if (itemsUnderPrice.size() >= count) {
                    break;
                }
                int currentPage = i;
                Allure.step("Filter items under price on page " + currentPage);
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
