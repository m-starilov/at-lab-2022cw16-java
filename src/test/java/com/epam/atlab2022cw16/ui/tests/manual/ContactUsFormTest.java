package com.epam.atlab2022cw16.ui.tests.manual;

import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import com.epam.atlab2022cw16.ui.application.models.User;
import com.epam.atlab2022cw16.ui.application.constants.Constants.AlertMessageTexts;
import com.epam.atlab2022cw16.ui.application.constants.Constants;
import com.epam.atlab2022cw16.ui.application.pages.ContactUsPage;
import com.epam.atlab2022cw16.ui.application.pages.MyAccountPage;
import com.epam.atlab2022cw16.ui.application.pages.MyStoreHomepage;
import com.epam.atlab2022cw16.ui.application.modules.Alert;
import com.epam.atlab2022cw16.ui.utils.TestListener;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
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
@Tags({
        @Tag("ui"),
        @Tag("manual")
})
public class ContactUsFormTest extends AbstractBaseTest {

    private static final String EMAIL_ADDRESS = "mofrekoiquemma-6157@yopmail.com";
    private static final String MESSAGE = "Message";

    private final WebDriver driver = getWebDriver();

    @Test
    @Order(1)
    public void openHomepageTest() {
        MyStoreHomepage homepage = new MyStoreHomepage(driver).openPage();
        assertThat(homepage.isPageTitleValid()).isTrue();
    }

    @Test
    @Order(2)
    public void openContactUsPageTest() {
        ContactUsPage contactUsPage = new MyStoreHomepage(driver).openContactUsPage();
        assertThat(contactUsPage.isPageTitleValid()).isTrue();
    }

    @Test
    @Order(3)
    public void invalidEmailTest() {
        ContactUsPage contactUsPage = new ContactUsPage(driver).clickSendMessageButton();
        Alert alert = contactUsPage.getPageElementAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isDanger())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(AlertMessageTexts.CONTACT_US_PAGE_INVALID_EMAIL_MESSAGE);
    }

    @Test
    @Order(4)
    public void emptyMessageTest() {
        ContactUsPage contactUsPage = new ContactUsPage(driver).inputEmail(EMAIL_ADDRESS);
        contactUsPage.clickSendMessageButton();
        Alert alert = contactUsPage.getPageElementAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isDanger())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(AlertMessageTexts.CONTACT_US_PAGE_BLANK_MESSAGE);
    }

    @Test
    @Order(5)
    public void noSubjectSelectedTest() {
        ContactUsPage contactUsPage = new ContactUsPage(driver).inputEmail(EMAIL_ADDRESS);
        contactUsPage.inputMessage(MESSAGE)
                .clickSendMessageButton();
        Alert alert = contactUsPage.getPageElementAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isDanger())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(AlertMessageTexts.CONTACT_US_PAGE_NO_SUBJECT_MESSAGE);
    }

    @Test
    @Order(6)
    public void correctMessageTest() {
        Alert alert = new ContactUsPage(driver).inputEmail(EMAIL_ADDRESS)
                .inputMessage(MESSAGE)
                .selectSubjectHeading(Constants.CUSTOMER_SERVICE)
                .clickSendMessageButton().getPageElementAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isSuccess())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(AlertMessageTexts.CONTACT_US_PAGE_SUCCESS_MESSAGE);
    }

    @Test
    @Order(7)
    public void loginTest() {
        User user = User.create().setEmail("mofrekoiquemma-6157@yopmail.com").setPassword("12345").build();
        MyAccountPage myAccountPage = new MyStoreHomepage(driver).clickSignInButton()
                .inputEmail(user.getEmail())
                .inputPassword(user.getPassword()).proceedToMyAccountPage();
        assertThat(myAccountPage.isPageTitleValid()).isTrue();
    }

    @Test
    @Order(8)
    public void openContactUsPageAsAuthorizedUserTest() {
        ContactUsPage contactUsPage = new MyAccountPage(driver).openContactUsPage();
        assertThat(contactUsPage.isPageTitleValid()).isTrue();
    }

    @Test
    @Order(9)
    public void emptyMessageAsAuthorizedUserTest() {
        Alert alert =  new ContactUsPage(driver).inputEmail(EMAIL_ADDRESS)
                .clickSendMessageButton()
                .getPageElementAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isDanger())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(AlertMessageTexts.CONTACT_US_PAGE_BLANK_MESSAGE);
    }

    @Test
    @Order(10)
    public void noSubjectSelectedAsAuthorizedUserTest() {
        Alert alert = new ContactUsPage(driver).inputEmail(EMAIL_ADDRESS)
                .inputMessage(MESSAGE)
                .clickSendMessageButton().getPageElementAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isDanger())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(AlertMessageTexts.CONTACT_US_PAGE_NO_SUBJECT_MESSAGE);
    }

    @Test
    @Order(11)
    public void productFieldAsAuthorizedUserTest() {
        ContactUsPage contactUsPage = new ContactUsPage(driver).inputEmail(EMAIL_ADDRESS)
                .inputMessage(MESSAGE)
                .selectSubjectHeading(Constants.CUSTOMER_SERVICE)
                .selectOrderReference(1);
        assertThat(contactUsPage.isProductDropdownVisible())
                .isTrue();
    }

    @Test
    @Order(12)
    public void correctMessageAsAuthorizedUserTest() {
        Alert alert = new ContactUsPage(driver).inputEmail(EMAIL_ADDRESS)
                .inputMessage(MESSAGE)
                .selectSubjectHeading(Constants.CUSTOMER_SERVICE)
                .selectOrderReference(1)
                .clickSendMessageButton()
                .getPageElementAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isSuccess())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(AlertMessageTexts.CONTACT_US_PAGE_SUCCESS_MESSAGE);
    }
}
