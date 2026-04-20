package com.ness.automation.base;

import com.ness.automation.core.DataReader;
import com.ness.automation.testng.listeners.DriverLifecycleListener;
import com.ness.automation.testng.listeners.TestListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

@Listeners({ DriverLifecycleListener.class, TestListener.class })
public abstract class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);

    @DataProvider(name = "scenarios", parallel = false)
    public static Object[][] scenarios() {
        return DataReader.loadScenarios().stream().map(s -> new Object[] { s }).toArray(Object[][]::new);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        log.info("─── Test setup ───");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        log.info("─── Test teardown ───");
    }
}
