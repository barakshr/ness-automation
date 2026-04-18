package com.ness.automation.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ness.automation.base.BaseTest;
import com.ness.automation.core.DataReader;
import com.ness.automation.pages.HomePage;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

@Epic("Ness Amazon Automation")
@Feature("Shopping flow")
public class ShoppingFlowTest extends BaseTest {

    @DataProvider(name = "scenarios", parallel = false)
    public Object[][] scenarios() {
        return DataReader.loadScenarios()
                .stream()
                .map(s -> new Object[] { s })
                .toArray(Object[][]::new);
    }

    // @Test(dataProvider = "scenarios")
    @Test
    @Description("Open home page and navigate to search")
    // public void shouldNotExceedBudget(TestScenario scenario)
    public void shouldNotExceedBudget() {
        new HomePage()
                .open()
                .goToSearch();
    }
}
