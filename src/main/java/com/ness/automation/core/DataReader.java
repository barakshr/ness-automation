package com.ness.automation.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ness.automation.core.config.ConfigManager;
import com.ness.automation.models.TestDataFile;
import com.ness.automation.models.TestScenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.List;

public final class DataReader {

    private static final Logger       log    = LogManager.getLogger(DataReader.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private DataReader() {}

    public static List<TestScenario> loadScenarios() {
        String path = ConfigManager.TESTDATA_SCENARIOS_PATH;
        log.info("Loading test scenarios from '{}'", path);
        try (InputStream in = DataReader.class.getClassLoader().getResourceAsStream(path)) {
            if (in == null) {
                throw new IllegalStateException("Test data file not found on classpath: " + path);
            }
            TestDataFile file = MAPPER.readValue(in, TestDataFile.class);
            List<TestScenario> scenarios = file.getScenarios();
            log.info("Loaded {} scenario(s)", scenarios.size());
            return scenarios;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load test scenarios from: " + path, e);
        }
    }
}
