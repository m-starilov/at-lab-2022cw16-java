package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.constants.Constants.Color;
import com.epam.at_lab_2022cw16.ui.model.User;
import com.epam.at_lab_2022cw16.ui.page.CreateAnAccountPage;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateAnAccountPageStepDefinitions {
    private final WebDriver driver = EnvironmentUtils.getDriver();
    private static final String expectedNumberOfErrorsAlertMessage = "There are 8 errors";
    private static final String invalidLastName = "lastname is invalid.";
    private static final String invalidFirstName = "firstname is invalid.";
    private static final String invalidMobile = "phone_mobile is invalid.";
    private static final String invalidZip = "The Zip/Postal code you've entered is invalid. It must follow this format: 00000";


    CreateAnAccountPage createAnAccountPage = new CreateAnAccountPage(driver);

    @Then("the email has been verified. “Create an account” page opened.")
    public void createAnAccountPageOpened() {
        Assertions.assertThat(createAnAccountPage.verifyPageTitle()).isTrue();
    }

    @When("the user clicks register button with empty fields in registration form")
    public void clickRegisterButtonWithEmptyFieldsInRegistrationForm() {
        createAnAccountPage.clickRegisterButton();
    }

    @Then("a message is displayed at the top of the form with a list of incorrectly filled fields. Number of fields in the list equals the number of required fields.")
    public void verifyErrorMessagePresence() {
        Assertions.assertThat(createAnAccountPage.isAlertMessageVisible()).isTrue();
        Assertions.assertThat(createAnAccountPage.registerErrorMessageText()).isEqualTo(expectedNumberOfErrorsAlertMessage);
    }

    @When("the user fills fields with invalid data")
    public void fillFieldsWithInvalidData(DataTable dataTable) {
        User invalidUser = new User();
        List<Map<String, String>> dataMap = dataTable.asMaps();
        invalidUser.setFirstName(dataMap.get(0).get("firstName"));
        invalidUser.setLastName(dataMap.get(0).get("lastName"));
        invalidUser.setBirthMonth(dataMap.get(0).get("birthMonth"));
        invalidUser.setBirthDay(dataMap.get(0).get("birthDay"));
        invalidUser.setBirthYear(dataMap.get(0).get("birthYear"));
        invalidUser.setPassword(dataMap.get(0).get("password"));
        invalidUser.setAddress(dataMap.get(0).get("address"));
        invalidUser.setCity(dataMap.get(0).get("city"));
        invalidUser.setPostalCode(dataMap.get(0).get("postalCode"));
        invalidUser.setMobilePhone(dataMap.get(0).get("mobilePhone"));
        createAnAccountPage.fillingRegisterForm(invalidUser);
    }

    @Then("the fields changed the color of the frame to red")
    public void verifyAlertMessagePresence() {
        assertThat(createAnAccountPage.getBorderColor()).isEqualTo(Color.RED_ALERT.getColorHex());
    }

    @And("А message is displayed at the top of the form with incorrectly filled fields.")
    public void messageIsDisplayedAtTheTopOfTheFormWithIncorrectlyFilledFields() {
        createAnAccountPage.clickRegisterButton();
        assertThat(createAnAccountPage.isAlertMessageVisible()).isTrue();
        assertThat(createAnAccountPage.invalidLastNameText()).isEqualTo(invalidLastName);
        assertThat(createAnAccountPage.invalidFirstNameText()).isEqualTo(invalidFirstName);
        assertThat(createAnAccountPage.invalidMobileText()).isEqualTo(invalidMobile);
        assertThat(createAnAccountPage.invalidZipText()).isEqualTo(invalidZip);
    }

    @When("the user fills fields with valid data")
    public void fillFieldsWithValidData(DataTable dataTable) {
        User validUser = new User();
        List<Map<String, String>> dataMap = dataTable.asMaps();
        validUser.setFirstName(dataMap.get(0).get("firstName"));
        validUser.setLastName(dataMap.get(0).get("lastName"));
        validUser.setBirthMonth(dataMap.get(0).get("birthMonth"));
        validUser.setBirthDay(dataMap.get(0).get("birthDay"));
        validUser.setBirthYear(dataMap.get(0).get("birthYear"));
        validUser.setPassword(dataMap.get(0).get("password"));
        validUser.setAddress(dataMap.get(0).get("address"));
        validUser.setCity(dataMap.get(0).get("city"));
        validUser.setPostalCode(dataMap.get(0).get("postalCode"));
        validUser.setMobilePhone(dataMap.get(0).get("mobilePhone"));
        createAnAccountPage.fillingRegisterForm(validUser);
        createAnAccountPage.clickRegisterButton();
    }
}
