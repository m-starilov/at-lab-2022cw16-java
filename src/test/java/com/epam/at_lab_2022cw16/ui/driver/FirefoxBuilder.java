package com.epam.at_lab_2022cw16.ui.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxBuilder implements Builder {
    private Dimension windowsSize;
    private FirefoxOptions firefoxOptions = new FirefoxOptions();
    private String driverVersion;

    @Override
    public WebDriver build() {
        WebDriver driver;
        WebDriverManager.firefoxdriver().driverVersion(driverVersion).setup();
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().window().setSize(windowsSize);
        return driver;
    }

    @Override
    public Builder defaultConfig() {
        return this;
    }

    @Override
    public Builder windowsSize(Dimension dimension) {
        windowsSize = dimension;
        return this;
    }

    @Override
    public Builder driverVersion(String version) {
        driverVersion = version;
        return this;
    }
}
