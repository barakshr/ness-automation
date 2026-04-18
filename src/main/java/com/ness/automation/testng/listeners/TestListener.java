package com.ness.automation.testng.listeners;

import com.ness.automation.core.config.ConfigManager;
import com.ness.automation.core.driver.DriverManager;
import com.ness.automation.utils.AllureAttachments;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info("▶ Starting test: {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("✔ Passed: {}", result.getName());
        if (ConfigManager.SCREENSHOT_ON_EVERY_PAGE) {
            AllureAttachments.screenshot(DriverManager.getDriver(), "final-state");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("✘ Failed: {}", result.getName());
        if (ConfigManager.SCREENSHOT_ON_FAILURE) {
            try {
                AllureAttachments.screenshot(DriverManager.getDriver(), "failure-screenshot");
            } catch (Exception e) {
                log.warn("Could not capture failure screenshot: {}", e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("⚠ Skipped: {}", result.getName());
    }
}
