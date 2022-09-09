package com.epam.at_lab_2022cw16.ui.utils;

public class EnvironmentUtils {
    public static BrowserName getBrowserName() {
       return BrowserName.fromString(System.getProperty("browser", "chrome"));
    }
}
