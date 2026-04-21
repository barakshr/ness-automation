package com.ness.automation.testng.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Test lifecycle logging. Driver and Allure screenshots are handled by {@link DriverLifecycleListener}.
 */
public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info("▶ Starting test: {}", displayName(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("✔ Passed: {}", displayName(result));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("✘ Failed: {}", displayName(result));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("⚠ Skipped: {}", displayName(result));
    }

    private static String displayName(ITestResult result) {
        String custom = result.getTestName();
        if (custom != null && !custom.isEmpty()) {
            return custom;
        }
        return result.getName();
    }
}
