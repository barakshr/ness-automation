package com.ness.automation.tests;

import com.ness.automation.base.BaseTest;
import com.ness.automation.core.DataReader;
import com.ness.automation.models.TestScenario;
import com.ness.automation.pages.HomePage;
import com.ness.automation.pages.SearchResultsPage;
import com.ness.automation.pages.components.ItemData;
import com.ness.automation.workflows.ItemsWorkFlows;
import com.ness.automation.workflows.SearchWorkFlowScenarios;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Epic("Ness Amazon Automation")
@Feature("Shopping flow")
public class ShoppingFlowTest extends BaseTest {
    @DataProvider(name = "scenarios", parallel = false)
    public Object[][] scenarios() {
        return DataReader.loadScenarios().stream().map(s -> new Object[]{s}).toArray(Object[][]::new);
    }


    @Test(dataProvider = "scenarios")
    @Description("Open home page and navigate to search")
    public void shouldNotExceedBudget(TestScenario scenario) {
        int count = scenario.getItemsCount();
        int maxPrice = scenario.getMaxPricePerItem();
        String query = scenario.getQuery();
        SearchResultsPage searchResultsPage = new HomePage().searchItem(scenario.getQuery());
        Optional<List<String>> itemsLinks = SearchWorkFlowScenarios.searchItemsByNameUnderPrice(query, maxPrice, count, searchResultsPage);
        if (itemsLinks.isEmpty()) {
            return;
        }
        ItemsWorkFlows.addItemsToCart(itemsLinks.get(), searchResultsPage);
        int cartTotalPrice = searchResultsPage.openCart().getCartSummary();
        assertAmount(maxPrice, count, cartTotalPrice);
    }

//    @Test(dataProvider = "scenarios")
    @Description("Open home page and navigate to search")
    public void shouldNotExceedBudgetOldWay(TestScenario scenario) {
        int count = scenario.getItemsCount();
        int maxPrice = scenario.getMaxPricePerItem();
        String query = scenario.getQuery();
        SearchResultsPage searchResultsPage = new HomePage().searchItem(scenario.getQuery());
        Optional<List<String>> itemsLinks = searchItemsByNameUnderPrice(query, maxPrice, count, searchResultsPage);
        if (itemsLinks.isEmpty()) {
            return;
        }
        addItemsToCart(itemsLinks.get(), searchResultsPage);
        int cartTotalPrice = searchResultsPage.openCart().getCartSummary();
        assertAmount(maxPrice, count, cartTotalPrice);
    }

  

    public Optional<List<String>> searchItemsByNameUnderPrice(String query, int maxPrice, int count, SearchResultsPage searchResultsPage) {

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

    public void addItemsToCart(List<String> itemsLinks, SearchResultsPage searchResultsPage) {
        for (String itemLink : itemsLinks) {
            searchResultsPage
                    .openItemInNewTab(itemLink)
                    .addItemToCart()
                    .closeTab();
        }
    }

    public void assertAmount(int budget, int count, int cartTotalPrice) {
        int totalBudgetAllowed = budget * count;
        Assert.assertTrue(cartTotalPrice<=totalBudgetAllowed,
                String.format("cart total price : %s exceed the allowed total budget:%s ",cartTotalPrice,totalBudgetAllowed));
    }

}
