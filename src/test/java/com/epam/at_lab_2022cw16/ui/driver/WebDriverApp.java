package com.epam.at_lab_2022cw16.ui.driver;

import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;

public class WebDriverApp {

    public ChromeBuilder getChrome() {
        return new ChromeBuilder();
    }

    public FirefoxBuilder getFirefox() {
        return new FirefoxBuilder();
    }

    public Builder systemPropertyBrowser() {

        switch (EnvironmentUtils.getBrowserName()) {
            case CHROME: {
                return new ChromeBuilder();
            }
            case FIREFOX: {
                return new FirefoxBuilder();
            }
        }
        return null;
    }

}
