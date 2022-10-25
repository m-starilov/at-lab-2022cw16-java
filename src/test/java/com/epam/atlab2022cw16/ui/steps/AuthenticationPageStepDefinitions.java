package com.epam.atlab2022cw16.ui.steps;

import com.epam.atlab2022cw16.ui.application.constants.Constants;
import com.epam.atlab2022cw16.ui.application.models.User;
import com.epam.atlab2022cw16.ui.application.modules.Alert;
import com.epam.atlab2022cw16.ui.application.pages.AuthenticationPage;
import com.epam.atlab2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;

import static com.epam.atlab2022cw16.ui.utils.RandomEmailCreator.generateRandomEmail;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthenticationPageStepDefinitions {

    private final WebDriver driver = EnvironmentUtils.getDriver();

    @When("I log in with email {string} and password {string}")
    public void iLogInWithValidEmailAddressAndPassword(String email, String password) {
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

    @When("the user enters validEmail in the “Email address” field and press the “Create an account” button")
    public void theUserEntersValidEmailInTheEmailAddressFieldAndPressTheCreateAnAccountButton() {
        new AuthenticationPage(driver).fillingEmailForm(generateRandomEmail());
    }

    @Then("I see a create account form")
    public void iSeeACreateAccountForm() {
        assertTrue(new AuthenticationPage(driver).isCreateAccountFormVisible());
    }

    @Then("I see an already registered form")
    public void iSeeAnAlreadyRegisteredForm() {
        assertTrue(new AuthenticationPage(driver).isLoginFormVisible());
    }


    @Then("I see An email address required message")
    public void iSeeAnEmailAddressRequiredMessage() {
        Alert alert = new AuthenticationPage(driver).getPageElementAlert();
        assertTrue(alert.isDisplayed());
        assertTrue(alert.isDanger());
        assertTrue(alert.getMessage().contains(Constants.AlertMessageTexts.EMAIL_REQUIRED));
    }

    @Then("I see Authentication failed message")
    public void iSeeAuthenticationFailedMessage() {
        Alert alert = new AuthenticationPage(driver).getPageElementAlert();
        assertTrue(alert.isDisplayed());
        assertTrue(alert.isDanger());
        assertTrue(alert.getMessage().contains(Constants.AlertMessageTexts.AUTH_FAIL));
    }

    @When("I click Proceed to My Account button")
    public void login() {
        AuthenticationPage authenticationPage = new AuthenticationPage(driver);
        authenticationPage.proceedToMyAccountPage();
    }
}
