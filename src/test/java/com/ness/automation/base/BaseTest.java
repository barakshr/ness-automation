package com.ness.automation.base;

import com.ness.automation.core.driver.DriverManager;
import com.ness.automation.testng.listeners.TestListener;
import com.ness.automation.testng.retry.RetryAnalyzerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({ TestListener.class, RetryAnalyzerFactory.class })
public abstract class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        log.info("─── Test setup ───");
        DriverManager.initDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        log.info("─── Test teardown ───");
        DriverManager.quitDriver();
    }
}
