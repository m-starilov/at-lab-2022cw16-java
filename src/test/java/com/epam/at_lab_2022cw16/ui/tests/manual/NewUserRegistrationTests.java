package com.epam.at_lab_2022cw16.ui.tests.manual;

import com.epam.at_lab_2022cw16.ui.constants.Constants.Color;
import com.epam.at_lab_2022cw16.ui.model.User;
import com.epam.at_lab_2022cw16.ui.page.AuthenticationPage;
import com.epam.at_lab_2022cw16.ui.page.CreateAnAccountPage;
import com.epam.at_lab_2022cw16.ui.page.MyAccountPage;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import com.epam.at_lab_2022cw16.ui.utils.TestListener;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(TestListener.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NewUserRegistrationTests extends AbstractBaseTest {
    private static final String invalidEmail = "kfhvifdjhkf";
    private static final String validEmail = "priffujautrezau-6876@yopmail.com";
    private static final String expectedNumberOfErrorsAlertMessage = "There are 8 errors";
    private static final String invalidLastName = "lastname is invalid.";
    private static final String invalidFirstName = "firstname is invalid.";
    private static final String invalidMobile = "phone_mobile is invalid.";
    private static final String invalidZip = "The Zip/Postal code you've entered is invalid. It must follow this format: 00000";


    @Test
    @Order(1)
    public void createAccountWithInvalidEmail() {
        AuthenticationPage newUserRegistrationTests = new AuthenticationPage(EnvironmentUtils.getDriver());
        newUserRegistrationTests.openPage().fillingEmailForm(invalidEmail);
        assertThat(newUserRegistrationTests.isErrorMessageVisible()).isTrue();
    }

    @Test
    @Order(2)
    public void createAccountWithValidEmail() {
        AuthenticationPage newUserRegistrationTests = new AuthenticationPage(EnvironmentUtils.getDriver());
        CreateAnAccountPage createAnAccountPage = new CreateAnAccountPage(EnvironmentUtils.getDriver());
        newUserRegistrationTests.fillingEmailForm(validEmail);
        assertThat(createAnAccountPage.isPageTitleValid()).isTrue();
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
        assertThat(createAnAccountPage.getBorderColor()).isEqualTo(Color.RED_ALERT.getColorHex());
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
        MyAccountPage myRegisteredAccountPage = new MyAccountPage(EnvironmentUtils.getDriver());
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
        assertThat(myRegisteredAccountPage.isPageTitleValid())
                .isTrue();
    }
}

