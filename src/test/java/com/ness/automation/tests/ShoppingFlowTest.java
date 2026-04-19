package com.ness.automation.tests;

import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ness.automation.base.BaseTest;
import com.ness.automation.core.DataReader;
import com.ness.automation.pages.HomePage;
import com.ness.automation.pages.SearchResultsPage;
import com.ness.automation.pages.components.ItemData;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

@Epic("Ness Amazon Automation")
@Feature("Shopping flow")
public class ShoppingFlowTest extends BaseTest {

    @DataProvider(name = "scenarios", parallel = false)
    public Object[][] scenarios() {
        return DataReader.loadScenarios()
                .stream()
                .map(s -> new Object[] { s })
                .toArray(Object[][]::new);
    }

    // @Test(dataProvider = "scenarios")
    @Test
    @Description("Open home page and navigate to search")
    // public void shouldNotExceedBudget(TestScenario scenario)
    public void shouldNotExceedBudget() {
        SearchResultsPage searchResultsPage = new HomePage().searchItem("shoes");
        List<ItemData> items = searchResultsPage.getItemsFromPage();

        for (ItemData item : items) {
            if (item.getPrice() < 110) {
                searchResultsPage.openItemInNewTab(item.getLink()).addItemToCart();
                searchResultsPage.closeTab();
                if (items.size() > 4) {
                    break;
                }
            }
        }
        
        int y = 0;

        // searchResultsPage.openItemInNewTab(items.get(0).getLink()).addItemToCart();
        // searchResultsPage.closeTab();
        // searchResultsPage.openItemInNewTab(items.get(1).getLink()).addItemToCart();
        // searchResultsPage.closeTab();

    }

    // @Test
    // @Description("Open home page and navigate to search")
    // // public void shouldNotExceedBudget(TestScenario scenario)
    // public void shouldNotExceedBudget2() {
    // new HomePage()
    // .open()
    // .goToSearch();
    // }

    // @Test
    // @Description("Open home page and navigate to search")
    // // public void shouldNotExceedBudget(TestScenario scenario)
    // public void shouldNotExceedBudget3() {

    // }
}
