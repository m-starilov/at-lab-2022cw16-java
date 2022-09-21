package com.epam.at_lab_2022cw16.ui.utils;

import org.openqa.selenium.WebDriver;

public class EnvironmentUtils {

    private static final ThreadLocal<WebDriver> localDriver = new ThreadLocal<>();

    public static BrowserName getBrowserName() {
        return BrowserName.fromString(System.getProperty("browser", "chrome"));
    }

    public static WebDriver getDriver() {
        return localDriver.get();
    }

    public static void setDriver(WebDriver driver) {
        localDriver.set(driver);
    }

    public static void removeDriver() {
        localDriver.remove();
    }

}
