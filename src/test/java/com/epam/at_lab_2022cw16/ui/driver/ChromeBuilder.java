package com.epam.at_lab_2022cw16.ui.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeBuilder implements Builder {

    private Dimension windowsSize;
    private ChromeOptions chromeOptions = new ChromeOptions();
    private String driverVersion;

    @Override
    public WebDriver build() {
        WebDriver driver;
        WebDriverManager.chromedriver().driverVersion(driverVersion).setup();
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().setSize(windowsSize);
        return driver;
    }

    @Override
    public Builder defaultConfig() {
        chromeOptions.addArguments("disable-popup-blocking");
        chromeOptions.addArguments("disable-infobars");
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
