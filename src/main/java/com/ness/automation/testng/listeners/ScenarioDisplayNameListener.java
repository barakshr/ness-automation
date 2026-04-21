package com.ness.automation.testng.listeners;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.ness.automation.models.TestScenario;

import io.qameta.allure.Allure;

/**
 * Uses {@link TestScenario#getId()} as the display name for data-driven tests.
 * <ul>
 *   <li><b>TestNG</b> — {@link ITestResult#setTestName(String)} (HTML report, {@code testng-results.xml}, IDE).</li>
 *   <li><b>Allure</b> — {@link io.qameta.allure.AllureLifecycle#updateTestCase} in {@code beforeInvocation}, because
 *       {@code allure-testng} names cases from {@code @Description} / method name, not from TestNG's test name.</li>
 * </ul>
 * Registered before {@link TestListener} so lifecycle logs see the custom name.
 */
public class ScenarioDisplayNameListener implements ITestListener, IInvokedMethodListener {

    @Override
    public void onTestStart(ITestResult testResult) {
        String id = scenarioIdFrom(testResult.getParameters());
        if (id != null) {
            testResult.setTestName(id);
        }
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!method.isTestMethod()) {
            return;
        }
        String id = scenarioIdFrom(testResult.getParameters());
        if (id != null) {
            Allure.getLifecycle().updateTestCase(tr -> tr.setName(id));
        }
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
