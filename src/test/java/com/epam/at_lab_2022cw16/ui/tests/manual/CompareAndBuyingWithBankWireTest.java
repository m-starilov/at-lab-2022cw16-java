package com.epam.at_lab_2022cw16.ui.tests.manual;

import com.epam.at_lab_2022cw16.ui.model.User;
import com.epam.at_lab_2022cw16.ui.page.*;
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
public class CompareAndBuyingWithBankWireTest extends AbstractBaseTest {

    private final WebDriver driver = getWebDriver();

    private static final String TOTAL_ORDER_PRICE = "$59.96";

    @Test
    @Order(1)
    public void openHomepageTest() {
        MyStoreHomepage homepage = new MyStoreHomepage(driver).openPage();
        assertThat(homepage.getTitle())
                .contains("My Store");
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
                .contains("My account");
    }

    @Test
    @Order(3)
    public void addToComparisonTest() {
        CatalogPage catalogPage = new MyAccountPage(driver).clickWomenCatalogButton();
        catalogPage.addToCompareItemByID(5);
        catalogPage.addToCompareItemByID(6);
        ComparisonPage comparisonPage = catalogPage.clickCompareButton();
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
}
