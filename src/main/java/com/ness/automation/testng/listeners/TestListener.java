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
        log.info("▶ Starting test: {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("✔ Passed: {}", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("✘ Failed: {}", result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("⚠ Skipped: {}", result.getName());
    }
}
