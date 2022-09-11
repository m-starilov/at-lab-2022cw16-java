package com.epam.at_lab_2022cw16.ui.tests.manual;

import com.epam.at_lab_2022cw16.ui.driver.WebDriverApp;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class CommonConditions {

    protected WebDriver driver;

    @BeforeClass(description = "browser setup")
    public void setupDriver() {
        driver = new WebDriverApp()
                .getChrome()
                .defaultConfig()
                .windowsSize(new Dimension(1920, 1080))
                .build();
    }

    @AfterClass(description = "Browser closing", alwaysRun = true)
    public void browserClose() {
        driver.quit();
    }
}
