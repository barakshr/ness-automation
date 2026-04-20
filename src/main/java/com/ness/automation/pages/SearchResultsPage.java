package com.ness.automation.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ness.automation.pages.components.ItemData;
import com.ness.automation.pages.components.PaginationComponent;
import com.ness.automation.pages.components.TopBarComponent;
import com.ness.automation.utils.Parser;

public class SearchResultsPage extends BasePage {

    private static final TopBarComponent searchBarComponent = new TopBarComponent();
    private static final PaginationComponent paginationComponent = new PaginationComponent();


    public SearchResultsPage() {
        super();
    }

    public SearchResultsPage(String url) {
        super(url);
    }

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

    public SearchResultsPage clickNextButton() {
        paginationComponent.clickNextButton();
        return this;
    }

    public Boolean isNextButtonEnabled() {
        return paginationComponent.isNextButtonEnabled();
    }

    public int getNumberOfPages(){
        return paginationComponent.getNumberOfPage();
    }



}
