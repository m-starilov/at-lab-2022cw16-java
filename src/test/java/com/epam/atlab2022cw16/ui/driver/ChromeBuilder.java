package com.epam.atlab2022cw16.ui.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeBuilder extends AbstractBuilder {

    private ChromeOptions chromeOptions = new ChromeOptions();

    public ChromeBuilder() {
        driverVersion = "105.0.5195.52";
    }

    @Override
    public WebDriver build() {
        WebDriver driver;
        WebDriverManager.chromedriver().driverVersion(driverVersion).setup();
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().setSize(windowsSize);
        return driver;
    }

    @Override
    public AbstractBuilder defaultConfig() {
        chromeOptions.addArguments("disable-popup-blocking");
        chromeOptions.addArguments("disable-infobars");
        return this;
    }
}
