package com.ness.automation.base;

import com.ness.automation.core.DataReader;
import com.ness.automation.testng.listeners.DriverLifecycleListener;
import com.ness.automation.testng.listeners.TestListener;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

@Listeners({ DriverLifecycleListener.class, TestListener.class })
public abstract class BaseTest {

    @DataProvider(name = "scenarios", parallel = false)
    public static Object[][] scenarios() {
        return DataReader.loadScenarios().stream().map(s -> new Object[] { s }).toArray(Object[][]::new);
    }
}
