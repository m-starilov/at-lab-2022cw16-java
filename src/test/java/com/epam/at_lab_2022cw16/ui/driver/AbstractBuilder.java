package com.epam.at_lab_2022cw16.ui.driver;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

public abstract class AbstractBuilder {
    protected Dimension windowsSize;
    protected String driverVersion;

    public abstract WebDriver build();

    public abstract AbstractBuilder defaultConfig();

    public AbstractBuilder windowsSize(Dimension dimension) {
        windowsSize = dimension;
        return this;
    }

    public AbstractBuilder driverVersion(String version) {
        driverVersion = version;
        return this;
    }
}

