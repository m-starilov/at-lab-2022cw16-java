package com.epam.atlab2022cw16.ui.hooks;

import com.epam.atlab2022cw16.ui.driver.WebDriverApp;
import com.epam.atlab2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import java.util.Optional;

public class DriverHooks {

    @Before
    public static void setupBrowser() {
        WebDriver driver = new WebDriverApp()
                .systemPropertyBrowser()
                .defaultConfig()
                .windowsSize(new Dimension(1920, 1080))
                .build();
        EnvironmentUtils.setDriver(driver);
    }

    @After(order = 0)
    public static void closeBrowser() {
        Optional
                .ofNullable(EnvironmentUtils.getDriver())
                .ifPresent(webDriver -> {
                    webDriver.quit();
                    EnvironmentUtils.removeDriver();
                });
    }
}
