package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.model.User;
import com.epam.at_lab_2022cw16.ui.page.AuthenticationPage;
import com.epam.at_lab_2022cw16.ui.tests.bdd.hooks.DriverHooks;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

public class AuthenticationPageStepDefinitions {

    protected static WebDriver driver = DriverHooks.getWebDriver();

    private final User user = new User("mofrekoiquemma-6157@yopmail.com", "12345");

    @When("I enter correct credentials to Authentication form")
    public void login() {
        AuthenticationPage authenticationPage = new AuthenticationPage(driver).inputEmail(user.getUsername());
        authenticationPage.inputPassword(user.getPassword());
        authenticationPage.proceedToMyAccountPage();
    }
}
