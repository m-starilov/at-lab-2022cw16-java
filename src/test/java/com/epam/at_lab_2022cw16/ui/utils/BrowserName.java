package com.epam.at_lab_2022cw16.ui.utils;

public enum BrowserName {
    CHROME("chrome"),
    FIREFOX("firefox");
    private String text;

    BrowserName(String browser) {
        this.text = browser;
    }

    public static BrowserName fromString(String text) {
        for (BrowserName browserName : BrowserName.values()) {
            if (browserName.text.equalsIgnoreCase(text)) {
                return browserName;
            }
        }
        return null;
    }
}
