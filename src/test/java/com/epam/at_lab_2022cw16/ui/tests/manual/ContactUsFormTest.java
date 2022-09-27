package com.epam.at_lab_2022cw16.ui.tests.manual;

import com.epam.at_lab_2022cw16.annotations.JiraTicketsLink;
import com.epam.at_lab_2022cw16.ui.constants.AlertMessageTexts;
import com.epam.at_lab_2022cw16.ui.constants.ContactUsSubjects;
import com.epam.at_lab_2022cw16.ui.constants.PageTitles;
import com.epam.at_lab_2022cw16.ui.model.User;
import com.epam.at_lab_2022cw16.ui.page.AuthenticationPage;
import com.epam.at_lab_2022cw16.ui.page.ContactUsPage;
import com.epam.at_lab_2022cw16.ui.page.MyAccountPage;
import com.epam.at_lab_2022cw16.ui.page.MyStoreHomepage;
import com.epam.at_lab_2022cw16.ui.page.pageElements.Alert;
import com.epam.at_lab_2022cw16.ui.utils.TestListener;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

@JiraTicketsLink(id = 16323,
        description = "Test check Contact Us form",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16323")
@ExtendWith(TestListener.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContactUsFormTest extends AbstractBaseTest {

    private static final String EMAIL_ADDRESS = "mofrekoiquemma-6157@yopmail.com";
    private static final String MESSAGE = "Message";

    private final WebDriver driver = getWebDriver();

    @Test
    @Order(1)
    public void openHomepageTest() {
        MyStoreHomepage homepage = new MyStoreHomepage(driver).openPage();
        assertThat(homepage.getTitle())
                .isEqualTo(PageTitles.HOME.getPageTitle());
    }

    @Test
    @Order(2)
    public void openContactUsPageTest() {
        ContactUsPage contactUsPage = new MyStoreHomepage(driver).openContactUsPage();
        assertThat(contactUsPage.getTitle())
                .isEqualTo(PageTitles.CONTACT_US.getPageTitle());
    }

    @Test
    @Order(3)
    public void invalidEmailTest() {
        ContactUsPage contactUsPage = new ContactUsPage(driver).clickSendMessageButton();
        Alert alert = contactUsPage.getMessageSentAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isDanger())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(AlertMessageTexts.CONTACT_US_PAGE_INVALID_EMAIL_MESSAGE.getAlertMessageText());
    }

    @Test
    @Order(4)
    public void emptyMessageTest() {
        ContactUsPage contactUsPage = new ContactUsPage(driver).inputEmail(EMAIL_ADDRESS);
        contactUsPage.clickSendMessageButton();
        Alert alert = contactUsPage.getMessageSentAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isDanger())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(AlertMessageTexts.CONTACT_US_PAGE_BLANK_MESSAGE.getAlertMessageText());
    }

    @Test
    @Order(5)
    public void noSubjectSelectedTest() {
        ContactUsPage contactUsPage = new ContactUsPage(driver).inputEmail(EMAIL_ADDRESS);
        contactUsPage.inputMessage(MESSAGE);
        contactUsPage.clickSendMessageButton();
        Alert alert = contactUsPage.getMessageSentAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isDanger())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(AlertMessageTexts.CONTACT_US_PAGE_NO_SUBJECT_MESSAGE.getAlertMessageText());
    }

    @Test
    @Order(6)
    public void correctMessageTest() {
        ContactUsPage contactUsPage = new ContactUsPage(driver).inputEmail(EMAIL_ADDRESS);
        contactUsPage.inputMessage(MESSAGE);
        contactUsPage.selectSubjectHeading(ContactUsSubjects.CUSTOMER_SERVICE.getSubject());
        contactUsPage.clickSendMessageButton();
        Alert alert = contactUsPage.getMessageSentAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isSuccess())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(AlertMessageTexts.CONTACT_US_PAGE_SUCCESS_MESSAGE.getAlertMessageText());
    }

    @Test
    @Order(7)
    public void loginTest() {
        User user = new User("mofrekoiquemma-6157@yopmail.com", "12345");
        AuthenticationPage authenticationPage = new MyStoreHomepage(driver).clickSignInButton();
        authenticationPage.inputEmail(user.getUsername());
        authenticationPage.inputPassword(user.getPassword());
        MyAccountPage myAccountPage = authenticationPage.proceedToMyAccountPage();
        assertThat(myAccountPage.getTitle())
                .isEqualTo(PageTitles.MY_ACCOUNT.getPageTitle());
    }

    @Test
    @Order(8)
    public void openContactUsPageAsAuthorizedUserTest() {
        ContactUsPage contactUsPage = new MyAccountPage(driver).openContactUsPage();
        assertThat(contactUsPage.getTitle())
                .isEqualTo(PageTitles.CONTACT_US.getPageTitle());
    }

    @Test
    @Order(9)
    public void emptyMessageAsAuthorizedUserTest() {
        ContactUsPage contactUsPage = new ContactUsPage(driver).inputEmail(EMAIL_ADDRESS);
        contactUsPage.clickSendMessageButton();
        Alert alert = contactUsPage.getMessageSentAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isDanger())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(AlertMessageTexts.CONTACT_US_PAGE_BLANK_MESSAGE.getAlertMessageText());
    }

    @Test
    @Order(10)
    public void noSubjectSelectedAsAuthorizedUserTest() {
        ContactUsPage contactUsPage = new ContactUsPage(driver).inputEmail(EMAIL_ADDRESS);
        contactUsPage.inputMessage(MESSAGE);
        contactUsPage.clickSendMessageButton();
        Alert alert = contactUsPage.getMessageSentAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isDanger())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(AlertMessageTexts.CONTACT_US_PAGE_NO_SUBJECT_MESSAGE.getAlertMessageText());
    }

    @Test
    @Order(11)
    public void productFieldAsAuthorizedUserTest() {
        ContactUsPage contactUsPage = new ContactUsPage(driver).inputEmail(EMAIL_ADDRESS);
        contactUsPage.inputMessage(MESSAGE);
        contactUsPage.selectSubjectHeading(ContactUsSubjects.CUSTOMER_SERVICE.getSubject());
        contactUsPage.selectOrderReference(1);
        assertThat(contactUsPage.isProductDropdownVisible())
                .isTrue();
    }

    @Test
    @Order(12)
    public void correctMessageAsAuthorizedUserTest() {
        ContactUsPage contactUsPage = new ContactUsPage(driver).inputEmail(EMAIL_ADDRESS);
        contactUsPage.inputMessage(MESSAGE);
        contactUsPage.selectSubjectHeading(ContactUsSubjects.CUSTOMER_SERVICE.getSubject());
        contactUsPage.selectOrderReference(1);
        contactUsPage.clickSendMessageButton();
        Alert alert = contactUsPage.getMessageSentAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isSuccess())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(AlertMessageTexts.CONTACT_US_PAGE_SUCCESS_MESSAGE.getAlertMessageText());
    }
}
