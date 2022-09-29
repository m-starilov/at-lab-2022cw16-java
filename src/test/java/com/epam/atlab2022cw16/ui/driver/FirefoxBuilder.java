package com.epam.atlab2022cw16.ui.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxBuilder extends AbstractBuilder {
    private FirefoxOptions firefoxOptions = new FirefoxOptions();

    public FirefoxBuilder() {
        driverVersion = "0.30.0";
    }

    @Override
    public WebDriver build() {
        WebDriver driver;
        WebDriverManager.firefoxdriver().driverVersion(driverVersion).setup();
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().window().setSize(windowsSize);
        return driver;
    }

    @Override
    public AbstractBuilder defaultConfig() {
        return this;
    }
}
