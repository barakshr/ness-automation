package com.ness.automation.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public final class AllureAttachments {

    private AllureAttachments() {}

    public static void screenshot(WebDriver driver, String name) {
        byte[] bytes = ScreenshotUtils.capture(driver);
        if (bytes.length > 0) {
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(bytes), ".png");
        }
    }

    public static void text(String name, String content) {
        Allure.addAttachment(name, "text/plain",
                new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), ".txt");
    }

    public static void html(String name, String htmlContent) {
        Allure.addAttachment(name, "text/html",
                new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8)), ".html");
    }
}
