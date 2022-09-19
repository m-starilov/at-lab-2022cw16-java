package com.epam.at_lab_2022cw16.ui.tests.bdd.hooks;

import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

@Log4j2
public class ScreenshotHook {

    @After(order = 1)
    public void saveScreenshot(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) EnvironmentUtils.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
    }
}
