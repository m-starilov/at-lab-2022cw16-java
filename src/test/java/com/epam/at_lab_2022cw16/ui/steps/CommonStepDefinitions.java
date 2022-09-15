package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.tests.bdd.hooks.DriverHooks;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonStepDefinitions {

    protected static WebDriver driver = DriverHooks.getWebDriver();

    @Then("I see {string} page title")
    public void checkPageTitle(String expectedTitle) {
        assertThat(driver.getTitle())
                .contains(expectedTitle);
    }
}
