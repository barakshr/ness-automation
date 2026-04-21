package com.ness.automation.tests;

import java.util.List;
import java.util.Optional;

import org.testng.annotations.Test;

import com.ness.automation.assertions.AssertBudgetInCart;
import com.ness.automation.base.BaseTest;
import com.ness.automation.models.TestScenario;
import com.ness.automation.pages.HomePage;
import com.ness.automation.pages.SearchResultsPage;
import com.ness.automation.workflows.ItemsWorkFlows;
import com.ness.automation.workflows.SearchWorkFlowScenarios;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

@Epic("Ness Amazon Automation")
@Feature("Shopping flow")
public class ShoppingTest extends BaseTest {

    @Test(dataProvider = "scenarios")
    @Description("Open home page and navigate to search")
    public void shouldNotExceedBudget(TestScenario scenario) {
        int count = scenario.getItemsCount();
        int maxPrice = scenario.getMaxPricePerItem();
        String query = scenario.getQuery();
        SearchResultsPage searchResultsPage = new HomePage().searchItem(query);
        Optional<List<String>> itemsLinks = SearchWorkFlowScenarios.searchItemsByNameUnderPrice(maxPrice, count,
                searchResultsPage);
        Integer cartTotalPrice = null;
        int numberofItemsInCart = 0;
        if (itemsLinks.isPresent()) {
            numberofItemsInCart = itemsLinks.get().size();
            ItemsWorkFlows.addItemsToCart(itemsLinks.get(), searchResultsPage);
            cartTotalPrice = searchResultsPage.openCart().getCartSummary();
        }

        AssertBudgetInCart.assertCartTotalNotExceeds(maxPrice, count, cartTotalPrice, numberofItemsInCart);
    }

}
