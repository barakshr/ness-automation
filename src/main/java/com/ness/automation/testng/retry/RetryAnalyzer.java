package com.ness.automation.testng.retry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger log  = LogManager.getLogger(RetryAnalyzer.class);
    private static final int    MAX  = 1;   // retry once — hardcoded, not configurable
    private int count = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (count < MAX) {
            count++;
            log.warn("Retrying '{}' — attempt {}/{}", result.getName(), count, MAX);
            return true;
        }
        return false;
    }
}
