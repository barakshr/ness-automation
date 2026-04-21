package com.ness.automation.utils.generalUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public final class ScreenshotUtils {

    private static final Logger log = LogManager.getLogger(ScreenshotUtils.class);

    private ScreenshotUtils() {}

    /**
     * Captures a screenshot as a byte array.
     * Caller decides what to do with it (attach to Allure, write to disk, etc.)
     */
    public static byte[] capture(WebDriver driver) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            log.warn("Screenshot capture failed: {}", e.getMessage());
            return new byte[0];
        }
    }

    public static File captureAsFile(WebDriver driver) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        } catch (Exception e) {
            log.warn("Screenshot capture failed: {}", e.getMessage());
            return null;
        }
    }
}
