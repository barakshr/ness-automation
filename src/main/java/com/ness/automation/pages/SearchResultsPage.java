package com.ness.automation.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ness.automation.pages.components.ItemData;
import com.ness.automation.pages.components.TopBarComponent;
import com.ness.automation.utils.Parser;

public class SearchResultsPage extends BasePage {

    private static final TopBarComponent searchBarComponent = new TopBarComponent();

    public List<ItemData> getItemsFromPage() {
        List<ItemData> itemDataList = new ArrayList<>();
        List<WebElement> elemets = getDriver().findElements(By.xpath("//*[@aria-describedby='price-link']"));
        for (WebElement webElement : elemets) {
            String priceText = webElement.findElement(By.className("a-price-whole")).getText();
            double price = Parser.parseAsDouble(priceText);
            String linkToItem = webElement.getAttribute("href");
            ItemData itemData = new ItemData(linkToItem, price);
            itemDataList.add(itemData);
        }
        return itemDataList;
    }

    public ItemPage openItemInNewTab(String link) {
        return openNewTabAndReturn(ItemPage.class, link);
    }

    public CartPage openCart() {
        return searchBarComponent.openCart();
    }

}
