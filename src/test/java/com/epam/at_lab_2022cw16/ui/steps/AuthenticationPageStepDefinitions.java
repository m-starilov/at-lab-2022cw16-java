package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.model.User;
import com.epam.at_lab_2022cw16.ui.page.AuthenticationPage;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

public class AuthenticationPageStepDefinitions {

    private final WebDriver driver = EnvironmentUtils.getDriver();

    @When("I log in with email {string} and password {string}")
    public void login(String email, String password) {
        User user = new User(email, password);
        AuthenticationPage authenticationPage = new AuthenticationPage(driver).inputEmail(user.getUsername());
        authenticationPage.inputPassword(user.getPassword());
        authenticationPage.proceedToMyAccountPage();
    }
}
