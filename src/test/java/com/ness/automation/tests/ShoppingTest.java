package com.ness.automation.tests;

import java.util.List;
import java.util.Optional;

import com.ness.automation.assertions.AssertBudgetInCart;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ness.automation.base.BaseTest;
import com.ness.automation.core.DataReader;
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
    @DataProvider(name = "scenarios", parallel = false)
    public Object[][] scenarios() {
        return DataReader.loadScenarios().stream().map(s -> new Object[] { s }).toArray(Object[][]::new);
    }

    @Test(dataProvider = "scenarios")
    @Description("Open home page and navigate to search")
    public void shouldNotExceedBudget(TestScenario scenario) {
        int count = scenario.getItemsCount();
        int maxPrice = scenario.getMaxPricePerItem();
        String query = scenario.getQuery();
        SearchResultsPage searchResultsPage = new HomePage().searchItem(scenario.getQuery());
        Optional<List<String>> itemsLinks = SearchWorkFlowScenarios.searchItemsByNameUnderPrice(query, maxPrice, count,
                searchResultsPage);
        if (itemsLinks.isEmpty()) {
            return;
        }
        ItemsWorkFlows.addItemsToCart(itemsLinks.get(), searchResultsPage);
        int cartTotalPrice = searchResultsPage.openCart().getCartSummary();
        AssertBudgetInCart.assertBudgetInCart(maxPrice, count, cartTotalPrice);
    }

    public void assertAmount(int budget, int count, int cartTotalPrice) {
        int totalBudgetAllowed = budget * count;
        Assert.assertTrue(cartTotalPrice <= totalBudgetAllowed,
                String.format("cart total price : %s exceed the allowed total budget:%s ", cartTotalPrice,
                        totalBudgetAllowed));
    }

}
