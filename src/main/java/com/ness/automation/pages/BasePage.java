package com.ness.automation.pages;

import com.ness.automation.core.config.ConfigManager;
import com.ness.automation.core.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Parent of every concrete page object.
 * <p>
 * Pulls the driver from {@link DriverManager} on construction so that
 * test code never has to pass the driver explicitly:
 * <pre>
 *     new HomePage().open().clickSearchBox();
 * </pre>
 */
public abstract class BasePage {

    protected final Logger log = LogManager.getLogger(getClass());
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(ConfigManager.TIMEOUT_EXPLICIT));
    }
    protected BasePage(String url) {
       this();
       driver.get(url);
    }


    public WebDriver getDriver() {
        return driver;
    }

    public List<WebElement> getElements(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElements(locator);
    }


    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    protected WebElement waitForVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitForClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void Click(WebElement element) {
        waitForClickable(element).click();
    }


    protected void click(By locator) {
        waitForClickable(locator).click();
    }
    protected void type(WebElement webElement, String text) {
        WebElement el = waitForVisible(webElement);
        el.clear();
        el.sendKeys(text);
    }


    protected void type(By locator, String text) {
        WebElement el = waitForVisible(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected boolean isPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }
}
