package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.page.NewUserRegisterPage;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;

public class NewRegisterAccountPageStepDefinition {
    private final WebDriver driver = EnvironmentUtils.getDriver();
    NewUserRegisterPage newUserRegisterPage = new NewUserRegisterPage(driver);

    @When("the user opens new user registration page")
    public void openPage() {
        newUserRegisterPage.openPage();
    }

    @When("the user enters {string} in the “Email address” field and press the “Create an account” button")
    public void enterEmail(String email) {
        newUserRegisterPage.fillingEmailForm(email);
    }

    @Then("the email has not been verified. “Invalid email address.” is displayed.")
    public void verifyInvalidEmailAddressErrorPresence() {
        Assertions.assertThat(newUserRegisterPage.isErrorMessageVisible()).isTrue();
    }
}
