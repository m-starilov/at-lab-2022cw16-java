package com.epam.at_lab_2022cw16.ui.tests.manual;

import com.epam.at_lab_2022cw16.annotations.JiraTicketsLink;
import com.epam.at_lab_2022cw16.ui.constants.PageTitles;
import com.epam.at_lab_2022cw16.ui.page.*;
import com.epam.at_lab_2022cw16.ui.utils.TestListener;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import java.util.List;
@JiraTicketsLink(id = 16318,
        description = "Check if order saves in order history",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16318")

@ExtendWith(TestListener.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderHistoryTest extends AbstractBaseTest {

    private final WebDriver driver = getWebDriver();
    private final WomenCatalogPage catalog = new WomenCatalogPage(driver);
    private static String orderReference;
    private static String productName = "Faded Short Sleeve T-shirts";

    @Order(1)
    @Test
    void shouldOpenCatalogPage() {
        catalog.openPage();
        Assertions.assertEquals(catalog.getTitle(), PageTitles.PAGE_WITH_WOMEN_TITLE.getPageTitle());
    }

    @Order(2)
    @Test
    void shouldAddItemInCart() {
        Assertions.assertTrue(catalog.addProductToCart(productName).isProductAddedToCart(productName));
    }
    @Order(3)
    @Test
    void shouldDisplayMessageConfirmAdditionItemToCart() {
        String confirmMessage = "Product successfully added to your shopping cart";
        Assertions.assertEquals(confirmMessage, catalog.getMessageConfirmAdditionItemToCart());
    }

    @Order(4)
    @Test
    void shouldBeOpenedCartWithAddedItem() {
        OrderSummaryPage orderSummaryPage = catalog.clickCheckoutButton();
        List<String> addedProductNames = orderSummaryPage.getAddedProductNames();
        Assertions.assertEquals(1, addedProductNames.size());
        Assertions.assertTrue(addedProductNames.contains(productName));
        Assertions.assertTrue(orderSummaryPage.getSummary().contains("SHOPPING-CART SUMMARY"));
    }

    @Order(5)
    @Test
    void shouldOpenAuthenticationPage() {
        AuthenticationPage authenticationPage = new OrderSummaryPage(driver).clickProceedToCheckoutButtonAsUnauthorizedUser();
        Assertions.assertEquals("AUTHENTICATION", authenticationPage.getSummary());
    }

    @Order(6)
    @Test
    void shouldOpenAddressPage() {
        OrderAddressPage addressPage = new AuthenticationPage(driver)
                .inputEmail("e1@gh.com")
                .inputPassword("22222222")
                .proceedToOrderAddressPage();
        Assertions.assertEquals("ADDRESSES", addressPage.getSummary());
    }

    @Order(7)
    @Test
    void shouldOpenShippingPage() {
        OrderShippingPage shippingPage = new OrderAddressPage(driver).clickProceedToCheckoutButton();
        Assertions.assertEquals("SHIPPING", shippingPage.getSummary());
    }

    @Order(8)
    @Test
    void shouldDisplayAlertWithAgreement() {
        OrderShippingPage shippingPage = new OrderShippingPage(driver);
        shippingPage.clickProceedToCheckoutButton();
        Assertions.assertTrue(shippingPage.isFancyboxDisplayed());
        Assertions.assertEquals("You must agree to the terms of service before continuing.", shippingPage.getFancyboxText());
    }

    @Order(9)
    @Test
    void shouldOpenPaymentPage() {
        OrderPaymentPage paymentPage = new OrderShippingPage(driver)
                .closeFancybox()
                .changingCheckboxState()
                .clickProceedToCheckoutButton();
        Assertions.assertEquals("PLEASE CHOOSE YOUR PAYMENT METHOD", paymentPage.getSummary());
    }

    @Order(10)
    @Test
    void shouldOpenBankWirePaymentPage() {
        Assertions.assertEquals("ORDER SUMMARY", new OrderPaymentPage(driver).chooseBankWirePayment().getSummary());
    }

    @Order(11)
    @Test
    void shouldOpenConfirmationPageAndDisplayMessage (){
        OrderConfirmationPage confirmationPage = new OrderBankWirePaymentPage(driver).clickPaymentIConfirmMyOrderButton();
        orderReference = confirmationPage.getOrderReverence();
        Assertions.assertEquals("ORDER CONFIRMATION", confirmationPage.getSummary());
        Assertions.assertEquals("Your order on My Store is complete.", new OrderConfirmationPage(driver).getConfirmationText());
        Assertions.assertEquals(9, orderReference.length());
    }

    @Order(12)
    @Test
    void shouldSaveOrderReferenceInOrderHistory(){
        Assertions.assertTrue(new OrderConfirmationPage(driver).clickBackToOrdersButton().hasOrderInOrderHistory(orderReference));
    }
}
