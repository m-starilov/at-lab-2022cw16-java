package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.model.User;
import com.epam.at_lab_2022cw16.ui.page.AuthenticationPage;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthenticationPageStepDefinitions {

    private final WebDriver driver = EnvironmentUtils.getDriver();

    @When("I log in with email {string} and password {string}")
    public void login(String email, String password) {
        User user = User.create().setEmail(email).setPassword(password).build();
        new AuthenticationPage(driver)
                .inputEmail(user.getEmail())
                .inputPassword(user.getPassword())
                .proceedToMyAccountPage();
    }

    @Then("Authentication page is opened")
    public void authenticationPageIsOpenedTitleOfPageIs() {
        assertTrue(new AuthenticationPage(driver).isPageTitleValid());
    }

    @When("I log in with valid email {string} address and password {string}")
    public void iLogInWithValidEmailAddressAndPassword(String email, String password) {
        new AuthenticationPage(driver)
                .inputEmail(email)
                .inputPassword(password)
                .proceedToOrderAddressPage();
    }

    @When("the user opens new user registration page")
    public void openPage() {
        new AuthenticationPage(driver).openPage();
    }

    @When("the user enters {string} in the “Email address” field and press the “Create an account” button")
    public void enterEmail(String email) {
        new AuthenticationPage(driver).fillingEmailForm(email);
    }

    @Then("the email has not been verified. “Invalid email address.” is displayed.")
    public void verifyInvalidEmailAddressErrorPresence() {
        Assertions.assertThat(new AuthenticationPage(driver).isErrorMessageVisible()).isTrue();
    }
}
