package com.ness.automation.pages;

import java.lang.reflect.Constructor;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ness.automation.core.config.ConfigManager;
import com.ness.automation.core.driver.DriverManager;
import com.ness.automation.pages.seleniumUtils.SeleniumOprationsUtil;
import com.ness.automation.pages.seleniumUtils.SeleniumWaitUtil;

/**
 * Parent of every concrete page object.
 * <p>
 * Pulls the driver from {@link DriverManager} on construction so that
 * test code never has to pass the driver explicitly:
 * 
 * <pre>
 * new HomePage().open().clickSearchBox();
 * </pre>
 */
public abstract class BasePage {

    private String tabHandler;
    protected final Logger log = LogManager.getLogger(getClass());
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final SeleniumWaitUtil seleniumWaitUtil;
    private final SeleniumOprationsUtil seleniumOprationsUtil;

    protected BasePage() {
        DriverManager.initDriver();
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(ConfigManager.TIMEOUT_EXPLICIT));
        this.seleniumWaitUtil = new SeleniumWaitUtil(wait);
        this.seleniumOprationsUtil = new SeleniumOprationsUtil(seleniumWaitUtil);
    }

    protected BasePage(String url) {
        this();
        driver.get(url);
    }

    public WebDriver getDriver() {
        return driver;
    }

    protected SeleniumWaitUtil getWait() {
        return seleniumWaitUtil;
    }

    protected SeleniumOprationsUtil getOpration() {
        return seleniumOprationsUtil;
    }

    /**
     * Opens {@code link} in a new tab and returns a page for that tab. The original
     * window
     * handle is stored on {@code this} and copied to the returned instance so
     * {@link #closeTab()}
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

    public void closeTab() {
        driver.close();
        driver.switchTo().window(tabHandler);
    }

}
