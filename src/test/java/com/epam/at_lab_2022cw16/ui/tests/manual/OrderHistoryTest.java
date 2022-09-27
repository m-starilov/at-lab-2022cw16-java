package com.epam.at_lab_2022cw16.ui.tests.manual;

import com.epam.at_lab_2022cw16.annotations.JiraTicketsLink;
import com.epam.at_lab_2022cw16.ui.constants.PageTitles;
import com.epam.at_lab_2022cw16.ui.page.AuthenticationPage;
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
import com.epam.at_lab_2022cw16.ui.utils.TestListener;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JiraTicketsLink(id = {16318, 16324},
        description = "Check if order saves in order history and check ability to reorder item from order history",
        url = {"https://jira.epam.com/jira/browse/EPMFARMATS-16318", "https://jira.epam.com/jira/browse/EPMFARMATS-16324"})

@ExtendWith(TestListener.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderHistoryTest extends AbstractBaseTest {

    private final WebDriver driver = getWebDriver();
    private final WomenCatalogPage catalog = new WomenCatalogPage(driver);
    private static String orderReference;
    private static final String productName = "Faded Short Sleeve T-shirts";
    private static final int productQuantity = 1;
    private static List<String> productsFromOldOrder;

    @Order(1)
    @Test
    void shouldOpenCatalogPage() {
        catalog.openPage();
        assertEquals(catalog.getTitle(), PageTitles.PAGE_WITH_WOMEN_TITLE.getPageTitle());
    }

    @Order(2)
    @Test
    void shouldAddItemInCart() {
        assertTrue(catalog.addProductToCart(productName).isProductAddedToCart(productName));
    }

    @Order(3)
    @Test
    void shouldDisplayMessageConfirmAdditionItemToCart() {
        String confirmMessage = "Product successfully added to your shopping cart";
        assertEquals(confirmMessage, catalog.getMessageConfirmAdditionItemToCart());
    }

    @Order(4)
    @Test
    void shouldBeOpenedCartWithAddedItem() {
        OrderSummaryPage orderSummaryPage = catalog.clickCheckoutButton();
        List<String> addedProductNames = orderSummaryPage.getAddedProductNames();
        assertEquals(1, addedProductNames.size());
        assertTrue(addedProductNames.contains(productName));
        assertTrue(orderSummaryPage.getSummary().contains("SHOPPING-CART SUMMARY"));
    }

    @Order(5)
    @Test
    void shouldOpenAuthenticationPage() {
        AuthenticationPage authenticationPage = new OrderSummaryPage(driver).clickProceedToCheckoutButtonAsUnauthorizedUser();
        assertEquals("AUTHENTICATION", authenticationPage.getSummary());
    }

    @Order(6)
    @Test
    void shouldOpenAddressPage() {
        OrderAddressPage addressPage = new AuthenticationPage(driver)
                .inputEmail("e1@gh.com")
                .inputPassword("22222222")
                .proceedToOrderAddressPage();
        assertEquals("ADDRESSES", addressPage.getSummary());
    }

    @Order(7)
    @Test
    void shouldOpenShippingPageFirstTest() {
        OrderShippingPage shippingPage = new OrderAddressPage(driver).clickProceedToCheckoutButton();
        assertEquals("SHIPPING", shippingPage.getSummary());
    }

    @Order(8)
    @Test
    void shouldDisplayAlertWithAgreement() {
        OrderShippingPage shippingPage = new OrderShippingPage(driver);
        shippingPage.clickProceedToCheckoutButton();
        assertTrue(shippingPage.isFancyboxDisplayed());
        assertEquals("You must agree to the terms of service before continuing.", shippingPage.getFancyboxText());
    }

    @Order(9)
    @Test
    void shouldOpenPaymentPageFirstTest() {
        OrderPaymentPage paymentPage = new OrderShippingPage(driver)
                .closeFancybox()
                .changingCheckboxState()
                .clickProceedToCheckoutButton();
        assertEquals("PLEASE CHOOSE YOUR PAYMENT METHOD", paymentPage.getSummary());
    }

    @Order(10)
    @Test
    void shouldOpenBankWirePaymentPage() {
        assertEquals("ORDER SUMMARY", new OrderPaymentPage(driver).chooseBankWirePayment().getSummary());
    }

    @Order(11)
    @Test
    void shouldOpenConfirmationPageAndDisplayMessage() {
        OrderConfirmationPage confirmationPage = new OrderBankWirePaymentPage(driver).clickPaymentIConfirmMyOrderButton();
        orderReference = confirmationPage.getOrderReverence();
        assertEquals("ORDER CONFIRMATION", confirmationPage.getSummary());
        assertEquals("Your order on My Store is complete.", new OrderConfirmationPage(driver).getConfirmationText());
        assertEquals(9, orderReference.length());
    }

    @Order(12)
    @Test
    void shouldSaveOrderReferenceInOrderHistory() {
        assertTrue(new OrderConfirmationPage(driver).clickBackToOrdersButton().hasOrderInOrderHistory(orderReference));
    }

    @Order(13)
    @Test
    void shouldLoginAndOpenMyAccountPage() {
        String summary = new MyStoreHomepage(driver)
                .openPage()
                .clickMyAccountButton()
                .getSummary();
        assertEquals("MY ACCOUNT", summary);
    }

    @Order(14)
    @Test
    void shouldOpenOrderHistoryPageFromMyAccountPage() {
        String summary = new MyAccountPage(driver).clickOrderHistoryButton().getSummary();
        assertEquals("ORDER HISTORY", summary);
    }

    @Order(15)
    @Test
    void shouldDisplayOrderInformationOnPage() {
        OrderHistoryPage orderHistoryPage = new OrderHistoryPage(driver);
        assertTrue(orderHistoryPage.showOrderDetails(orderReference).isDisplayedDetailsBlock());

        productsFromOldOrder = orderHistoryPage.getProductsNameFromOldOrder();
        assertTrue(productsFromOldOrder.stream().anyMatch(name -> name.contains(productName)));
        assertEquals(productQuantity, orderHistoryPage.getProductsQuantityFromOldOrder());
    }

    @Order(16)
    @Test
    void shouldAddReorderedItemInCart() {
        OrderSummaryPage summaryPage = new OrderHistoryPage(driver).reorderOldOrderByButtonFromDetails();
        assertTrue(summaryPage.getSummary().contains("SHOPPING-CART SUMMARY"));

        List<String> productsFromCurrentOrder = summaryPage.getAddedProductNames();
        assertTrue(productsFromOldOrder.containsAll(productsFromCurrentOrder));
    }

    @Order(17)
    @Test
    void shouldOpenOrderHistoryPage() {
        String summary = new OrderSummaryPage(driver).clickMyAccountButton().clickOrderHistoryButton().getSummary();
        assertEquals("ORDER HISTORY", summary);
    }

    @Order(18)
    @Test
    void shouldOpenCartPageAndDisplayAddedItemOnly() {
        OrderSummaryPage summaryPage = new OrderHistoryPage(driver)
                .showOrderDetails(orderReference)
                .reorderOldOrderByButtonFromOrderList();
        assertTrue(summaryPage.getSummary().contains("SHOPPING-CART SUMMARY"));

        List<String> productsFromCurrentOrder = summaryPage.getAddedProductNames();
        assertEquals(productQuantity, summaryPage.getSummaryProductsQuantityAsInt());
        assertTrue(productsFromOldOrder.containsAll(productsFromCurrentOrder));

    }

    @Order(19)
    @Test
    void shouldOpenAddressInformationPage() {
        assertEquals("ADDRESSES", new OrderSummaryPage(driver)
                .clickProceedToCheckoutButton()
                .getSummary());
    }

    @Order(20)
    @Test
    void shouldOpenShippingPage() {
        OrderShippingPage shippingPage = new OrderAddressPage(driver).clickProceedToCheckoutButton();
        assertEquals("SHIPPING", shippingPage.getSummary());
    }

    @Order(21)
    @Test
    void shouldOpenPaymentMethodPage() {
        OrderPaymentPage paymentPage = new OrderShippingPage(driver)
                .changingCheckboxState()
                .clickProceedToCheckoutButton();
        assertEquals("PLEASE CHOOSE YOUR PAYMENT METHOD", paymentPage.getSummary());
    }

    @Order(22)
    @Test
    void shouldOpenPaymentPage() {
        assertEquals("ORDER SUMMARY", new OrderPaymentPage(driver).chooseBankWirePayment().getSummary());
    }

    @Order(23)
    @Test
    void shouldOpenConfirmationPageAndDisplayConfirmMessage() {
        OrderConfirmationPage confirmationPage = new OrderBankWirePaymentPage(driver).clickPaymentIConfirmMyOrderButton();
        assertEquals("ORDER CONFIRMATION", confirmationPage.getSummary());
        assertEquals("Your order on My Store is complete.", confirmationPage.getConfirmationText());

    }
}
