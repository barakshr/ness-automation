package com.ness.automation.pages;

import com.ness.automation.pages.components.ItemData;
import com.ness.automation.pages.components.PaginationComponent;
import com.ness.automation.pages.components.TopBarComponent;
import com.ness.automation.utils.Parser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsPage extends BasePage {

    private static final TopBarComponent topBarComponent = new TopBarComponent();
    private static final PaginationComponent paginationComponent = new PaginationComponent();

    By itemPriceLink = By.xpath("//*[@aria-describedby='price-link']");

    By itemPrice = By.className("a-price-whole");

    public SearchResultsPage() {
        super();
    }

    public SearchResultsPage(String url) {
        super(url);
    }

    public List<ItemData> getItemsFromPage() {
        List<WebElement> elements;
        try {
            elements = getElements(itemPriceLink);
        } catch (Exception ignore) {
            return null;
        }
        List<ItemData> itemDataList = new ArrayList<>();
        for (WebElement webElement : elements) {
            String priceText = webElement.findElement(itemPrice).getText();
            double price = Parser.parseToNumber(priceText);
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
        return topBarComponent.openCart();

    }

    public SearchResultsPage clickNextButton() {
        paginationComponent.clickNextButton();
        return this;
    }



    public int getNumberOfPages() {
        return paginationComponent.getNumberOfPage();
    }


}
