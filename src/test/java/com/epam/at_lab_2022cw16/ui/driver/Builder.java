package com.epam.at_lab_2022cw16.ui.driver;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

public interface Builder {
    WebDriver build();

    Builder defaultConfig();

    Builder windowsSize(Dimension dimension);

    Builder driverVersion(String version);
}

