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
        Assertions.assertThat(createAnAccountPage.isPageTitleValid()).isTrue();
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
        List<Map<String, String>> dataMap = dataTable.asMaps();
        User invalidUser = User.create()
                .setFirstName(dataMap.get(0).get("firstName"))
                .setLastName(dataMap.get(0).get("lastName"))
                .setBirthMonth(dataMap.get(0).get("birthMonth"))
                .setBirthDay(dataMap.get(0).get("birthDay"))
                .setBirthYear(dataMap.get(0).get("birthYear"))
                .setPassword(dataMap.get(0).get("password"))
                .setAddress(dataMap.get(0).get("address"))
                .setCity(dataMap.get(0).get("city"))
                .setPostalCode(dataMap.get(0).get("postalCode"))
                .setMobilePhone(dataMap.get(0).get("mobilePhone")).build();
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
        List<Map<String, String>> dataMap = dataTable.asMaps();
        User validUser = User.create()
                .setFirstName(dataMap.get(0).get("firstName"))
                .setLastName(dataMap.get(0).get("lastName"))
                .setBirthMonth(dataMap.get(0).get("birthMonth"))
                .setBirthDay(dataMap.get(0).get("birthDay"))
                .setBirthYear(dataMap.get(0).get("birthYear"))
                .setPassword(dataMap.get(0).get("password"))
                .setAddress(dataMap.get(0).get("address"))
                .setCity(dataMap.get(0).get("city"))
                .setPostalCode(dataMap.get(0).get("postalCode"))
                .setMobilePhone(dataMap.get(0).get("mobilePhone")).build();
        createAnAccountPage.fillingRegisterForm(validUser);
        createAnAccountPage.clickRegisterButton();
    }
}
