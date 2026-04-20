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

        SearchResultsPage test = new SearchResultsPage(
                "https://www.amazon.com/s?k=shoes&crid=29K4W03Y3P9F7&sprefix=shoes%2Caps%2C210&ref=nb_sb_noss_2");

        if (test.isNextButtonEnabled()) {
            test.clickNextButton();
        }

        if (test.isNextButtonEnabled()) {
            test.clickNextButton();
        }
        

        SearchResultsPage searchResultsPage = new HomePage().searchItem("shoes");
        List<ItemData> items = searchResultsPage.getItemsFromPage();

        int count = 0;
        for (ItemData item : items) {
            if (item.getPrice() < 110) {
                count++;
                searchResultsPage.openItemInNewTab(item.getLink()).addItemToCart();
                searchResultsPage.closeTab();
                if (count > 4) {
                    break;
                }
            }
        }

        int y = 0;
        double summery = searchResultsPage.openCart().getCartSummary();

        int yd = 0;

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
