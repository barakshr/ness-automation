package com.ness.automation.pages.components;

import com.ness.automation.core.driver.DriverManager;
import org.openqa.selenium.WebDriver;

public class PaginationComponent {

    protected final WebDriver driver;

    public PaginationComponent() {
        this.driver = DriverManager.getDriver();
    }

    // TODO: implement hasNext(), goToNext()
}
