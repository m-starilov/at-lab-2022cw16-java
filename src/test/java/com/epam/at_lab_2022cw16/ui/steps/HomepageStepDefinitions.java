package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.page.MyStoreHomepage;
import com.epam.at_lab_2022cw16.ui.tests.bdd.hooks.DriverHooks;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

public class HomepageStepDefinitions {

    private final WebDriver driver = DriverHooks.getWebDriver();

    @When("I open Home Page")
    public void openHomepage() {
        new MyStoreHomepage(driver).openPage();
    }

    @When("I click to Sign in button")
    public void signIn() {
        new MyStoreHomepage(driver).clickSignInButton();
    }
}
