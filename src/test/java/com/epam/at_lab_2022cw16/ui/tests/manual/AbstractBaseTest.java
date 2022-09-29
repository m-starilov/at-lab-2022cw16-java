package com.epam.at_lab_2022cw16.ui.tests.manual;

import com.epam.at_lab_2022cw16.ui.driver.WebDriverApp;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import java.util.Optional;

public abstract class AbstractBaseTest {

    @BeforeAll
    public static void openBrowser() {
        WebDriver driver = new WebDriverApp()
                .systemPropertyBrowser()
                .defaultConfig()
                .windowsSize(new Dimension(1920, 1080))
                .build();
        EnvironmentUtils.setDriver(driver);
    }

    @AfterAll
    public static void closeBrowser() {
        Optional
                .ofNullable(EnvironmentUtils.getDriver())
                .ifPresent(webDriver -> {
                    webDriver.quit();
                    EnvironmentUtils.removeDriver();
                });
    }

    protected static WebDriver getWebDriver() {
        return EnvironmentUtils.getDriver();
    }
}
