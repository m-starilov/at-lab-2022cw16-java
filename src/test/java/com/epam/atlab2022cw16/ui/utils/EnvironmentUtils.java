package com.epam.atlab2022cw16.ui.utils;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;

@Log4j2
public class EnvironmentUtils {

    private static final ThreadLocal<WebDriver> localDriver = new ThreadLocal<>();

    public static BrowserName getBrowserName() {
        log.info(System.getProperty("browser"));
        return BrowserName.fromString(System.getProperty("browser", "chrome"));
    }

    public static int getParallelTestCount() {
        return Integer.parseInt(System.getProperty("project.tests.parallel.count", "1"));
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
