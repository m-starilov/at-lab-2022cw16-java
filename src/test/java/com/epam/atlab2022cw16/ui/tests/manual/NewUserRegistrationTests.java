package com.epam.atlab2022cw16.ui.tests.manual;

import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import com.epam.atlab2022cw16.ui.application.constants.Constants;
import com.epam.atlab2022cw16.ui.application.models.User;
import com.epam.atlab2022cw16.ui.application.pages.AuthenticationPage;
import com.epam.atlab2022cw16.ui.application.pages.CreateAnAccountPage;
import com.epam.atlab2022cw16.ui.application.pages.MyAccountPage;
import com.epam.atlab2022cw16.ui.utils.EnvironmentUtils;
import com.epam.atlab2022cw16.ui.utils.TestListener;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.epam.atlab2022cw16.ui.utils.RandomEmailCreator.generateRandomEmail;
import static org.assertj.core.api.Assertions.assertThat;

@JiraTicketsLink(id = 16292,
        description = "New user registration test (requested fields only)",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16292")
@ExtendWith(TestListener.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tags({
        @Tag("ui"),
        @Tag("manual")
})
public class NewUserRegistrationTests extends AbstractBaseTest {

    @Test
    @Order(1)
    public void createAccountWithInvalidEmail() {
        AuthenticationPage newUserRegistrationTests = new AuthenticationPage(EnvironmentUtils.getDriver());
        newUserRegistrationTests.openPage().fillingEmailForm("kfhvifdjhkf");
        assertThat(newUserRegistrationTests.isErrorMessageVisible()).isTrue();
    }

    @Test
    @Order(2)
    public void createAccountWithValidEmail() {
        AuthenticationPage newUserRegistrationTests = new AuthenticationPage(EnvironmentUtils.getDriver());
        CreateAnAccountPage createAnAccountPage = new CreateAnAccountPage(EnvironmentUtils.getDriver());
        newUserRegistrationTests.fillingEmailForm(generateRandomEmail());
        assertThat(createAnAccountPage.isPageTitleValid()).isTrue();
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
        User invalidUser = User.create()
                .setFirstName("123-dfd")
                .setLastName("Sark-isy@an")
                .setBirthMonth("05")
                .setBirthDay("12")
                .setBirthYear("2005")
                .setPassword("-------")
                .setAddress("dfdfdfds")
                .setCity("fdfdgfd")
                .setPostalCode("3423")
                .setMobilePhone("er4q2").build();
        createAnAccountPage.fillingRegisterForm(invalidUser);
        assertThat(createAnAccountPage.getBorderColor()).isEqualTo(Constants.Color.RED_ALERT.getColorHex());
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
        MyAccountPage myRegisteredAccountPage = new MyAccountPage(EnvironmentUtils.getDriver());
        User validUser = User.create()
                .setFirstName("Josslen")
                .setLastName("Bomond")
                .setBirthDay("14")
                .setBirthMonth("07")
                .setBirthYear("1999")
                .setPassword("0123456789")
                .setAddress("Freedom str,12")
                .setCity("Alabama")
                .setPostalCode("00056")
                .setMobilePhone("012345346").build();

        createAnAccountPage.fillingRegisterForm(validUser);
        createAnAccountPage.clickRegisterButton();
        assertThat(myRegisteredAccountPage.isPageTitleValid())
                .isTrue();
    }
}

