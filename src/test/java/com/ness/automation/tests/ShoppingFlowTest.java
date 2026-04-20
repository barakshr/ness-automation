package com.ness.automation.tests;

import com.ness.automation.base.BaseTest;
import com.ness.automation.core.DataReader;
import com.ness.automation.pages.SearchResultsPage;
import com.ness.automation.pages.components.ItemData;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Epic("Ness Amazon Automation")
@Feature("Shopping flow")
public class ShoppingFlowTest extends BaseTest {

    @DataProvider(name = "scenarios", parallel = false)
    public Object[][] scenarios() {
        return DataReader.loadScenarios().stream().map(s -> new Object[]{s}).toArray(Object[][]::new);
    }

    // @Test(dataProvider = "scenarios")
    @Test
    @Description("Open home page and navigate to search")
    // public void shouldNotExceedBudget(TestScenario scenario)
    public void shouldNotExceedBudget() {
        SearchResultsPage searchResultsPage = new SearchResultsPage("https://www.amazon.com/s?k=shoes&crid=29K4W03Y3P9F7&sprefix=shoes%2Caps%2C210&ref=nb_sb_noss_2");
        int count = 3;
        int maxPrice = 1;
        int number = searchResultsPage.getNumberOfPages();
        List<ItemData> items = new ArrayList<>();


        if (number == 1) {
            List<ItemData> filteredItems = searchResultsPage.getItemsFromPage().stream().filter(x -> x.getPrice() < maxPrice).toList();
            items.addAll(filteredItems);


//            if (items.size() >= count) {
//                newList = items.subList(0, count);
//            } else {
//                newList = items;
//            }
        } else {
            for (int i = 1; i < number; i++) {
                if (items.size() >= count) {
                    break;
                }
                List<ItemData> filteredItems = searchResultsPage.getItemsFromPage().stream().filter(x -> x.getPrice() < maxPrice).toList();
                items.addAll(filteredItems);
                searchResultsPage.clickNextButton();
            }
        }

        List<ItemData> newList;



        if (items.size() >= count) {
            newList = items.subList(0, count);
        } else {
            newList = items;
        }

        if(newList.size() == 0){
            int yyy=0;
        }


        for (ItemData itemData : newList) {
            searchResultsPage
                    .openItemInNewTab(itemData.getLink())
                    .addItemToCart()
                    .closeTab();
        }



        double total = searchResultsPage
                .openCart()
                .getCartSummary();

        int y=0;

//         searchResultsPage.openItemInNewTab(items.get(0).getLink()).addItemToCart();
//         searchResultsPage.closeTab();
//         searchResultsPage.openItemInNewTab(items.get(1).getLink()).addItemToCart();
//         searchResultsPage.closeTab();




//        SearchResultsPage searchResultsPage = new HomePage().searchItem("shoes");
//        items = searchResultsPage.getItemsFromPage();
//
//        int count = 0;
//        for (ItemData item : items) {
//            if (item.getPrice() < 110) {
//                count++;
//                searchResultsPage.openItemInNewTab(item.getLink()).addItemToCart();
//                searchResultsPage.closeTab();
//                if (count > 5) {
//                    break;
//                }
//            }
//        }
//
//        int y = 0;
//        double summery = searchResultsPage.openCart().getCartSummary();
//
//        int yd = 0;

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
