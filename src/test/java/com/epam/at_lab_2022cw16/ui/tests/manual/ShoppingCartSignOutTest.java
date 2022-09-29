package com.epam.at_lab_2022cw16.ui.tests.manual;

import com.epam.at_lab_2022cw16.annotations.JiraTicketsLink;
import com.epam.at_lab_2022cw16.ui.constants.Constants.AlertMessageTexts;
import com.epam.at_lab_2022cw16.ui.model.User;
import com.epam.at_lab_2022cw16.ui.page.AuthenticationPage;
import com.epam.at_lab_2022cw16.ui.page.MyAccountPage;
import com.epam.at_lab_2022cw16.ui.page.MyStoreHomepage;
import com.epam.at_lab_2022cw16.ui.page.OrderSummaryPage;
import com.epam.at_lab_2022cw16.ui.page.WomenCatalogPage;
import com.epam.at_lab_2022cw16.ui.page.pageElements.Alert;
import com.epam.at_lab_2022cw16.ui.utils.TestListener;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

@JiraTicketsLink(id = 16293,
        description = "Shopping cart page test",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16293")
@ExtendWith(TestListener.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShoppingCartSignOutTest extends AbstractBaseTest {

    private final WebDriver driver = getWebDriver();
    private final User validUser = new User("rescue-rangers@mail.com", "010203");

    @Test
    @Order(1)
    public void openHomePageTest() {
        MyStoreHomepage homePage = new MyStoreHomepage(driver).openPage();
        assertThat(homePage.isPageTitleValid()).isTrue();
    }

    @Test
    @Order(2)
    public void openAuthenticationPageTest() {
        AuthenticationPage authPage = new MyStoreHomepage(driver).clickSignInButton();
        assertThat(authPage.isPageTitleValid()).isTrue();
        assertThat(authPage.isCreateAccountFormVisible()).isTrue();
        assertThat(authPage.isLoginFormVisible()).isTrue();
    }

    @Test
    @Order(3)
    public void signInWithEmptyCredentialsTest() {
        AuthenticationPage authPage = new AuthenticationPage(driver);
        authPage.proceedToMyAccountPage();
        Alert alert = authPage.getPageElementAlert();
        assertThat(alert.isDisplayed()).isTrue();
        assertThat(alert.isDanger()).isTrue();
        assertThat(alert.getMessage()).contains(AlertMessageTexts.EMAIL_REQUIRED);
    }

    @Test
    @Order(4)
    public void signInWithNotRegisteredEmailTest() {
        User user = new User("rescuerangers@mail.com", "010203");
        AuthenticationPage authPage = new AuthenticationPage(driver);
        authPage.inputEmail(user.getUsername());
        authPage.inputPassword(user.getPassword());
        authPage.proceedToMyAccountPage();
        Alert alert = authPage.getPageElementAlert();
        assertThat(alert.isDisplayed()).isTrue();
        assertThat(alert.isDanger()).isTrue();
        assertThat(alert.getMessage()).contains(AlertMessageTexts.AUTH_FAIL);
    }

    @Test
    @Order(5)
    public void signInWithWrongPassTest() {
        User user = new User("rescue-rangers@mail.com", "0102030");
        AuthenticationPage authPage = new AuthenticationPage(driver);
        authPage.inputEmail(user.getUsername());
        authPage.inputPassword(user.getPassword());
        authPage.proceedToMyAccountPage();
        Alert alert = authPage.getPageElementAlert();
        assertThat(alert.isDisplayed()).isTrue();
        assertThat(alert.isDanger()).isTrue();
        assertThat(alert.getMessage()).contains(AlertMessageTexts.AUTH_FAIL);
    }

    @Test
    @Order(6)
    public void signInTest() {
        AuthenticationPage authPage = new AuthenticationPage(driver);
        authPage.inputEmail(validUser.getUsername());
        authPage.inputPassword(validUser.getPassword());
        MyAccountPage myAccountPage = authPage.proceedToMyAccountPage();
        assertThat(myAccountPage.isPageTitleValid()).isTrue();
    }

    @Test
    @Order(7)
    public void addItemToCart() {
        WomenCatalogPage womenCatalogPage = new MyAccountPage(driver).clickWomenCatalogButton();
        womenCatalogPage.addOneItemToCart();
        assertThat(womenCatalogPage.isProductAddedTitleVisible()).isTrue();
    }

    @Test
    @Order(8)
    public void openTheCartTest() {
        WomenCatalogPage womenCatalogPage = new WomenCatalogPage(driver);
        OrderSummaryPage orderSummaryPage = womenCatalogPage.proceedToCheckout();
        assertThat(orderSummaryPage.isPageTitleValid()).isTrue();
        assertThat(orderSummaryPage.isProductVisible()).isTrue();
    }

    @Test
    @Order(9)
    public void signOutTest() {
        OrderSummaryPage orderSummaryPage = new OrderSummaryPage(driver);
        orderSummaryPage.clickSignOutButton();
        assertThat(orderSummaryPage.getAlertMessage())
                .isEqualTo(AlertMessageTexts.EMPTY_CART_TEXT);
        assertThat(orderSummaryPage.isAccountVisible()).isFalse();
    }

    @Test
    @Order(10)
    public void signInAfterSignOutTest() {
        OrderSummaryPage orderSummaryPage = new OrderSummaryPage(driver);
        AuthenticationPage authPage = orderSummaryPage.clickSignInButton();
        authPage.inputEmail(validUser.getUsername());
        authPage.inputPassword(validUser.getPassword());
        MyAccountPage myAccountPage = authPage.proceedToMyAccountPage();
        assertThat(myAccountPage.isPageTitleValid()).isTrue();
    }

    @Test
    @Order(10)
    public void goToCartTest() {
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        assertThat(myAccountPage.isMiniCartEmpty()).isTrue();
        OrderSummaryPage orderSummaryPage = myAccountPage.clickToMiniCart();
        assertThat(orderSummaryPage.getAlertMessage())
                .isEqualTo(AlertMessageTexts.EMPTY_CART_TEXT);
    }
}
