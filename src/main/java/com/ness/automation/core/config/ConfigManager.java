package com.ness.automation.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Immutable, JVM-wide configuration.
 * <p>
 * Values are loaded once from {@code config.properties} on the classpath
 * the first time this class is referenced. Field names mirror the property
 * keys so there is no mental translation between the file and the code:
 * <pre>
 *     aut.base.url        →  ConfigManager.AUT_BASE_URL
 *     browser.headless    →  ConfigManager.BROWSER_HEADLESS
 * </pre>
 */
public final class ConfigManager {

    private static final String CONFIG_FILE = "config.properties";

    // ─── Application Under Test ───
    public static final String AUT_BASE_URL;
    public static final String AUT_DEFAULT_LANGUAGE;

    // ─── Browser ───
    public static final String BROWSER_TYPE;
    public static final boolean BROWSER_HEADLESS;
    public static final boolean BROWSER_WINDOW_MAXIMIZE;

    // ─── Timeouts (seconds) ───
    public static final int TIMEOUT_IMPLICIT;
    public static final int TIMEOUT_EXPLICIT;
    public static final int TIMEOUT_PAGE_LOAD;
    public static final int TIMEOUT_MANUAL_INTERVENTION;

    // ─── Reporting ───
    public static final String REPORT_ALLURE_RESULTS_DIR;
    public static final boolean SCREENSHOT_ON_FAILURE;
    public static final boolean SCREENSHOT_ON_EVERY_PAGE;

    // ─── Test Data ───
    public static final String TESTDATA_SCENARIOS_PATH;

    static {
        Properties props = loadProperties();

        AUT_BASE_URL                = required(props, "aut.base.url");
        AUT_DEFAULT_LANGUAGE        = required(props, "aut.default.language");

        BROWSER_TYPE                = required(props, "browser.type");
        BROWSER_HEADLESS            = Boolean.parseBoolean(required(props, "browser.headless"));
        BROWSER_WINDOW_MAXIMIZE     = Boolean.parseBoolean(required(props, "browser.window.maximize"));

        TIMEOUT_IMPLICIT            = Integer.parseInt(required(props, "timeout.implicit"));
        TIMEOUT_EXPLICIT            = Integer.parseInt(required(props, "timeout.explicit"));
        TIMEOUT_PAGE_LOAD           = Integer.parseInt(required(props, "timeout.page.load"));
        TIMEOUT_MANUAL_INTERVENTION = Integer.parseInt(required(props, "timeout.manual.intervention"));

        REPORT_ALLURE_RESULTS_DIR   = required(props, "report.allure.results.dir");
        SCREENSHOT_ON_FAILURE       = Boolean.parseBoolean(required(props, "screenshot.on.failure"));
        SCREENSHOT_ON_EVERY_PAGE    = Boolean.parseBoolean(required(props, "screenshot.on.every.page"));

        TESTDATA_SCENARIOS_PATH     = required(props, "testdata.scenarios.path");
    }

    private ConfigManager() {
        // utility class; prevent instantiation
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream in = ConfigManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (in == null) {
                throw new IllegalStateException(
                        "Config file not found on classpath: " + CONFIG_FILE);
            }
            props.load(in);
            return props;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load config: " + CONFIG_FILE, e);
        }
    }

    private static String required(Properties props, String key) {
        String value = props.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing required config key '" + key + "' in " + CONFIG_FILE);
        }
        return value.trim();
    }
}
