package com.epam.at_lab_2022cw16.ui.tests.manual;

import com.epam.at_lab_2022cw16.ui.model.User;
import com.epam.at_lab_2022cw16.ui.page.CreateAnAccountPage;
import com.epam.at_lab_2022cw16.ui.page.MyRegisteredAccountPage;
import com.epam.at_lab_2022cw16.ui.page.NewUserRegisterPage;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import com.epam.at_lab_2022cw16.ui.utils.TestListener;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.epam.at_lab_2022cw16.ui.utils.RandomEmailCreator.generateRandomEmail;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(TestListener.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NewUserRegistrationTests extends AbstractBaseTest {

    @Test
    @Order(1)
    public void createAccountWithInvalidEmail() {
        NewUserRegisterPage newUserRegistrationTests = new NewUserRegisterPage(EnvironmentUtils.getDriver());
        newUserRegistrationTests.openPage().fillingEmailForm("kfhvifdjhkf");
        assertThat(newUserRegistrationTests.isErrorMessageVisible()).isTrue();
    }

    @Test
    @Order(2)
    public void createAccountWithValidEmail() {
        NewUserRegisterPage newUserRegistrationTests = new NewUserRegisterPage(EnvironmentUtils.getDriver());
        CreateAnAccountPage createAnAccountPage = new CreateAnAccountPage(EnvironmentUtils.getDriver());
        newUserRegistrationTests.fillingEmailForm(generateRandomEmail());
        assertThat(createAnAccountPage.isCreateAccountHeaderVisible()).isTrue();
    }

    @Test
    @Order(3)
    public void registerWithEmptyFields() {
        CreateAnAccountPage createAnAccountPage = new CreateAnAccountPage(EnvironmentUtils.getDriver());
        createAnAccountPage.clickRegisterButton();
        assertThat(createAnAccountPage.registerErrorMessageText()).isEqualTo("There are 8 errors");
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
        assertThat(createAnAccountPage.getBorderColor()).isEqualTo("#f13340");
        createAnAccountPage.clickRegisterButton();
        assertThat(createAnAccountPage.isAlertMessageVisible()).isTrue();
        assertThat(createAnAccountPage.invalidLastNameText()).isEqualTo("lastname is invalid.");
        assertThat(createAnAccountPage.invalidFirstNameText()).isEqualTo("firstname is invalid.");
        assertThat(createAnAccountPage.invalidMobileText()).isEqualTo("phone_mobile is invalid.");
        assertThat(createAnAccountPage.invalidZipText()).isEqualTo("The Zip/Postal code you've entered is invalid. It must follow this format: 00000");
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

