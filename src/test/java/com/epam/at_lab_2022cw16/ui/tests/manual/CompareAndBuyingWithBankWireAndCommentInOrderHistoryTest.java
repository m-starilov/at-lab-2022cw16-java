package com.epam.at_lab_2022cw16.ui.tests.manual;

import com.epam.at_lab_2022cw16.ui.constants.PageTitles;
import com.epam.at_lab_2022cw16.ui.model.User;
import com.epam.at_lab_2022cw16.ui.page.AuthenticationPage;
import com.epam.at_lab_2022cw16.ui.page.ComparisonPage;
import com.epam.at_lab_2022cw16.ui.page.MyAccountPage;
import com.epam.at_lab_2022cw16.ui.page.MyStoreHomepage;
import com.epam.at_lab_2022cw16.ui.page.OrderAddressPage;
import com.epam.at_lab_2022cw16.ui.page.OrderBankWirePaymentPage;
import com.epam.at_lab_2022cw16.ui.page.OrderConfirmationPage;
import com.epam.at_lab_2022cw16.ui.page.OrderHistoryPage;
import com.epam.at_lab_2022cw16.ui.page.OrderPaymentPage;
import com.epam.at_lab_2022cw16.ui.page.OrderShippingPage;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(TestListener.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompareAndBuyingWithBankWireAndCommentInOrderHistoryTest extends AbstractBaseTest {

    private static final String TOTAL_ORDER_PRICE = "$59.96";
    private static final String USER_MESSAGE = "Please, send me photo of it!";
    private static final String ALERT_DANGER_MESSAGE = "The message cannot be blank.";
    private static final String ALERT_SUCCESS_MESSAGE = "Message successfully sent";
    private final WebDriver driver = getWebDriver();

    @Test
    @Order(1)
    public void openHomepageTest() {
        MyStoreHomepage homepage = new MyStoreHomepage(driver).openPage();
        assertThat(homepage.getTitle())
                .isEqualTo(PageTitles.HOMEPAGE_TITLE.getPageTitle());
    }

    @Test
    @Order(2)
    public void loginTest() {
        User user = new User("mofrekoiquemma-6157@yopmail.com", "12345");
        AuthenticationPage authenticationPage = new MyStoreHomepage(driver).clickSignInButton();
        authenticationPage.inputEmail(user.getUsername());
        authenticationPage.inputPassword(user.getPassword());
        MyAccountPage myAccountPage = authenticationPage.proceedToMyAccountPage();
        assertThat(myAccountPage.getTitle())
                .isEqualTo(PageTitles.MY_ACCOUNT_PAGE_TITLE.getPageTitle());
    }

    @Test
    @Order(3)
    public void addToComparisonTest() {
        WomenCatalogPage womenCatalogPage = new MyAccountPage(driver).clickWomenCatalogButton();
        womenCatalogPage.addToCompareItemByID(5);
        womenCatalogPage.addToCompareItemByID(6);
        ComparisonPage comparisonPage = womenCatalogPage.clickCompareButton();
        assertThat(comparisonPage.getNumberOfComparableItems())
                .isEqualTo(2);
    }

    @Test
    @Order(4)
    public void deleteFromComparisonTest() {
        ComparisonPage comparisonPage = new ComparisonPage(driver);
        comparisonPage.addToCartItemByID(5);
        comparisonPage.clickContinueShoppingButton();
        comparisonPage.deleteItemFromComparisonByID(5);
        comparisonPage.deleteItemFromComparisonByID(6);
        assertThat(comparisonPage.getNumberOfComparableItems())
                .isEqualTo(0);
    }

    @Test
    @Order(5)
    public void setProductQuantityTest() {
        OrderSummaryPage orderSummaryPage = new ComparisonPage(driver).clickViewSoppingCartButton();
        assertThat(orderSummaryPage.getSummaryProductsQuantity())
                .isEqualTo("1 Product");
        orderSummaryPage.setProductQuantity("qwe");
        assertThat(orderSummaryPage.getSummaryProductsQuantity())
                .isEqualTo("1 Product");
        orderSummaryPage.setProductQuantity("-9");
        assertThat(orderSummaryPage.getSummaryProductsQuantity())
                .isEqualTo("1 Product");
        orderSummaryPage.setProductQuantity("2");
        assertThat(orderSummaryPage.getSummaryProductsQuantity())
                .isEqualTo("2 Products");
    }

    @Test
    @Order(6)
    public void shippingAgreementCheckboxTest() {
        OrderAddressPage orderAddressPage = new OrderSummaryPage(driver).clickProceedToCheckoutButton();
        OrderShippingPage orderShippingPage = orderAddressPage.clickProceedToCheckoutButton();
        orderShippingPage.clickProceedToCheckoutButton();
        assertThat(orderShippingPage.isFancyboxDisplayed())
                .isTrue();
        orderShippingPage.closeFancybox();
        orderShippingPage.changingCheckboxState();
    }

    @Test
    @Order(7)
    public void paymentMethodTitleTest() {
        OrderPaymentPage orderPaymentPage = new OrderShippingPage(driver).clickProceedToCheckoutButton();
        assertThat(orderPaymentPage.getNavigationPageTitle())
                .isEqualTo("Your payment method");
    }

    @Test
    @Order(8)
    public void bankWirePaymentTitleTest() {
        OrderBankWirePaymentPage orderBankWirePaymentPage = new OrderPaymentPage(driver).chooseBankWirePayment();
        assertThat(orderBankWirePaymentPage.getNavigationPageTitle())
                .isEqualTo("Bank-wire payment.");
        assertThat(orderBankWirePaymentPage.getTotalPriceInformation())
                .isEqualTo(TOTAL_ORDER_PRICE);
    }

    @Test
    @Order(9)
    public void orderConfirmationPageTest() {
        List<String> bankAccountInformation = new ArrayList<>(
                List.of("Pradeep Macharla", "xyz", "RTP")
        );
        OrderConfirmationPage orderConfirmationPage =
                new OrderBankWirePaymentPage(driver).clickPaymentIConfirmMyOrderButton();
        assertThat(orderConfirmationPage.getNavigationPageTitle())
                .isEqualTo("Order confirmation");
        assertThat(orderConfirmationPage.getConfirmationText())
                .isEqualTo("Your order on My Store is complete.");
        assertThat(orderConfirmationPage.getAmountInformation())
                .isEqualTo(TOTAL_ORDER_PRICE);
        assertThat(orderConfirmationPage.getBankAccountInformation())
                .isEqualTo(bankAccountInformation);
    }

    @Test
    @Order(10)
    public void emptyCommentMessageTest() {
        MyAccountPage myAccountPage = new OrderConfirmationPage(driver).openMyAccountPage();
        assertThat(myAccountPage.getTitle())
                .isEqualTo(PageTitles.MY_ACCOUNT_PAGE_TITLE.getPageTitle());
        OrderHistoryPage ordersHistoryPage = myAccountPage.clickOrderHistoryButton();
        assertThat(ordersHistoryPage.getTitle())
                .isEqualTo(PageTitles.ORDER_HISTORY_PAGE_TITLE.getPageTitle());
        ordersHistoryPage.showLastOrderDetails();
        ordersHistoryPage.clickSendButton();
        Alert alert = ordersHistoryPage.getAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isDanger())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(ALERT_DANGER_MESSAGE);
    }

    @Test
    @Order(11)
    public void nonEmptyCommentMessageTest() {
        OrderHistoryPage ordersHistoryPage = new OrderHistoryPage(driver);
        ordersHistoryPage.selectProductFromDropdownByID("5");
        ordersHistoryPage.setMessageText(USER_MESSAGE);
        ordersHistoryPage.clickSendButton();
        Alert alert = ordersHistoryPage.getAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isSuccess())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(ALERT_SUCCESS_MESSAGE);
        assertThat(ordersHistoryPage.getMessageText())
                .isEqualTo(USER_MESSAGE);
    }
}
