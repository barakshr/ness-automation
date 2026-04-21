package com.ness.automation.testng.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.ness.automation.models.TestScenario;

import io.qameta.allure.Allure;


public class TestListener implements ITestListener, IInvokedMethodListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult result) {
        if (!method.isTestMethod()) {
            return;
        }
        String id = scenarioIdFrom(result.getParameters());
        if (id != null) {
            Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(id));
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        String id = scenarioIdFrom(result.getParameters());
        if (id != null) {
            result.setTestName(id);
        }
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

    private static String scenarioIdFrom(Object[] parameters) {
        if (parameters == null || parameters.length == 0) {
            return null;
        }
        if (parameters[0] instanceof TestScenario scenario) {
            String id = scenario.getId();
            if (id != null && !id.isEmpty()) {
                return id;
            }
        }
        return null;
    }
}
