package com.ness.automation.testng.listeners;

import com.ness.automation.core.config.ConfigManager;
import com.ness.automation.core.driver.DriverManager;
import com.ness.automation.core.report.AllureAttachments;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

/**
 * Owns WebDriver lifecycle so init/quit align with TestNG invocation order.
 * Optionally captures a failure screenshot, then {@link DriverManager#quitDriver()} after each {@code @Test}.
 */
public final class DriverLifecycleListener implements IInvokedMethodListener {

    private static final Logger log = LogManager.getLogger(DriverLifecycleListener.class);

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isConfigurationMethod() && method.getTestMethod().isBeforeMethodConfiguration()) {
            DriverManager.initDriver();
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!method.isTestMethod()) {
            return;
        }
        try {
            if (testResult.getStatus() == ITestResult.FAILURE
                    && ConfigManager.SCREENSHOT_ON_FAILURE) {
                AllureAttachments.screenshot(DriverManager.getDriver(), "failure-screenshot");
            }
        } catch (Exception e) {
            log.warn("Post-test screenshot failed: {}", e.getMessage());
        } finally {
            DriverManager.quitDriver();
        }
    }
}
