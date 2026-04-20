package com.ness.automation.pages.components;

import com.ness.automation.core.driver.DriverManager;
import com.ness.automation.pages.BasePage;
import com.ness.automation.utils.Parser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaginationComponent extends BasePage {

    By lastPageButton = By.xpath("//*[@class='s-pagination-item s-pagination-next s-pagination-button s-pagination-button-accessibility s-pagination-separator']/parent::*/parent::*/preceding-sibling::*[1]");

    By nextButton = By.xpath("//*[@class='s-pagination-item s-pagination-next s-pagination-button s-pagination-button-accessibility s-pagination-separator']");
    protected final WebDriver driver;


    public int getNumberOfPage() {
        try {
            waitForVisible(nextButton);
            String lastPage = getText(lastPageButton);
            return (int) Parser.parseToNumber(lastPage);
        } catch (Exception e) {
            return 1;
        }
    }



    public PaginationComponent() {
        this.driver = DriverManager.getDriver();
    }

    public PaginationComponent clickNextButton() {
        click(nextButton);
        return this;
    }

    public Boolean isNextButtonEnabled() {
        try {
            waitForClickable(nextButton, 7);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
