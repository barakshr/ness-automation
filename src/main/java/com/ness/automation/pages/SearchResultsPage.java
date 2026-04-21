package com.ness.automation.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ness.automation.core.report.AllureAttachments;
import com.ness.automation.pages.components.ItemData;
import com.ness.automation.pages.components.PaginationComponent;
import com.ness.automation.pages.components.TopBarComponent;
import com.ness.automation.utils.NumberParser;

import io.qameta.allure.Step;

public class SearchResultsPage extends BasePage {

    private final TopBarComponent topBarComponent = new TopBarComponent();
    private final PaginationComponent paginationComponent = new PaginationComponent();

    By itemPriceLink = By.xpath("//*[@aria-describedby='price-link']");

    By itemPrice = By.className("a-price-whole");

    public SearchResultsPage() {
        super();
    }

    public SearchResultsPage(String url) {
        super(url);
    }

    @Step("Collect items from current search results page")
    public List<ItemData> getItemsFromPage() {
        List<WebElement> elements;
        try {
            elements = getOpration().getElements(itemPriceLink);
        } catch (Exception ignore) {
            return null;
        }
        List<ItemData> itemDataList = new ArrayList<>();
        for (WebElement webElement : elements) {
            String priceText = webElement.findElement(itemPrice).getText();
            double price = NumberParser.parseToNumber(priceText);
            String linkToItem = webElement.getAttribute("href");
            ItemData itemData = new ItemData(linkToItem, price);
            itemDataList.add(itemData);
        }
        return itemDataList;
    }

    @Step("Open item in new tab: {link}")
    public ItemPage openItemInNewTab(String link) {
        AllureAttachments.screenshot(getDriver(), "Open item in new tab: " + link);
        return openNewTabAndReturn(ItemPage.class, link);
    }

    @Step("Open cart from search results")
    public CartPage openCart() {
        return topBarComponent.openCart();

    }

    @Step("Go to next search results page")
    public SearchResultsPage clickNextButton() {
        paginationComponent.clickNextButton();
        return this;
    }

    @Step("Get number of available result pages")
    public int getNumberOfPages() {
        return paginationComponent.getNumberOfPage();
    }

}
