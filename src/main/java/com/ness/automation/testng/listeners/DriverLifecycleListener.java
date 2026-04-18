package com.ness.automation.testng.listeners;

import com.ness.automation.core.config.ConfigManager;
import com.ness.automation.core.driver.DriverManager;
import com.ness.automation.utils.AllureAttachments;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

/**
 * Owns WebDriver lifecycle so init/quit align with TestNG invocation order.
 * Screenshots after each {@code @Test} run while the session is still alive, then {@link DriverManager#quitDriver()}.
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
            switch (testResult.getStatus()) {
                case ITestResult.FAILURE -> {
                    if (ConfigManager.SCREENSHOT_ON_FAILURE) {
                        AllureAttachments.screenshot(DriverManager.getDriver(), "failure-screenshot");
                    }
                }
                case ITestResult.SUCCESS -> {
                    if (ConfigManager.SCREENSHOT_ON_EVERY_PAGE) {
                        AllureAttachments.screenshot(DriverManager.getDriver(), "final-state");
                    }
                }
                default -> { /* no screenshot */ }
            }
        } catch (Exception e) {
            log.warn("Post-test screenshot failed: {}", e.getMessage());
        } finally {
            DriverManager.quitDriver();
        }
    }
}
