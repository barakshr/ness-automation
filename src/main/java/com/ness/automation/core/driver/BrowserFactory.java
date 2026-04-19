package com.ness.automation.core.driver;

import com.ness.automation.core.config.ConfigManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Knows <i>how</i> to build a {@link WebDriver} for the configured browser.
 * Stateless: every {@link #create()} call returns a fresh driver.
 * <p>
 * Lifecycle (when to start/stop the driver) is the responsibility of
 * {@link DriverManager}.
 */
public class BrowserFactory {

    public static WebDriver create() {
        WebDriver driver = switch (ConfigManager.BROWSER_TYPE.toLowerCase()) {
            case "chrome"  -> buildChrome();
            case "firefox" -> buildFirefox();
            case "edge"    -> buildEdge();
            default -> throw new IllegalArgumentException(
                    "Unsupported browser type: " + ConfigManager.BROWSER_TYPE);
        };

        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(ConfigManager.TIMEOUT_PAGE_LOAD));
        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(ConfigManager.TIMEOUT_IMPLICIT));

        if (ConfigManager.BROWSER_WINDOW_MAXIMIZE && !ConfigManager.BROWSER_HEADLESS) {
            driver.manage().window().maximize();
        }

        return AllureLoggingWebDriverListener.wrap(driver);
    }

    private static WebDriver buildChrome() {
        ChromeOptions options = new ChromeOptions();
        if (ConfigManager.BROWSER_HEADLESS) {
            options.addArguments("--headless=new");
        }
        // Pin language so Amazon doesn't redirect based on IP locale (decision #7).
        options.addArguments("--lang=" + ConfigManager.AUT_DEFAULT_LANGUAGE);

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("intl.accept_languages", ConfigManager.AUT_DEFAULT_LANGUAGE);
        options.setExperimentalOption("prefs", prefs);

        // Light anti-detection flags - Amazon is less aggressive than eBay but still
        // benefits from removing the most obvious automation fingerprints.
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        return new ChromeDriver(options);
    }

    private static WebDriver buildFirefox() {
        FirefoxOptions options = new FirefoxOptions();
        if (ConfigManager.BROWSER_HEADLESS) {
            options.addArguments("--headless");
        }
        options.addPreference("intl.accept_languages", ConfigManager.AUT_DEFAULT_LANGUAGE);
        return new FirefoxDriver(options);
    }

    private static WebDriver buildEdge() {
        EdgeOptions options = new EdgeOptions();
        if (ConfigManager.BROWSER_HEADLESS) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--lang=" + ConfigManager.AUT_DEFAULT_LANGUAGE);
        return new EdgeDriver(options);
    }
}
