package com.ness.automation.pages;

import com.ness.automation.core.config.ConfigManager;
import com.ness.automation.core.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Constructor;
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


    private String tabHandler;
    protected final Logger log = LogManager.getLogger(getClass());
    private final WebDriver driver;
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

    protected void waitForClickable(By locator , int timeout ) {
        wait.withTimeout(Duration.ofSeconds(timeout)).until(ExpectedConditions.elementToBeClickable(locator));
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



    /**
     * Opens {@code link} in a new tab and returns a page for that tab. The original window
     * handle is stored on {@code this} and copied to the returned instance so {@link #closeTab()}
     * works on either object.
     */
    protected <T extends BasePage> T openNewTabAndReturn(Class<T> pageClass, String link) {
        tabHandler = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(link);
        try {
            Constructor<T> ctor = pageClass.getDeclaredConstructor();
            ctor.setAccessible(true);
            T page = ctor.newInstance();
            copyTabHandlerTo(page, this);
            return page;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Could not create page: " + pageClass.getName(), e);
        }
    }

    private static void copyTabHandlerTo(BasePage target, BasePage source) {
        target.tabHandler = source.tabHandler;
    }



    
    public void closeTab(){
        driver.close();
        driver.switchTo().window(tabHandler);
    }



    protected boolean isPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public String getText(By locator) {
        return getDriver().findElement(locator).getText();
    }


}
