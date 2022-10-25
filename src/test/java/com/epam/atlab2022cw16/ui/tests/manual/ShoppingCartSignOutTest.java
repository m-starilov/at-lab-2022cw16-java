package com.epam.atlab2022cw16.ui.tests.manual;

import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import com.epam.atlab2022cw16.ui.application.constants.Constants.AlertMessageTexts;
import com.epam.atlab2022cw16.ui.application.models.User;
import com.epam.atlab2022cw16.ui.application.pages.AuthenticationPage;
import com.epam.atlab2022cw16.ui.application.pages.MyAccountPage;
import com.epam.atlab2022cw16.ui.application.pages.MyStoreHomepage;
import com.epam.atlab2022cw16.ui.application.pages.OrderSummaryPage;
import com.epam.atlab2022cw16.ui.application.pages.WomenCatalogPage;
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

@JiraTicketsLink(id = 16293,
        description = "Shopping cart page test",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16293")
@ExtendWith(TestListener.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tags({
        @Tag("ui"),
        @Tag("manual")
})
public class ShoppingCartSignOutTest extends AbstractBaseTest {

    private final WebDriver driver = getWebDriver();
    private static User user;

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
        user = User.create().setEmail("rescuerangers@mail.com").setPassword("0102030").build();
        AuthenticationPage authPage = new AuthenticationPage(driver);
        authPage.inputEmail(user.getEmail());
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
        user = user.edit().setEmail("rescue-rangers@mail.com").build();
        AuthenticationPage authPage = new AuthenticationPage(driver);
        authPage.inputEmail(user.getEmail());
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
        user = user.edit().setPassword("010203").build();
        AuthenticationPage authPage = new AuthenticationPage(driver);
        authPage.inputEmail(user.getEmail());
        authPage.inputPassword(user.getPassword());
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
        assertThat(orderSummaryPage.getPageElementAlert().getMessage())
                .isEqualTo(AlertMessageTexts.EMPTY_CART_TEXT);
        assertThat(orderSummaryPage.isAccountVisible()).isFalse();
    }

    @Test
    @Order(10)
    public void signInAfterSignOutTest() {
        OrderSummaryPage orderSummaryPage = new OrderSummaryPage(driver);
        AuthenticationPage authPage = orderSummaryPage.clickSignInButton();
        authPage.inputEmail(user.getEmail());
        authPage.inputPassword(user.getPassword());
        MyAccountPage myAccountPage = authPage.proceedToMyAccountPage();
        assertThat(myAccountPage.isPageTitleValid()).isTrue();
    }

    @Test
    @Order(10)
    public void goToCartTest() {
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        assertThat(myAccountPage.isMiniCartEmpty()).isTrue();
        OrderSummaryPage orderSummaryPage = myAccountPage.clickToMiniCart();
        assertThat(orderSummaryPage.getPageElementAlert().getMessage())
                .isEqualTo(AlertMessageTexts.EMPTY_CART_TEXT);
    }
}
