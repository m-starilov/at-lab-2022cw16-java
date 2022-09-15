package com.epam.at_lab_2022cw16.ui.tests.manual;

import com.epam.at_lab_2022cw16.ui.driver.WebDriverApp;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import java.time.Duration;


public abstract class AbstractBaseTest {

    protected static WebDriver driver;
    protected static final int WAIT_TIMEOUT_SECONDS = 30;

    @BeforeAll
    public static void setupDriver() {
        driver = new WebDriverApp()
                .getChrome()
                .defaultConfig()
                .windowsSize(new Dimension(1920, 1080))
                .build();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));
    }

    @AfterAll
    public static void browserClose() {
        driver.quit();
    }
}
