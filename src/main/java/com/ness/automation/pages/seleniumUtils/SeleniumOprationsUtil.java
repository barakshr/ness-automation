package com.ness.automation.pages.seleniumUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SeleniumOprationsUtil {

    private final SeleniumWaitUtil seleniumWaitUtil;

    public SeleniumOprationsUtil( SeleniumWaitUtil seleniumWaitUtil) {
        this.seleniumWaitUtil = seleniumWaitUtil;
    }

    public List<WebElement> getElements(By locator) {
     return   seleniumWaitUtil.waitForPresence(locator).findElements(locator);
    
    }

    public void click(By locator) {
        seleniumWaitUtil.waitForClickable(locator).click();
    }

    public void click(WebElement element) {
        seleniumWaitUtil.waitForClickable(element).click();
    }


    public void type(By locator, String text) {
        WebElement el = seleniumWaitUtil.waitForVisible(locator);
        el.clear();
        el.sendKeys(text);
    }


    public String getText(By locator) {
      return  seleniumWaitUtil.waitForVisible(locator).getText();
    }
}
