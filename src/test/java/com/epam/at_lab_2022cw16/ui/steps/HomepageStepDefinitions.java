package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.page.MyStoreHomepage;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

public class HomepageStepDefinitions {

    private final WebDriver driver = EnvironmentUtils.getDriver();

    @When("I open Home Page")
    public void openHomepage() {
        new MyStoreHomepage(driver).openPage();
    }

    @When("I click to Sign in button")
    public void signIn() {
        new MyStoreHomepage(driver).clickSignInButton();
    }

    @And("I click to My account button")
    public void clickToMyAccountButton() {
        new MyStoreHomepage(driver).clickMyAccountButton();
    }
}
