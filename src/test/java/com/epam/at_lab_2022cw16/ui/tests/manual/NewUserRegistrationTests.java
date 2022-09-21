package com.epam.at_lab_2022cw16.ui.tests.manual;

import com.epam.at_lab_2022cw16.ui.model.User;
import com.epam.at_lab_2022cw16.ui.page.CreateAnAccountPage;
import com.epam.at_lab_2022cw16.ui.page.MyRegisteredAccountPage;
import com.epam.at_lab_2022cw16.ui.page.NewUserRegisterPage;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NewUserRegistrationTests extends AbstractBaseTest {
    private final String invalidEmail = "kfhvifdjhkf";
    private final String validEmail = "priffujautrezau-6876@yopmail.com";
    private final String expectedNumberOfErrorsAlertMessage = "There are 8 errors";
    private final String invalidLastName = "lastname is invalid.";
    private final String invalidFirstName = "firstname is invalid.";
    private final String invalidMobile = "phone_mobile is invalid.";
    private final String invalidZip = "The Zip/Postal code you've entered is invalid. It must follow this format: 00000";
    private final String hexColor = "#f13340";

    @Test
    @Order(1)
    public void createAccountWithInvalidEmail() {
        NewUserRegisterPage newUserRegistrationTests = new NewUserRegisterPage(EnvironmentUtils.getDriver());
        newUserRegistrationTests.openPage().fillingEmailForm(invalidEmail);
        assertThat(newUserRegistrationTests.isErrorMessageVisible()).isTrue();
    }

    @Test
    @Order(2)
    public void createAccountWithValidEmail() {
        NewUserRegisterPage newUserRegistrationTests = new NewUserRegisterPage(EnvironmentUtils.getDriver());
        CreateAnAccountPage createAnAccountPage = new CreateAnAccountPage(EnvironmentUtils.getDriver());
        newUserRegistrationTests.fillingEmailForm(validEmail);
        assertThat(createAnAccountPage.isCreateAccountHeaderVisible()).isTrue();
    }

    @Test
    @Order(3)
    public void registerWithEmptyFields() {
        CreateAnAccountPage createAnAccountPage = new CreateAnAccountPage(EnvironmentUtils.getDriver());
        createAnAccountPage.clickRegisterButton();
        assertThat(createAnAccountPage.registerErrorMessageText()).isEqualTo(expectedNumberOfErrorsAlertMessage);
    }

    @Test
    @Order(4)
    public void registerWithInvalidFields() {
        CreateAnAccountPage createAnAccountPage = new CreateAnAccountPage(EnvironmentUtils.getDriver());
        User invalidUser = new User();
        invalidUser.setFirstName("123-dfd");
        invalidUser.setLastName("Sark-isy@an");
        invalidUser.setBirthMonth("05");
        invalidUser.setBirthDay("12");
        invalidUser.setBirthYear("2005");
        invalidUser.setPassword("-------");
        invalidUser.setAddress("dfdfdfds");
        invalidUser.setCity("fdfdgfd");
        invalidUser.setPostalCode("3423");
        invalidUser.setMobilePhone("er4q2");

        createAnAccountPage.fillingRegisterForm(invalidUser);
        assertThat(createAnAccountPage.getBorderColor()).isEqualTo(hexColor);
        createAnAccountPage.clickRegisterButton();
        assertThat(createAnAccountPage.isAlertMessageVisible()).isTrue();
        assertThat(createAnAccountPage.invalidLastNameText()).isEqualTo(invalidLastName);
        assertThat(createAnAccountPage.invalidFirstNameText()).isEqualTo(invalidFirstName);
        assertThat(createAnAccountPage.invalidMobileText()).isEqualTo(invalidMobile);
        assertThat(createAnAccountPage.invalidZipText()).isEqualTo(invalidZip);
    }

    @Test
    @Order(5)
    public void registerWithValidFields() {
        CreateAnAccountPage createAnAccountPage = new CreateAnAccountPage(EnvironmentUtils.getDriver());
        MyRegisteredAccountPage myRegisteredAccountPage = new MyRegisteredAccountPage(EnvironmentUtils.getDriver());
        User validUser = new User();
        validUser.setFirstName("Josslen");
        validUser.setLastName("Bomond");
        validUser.setBirthDay("14");
        validUser.setBirthMonth("07");
        validUser.setBirthYear("1999");
        validUser.setPassword("0123456789");
        validUser.setAddress("Freedom str,12");
        validUser.setCity("Alabama");
        validUser.setPostalCode("00056");
        validUser.setMobilePhone("012345346");

        createAnAccountPage.fillingRegisterForm(validUser);
        createAnAccountPage.clickRegisterButton();
        assertThat(myRegisteredAccountPage.isPageOpened()).isTrue();
    }
}

