package com.ness.automation.core.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * Owns the per-thread {@link WebDriver} lifecycle.
 * <p>
 * {@code ThreadLocal} is used so that each TestNG thread (including
 * parallel {@code @DataProvider} rows) gets its own browser without
 * cross-contamination.
 * <p>
 * Usage:
 * <pre>
 *   // @BeforeMethod
 *   DriverManager.initDriver();
 *
 *   // anywhere
 *   WebDriver driver = DriverManager.getDriver();
 *
 *   // @AfterMethod
 *   DriverManager.quitDriver();
 * </pre>
 */
public final class DriverManager {

    private static final Logger log = LogManager.getLogger(DriverManager.class);

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
        // utility class; prevent instantiation
    }

    /** Creates a new driver for the current thread. Idempotent - ignored if already initialized. */
    public static void initDriver() {
        if (DRIVER.get() != null) {
            log.debug("Driver already initialized for thread '{}', skipping.",
                    Thread.currentThread().getName());
            return;
        }
        WebDriver driver = BrowserFactory.create();
        DRIVER.set(driver);
        log.info("Initialized driver for thread '{}'.", Thread.currentThread().getName());
    }

    /**
     * @return the driver for the current thread.
     * @throws IllegalStateException if {@link #initDriver()} has not been called.
     */
    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException(
                    "WebDriver not initialized for thread '"
                            + Thread.currentThread().getName()
                            + "'. Ensure BaseTest.@BeforeMethod has run.");
        }
        return driver;
    }

    /** Quits the driver for the current thread and removes it from the ThreadLocal. */
    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            return;
        }
        try {
            driver.quit();
        } catch (Exception e) {
            log.warn("Exception while quitting driver: {}", e.getMessage());
        } finally {
            DRIVER.remove();   // CRITICAL: avoid ThreadLocal memory leak
            log.info("Quit driver for thread '{}'.", Thread.currentThread().getName());
        }
    }
}
