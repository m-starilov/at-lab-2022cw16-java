package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.constants.PageTitles;
import com.epam.at_lab_2022cw16.ui.page.*;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class SaveOrderHistoryStepsDefinitions {
    private final WebDriver driver = EnvironmentUtils.getDriver();
    private String orderReference = null;

    @When("I open catalog page")
    public void openCatalogPage() {
        new WomenCatalogPage(driver).openPage();
    }

    @Then("catalog page is opened. Products are displayed on the page")
    public void catalogPageIsOpenedProductsAreDisplayedOnThePage() {
        Assertions.assertEquals(new WomenCatalogPage(driver).getTitle(), PageTitles.WOMEN_CATALOG.getPageTitle());
    }

    @When("I put one item {string} in a cart")
    public void putOneItemInACart(String productName) {
        new WomenCatalogPage(driver).addProductToCart(productName);
    }

    @Then("alert is displayed with a message {string}")
    public void alertIsDisplayedWithAMessage(String message) {
        Assertions.assertEquals(message, new WomenCatalogPage(driver).getMessageConfirmAdditionItemToCart());
    }

    @And("item  {string} displayed in a cart")
    public void itemDisplayedInACart(String productName) {
        Assertions.assertTrue(new WomenCatalogPage(driver).isProductAddedToCart(productName));
    }

    @When("I press Proceed to checkout button")
    public void iPressProceedToCheckoutButton() {
        new WomenCatalogPage(driver).clickCheckoutButton();
    }

    @Then("Shopping cart page is opened. Title of page is {string}")
    public void shoppingCartPageIsOpenedTitleOfPageIs(String h1) {
        Assertions.assertTrue(new OrderSummaryPage(driver).getSummary().contains(h1));

    }

    @And("added product {string} displayed on the page")
    public void addedProductDisplayedOnThePage(String productName) {
        List<String> addedProductNames = new OrderSummaryPage(driver).getAddedProductNames();
        Assertions.assertEquals(1, addedProductNames.size());
        Assertions.assertTrue(addedProductNames.contains(productName));
    }

    @When("I press Proceed to checkout button on Order Summary Page")
    public void iPressProceedToCheckoutButtonOnOrderSummaryPage() {
        new OrderSummaryPage(driver).clickProceedToCheckoutButtonAsUnauthorizedUser();
    }

    @Then("Authentication page is opened. Title of page is  {string}")
    public void authenticationPageIsOpenedTitleOfPageIs(String h1) {
        Assertions.assertEquals(h1, new AuthenticationPage(driver).getSummary());
    }

    @When("I log in with valid email {string} address and password {string}")
    public void iLogInWithValidEmailAddressAndPassword(String email, String password) {
        new AuthenticationPage(driver)
                .inputEmail(email)
                .inputPassword(password)
                .proceedToOrderAddressPage();
    }

    @Then("Address information page opened. Title of page is {string}")
    public void addressInformationPageOpenedTitleOfPageIs(String h1) {
        Assertions.assertEquals("ADDRESSES", new OrderAddressPage(driver).getSummary());
    }

    @When("I press Proceed to checkout button on the Address page")
    public void iPressProceedToCheckoutButtonOnTheAddressPage() {
        new OrderAddressPage(driver).clickProceedToCheckoutButton();
    }

    @Then("Shipping page opened. Title of page is {string}")
    public void shippingPageOpenedTitleOfPageIs(String h1) {
        Assertions.assertEquals(h1, new OrderShippingPage(driver).getSummary());
    }


    @When("I press Proceed to checkout button on the Shipping page")
    public void iPressProceedToCheckoutButtonOnTheShippingPage() {
        new OrderShippingPage(driver).clickProceedToCheckoutButton();
    }

    @Then("Alert is displayed with a message You must agree to the terms of service before continuing.")
    public void alertIsDisplayedWithAMessage() {
        Assertions.assertTrue(new OrderShippingPage(driver).isFancyboxDisplayed());
    }

    @When("I click checkbox Terms of service and press Proceed to checkout button")
    public void iClickCheckboxTermsOfServiceAndPressProceedToCheckoutButton() {
        new OrderShippingPage(driver)
                .closeFancybox()
                .changingCheckboxState()
                .clickProceedToCheckoutButton();
    }

    @Then("Your payment method page opened Title of page is {string}")
    public void yourPaymentMethodPageOpenedTitleOfPageIs(String h1) {
        Assertions.assertEquals(h1, new OrderPaymentPage(driver).getSummary());
    }

    @When("I click button Pay by bank wire")
    public void iClickButtonPayByBankWire() {
        new OrderPaymentPage(driver).chooseBankWirePayment();

    }

    @Then("Payment page opened. Title of page is {string}")
    public void paymentPageOpenedTitleOfPageIs(String h1) {
        Assertions.assertEquals(h1, new OrderBankWirePaymentPage(driver).getSummary());
    }

    @When("I press I confirm my order button")
    public void iPressIConfirmMyOrderButton() {
        new OrderBankWirePaymentPage(driver).clickPaymentIConfirmMyOrderButton();
    }

    @Then("Order confirmation page opened. Title of page is {string}.")
    public void orderConfirmationPageOpenedTitleOfPageIs(String h1) {
        Assertions.assertEquals(h1, new OrderConfirmationPage(driver).getSummary());
    }

    @And("A message {string}  displayed")
    public void aMessageDisplayed(String message) {
        Assertions.assertEquals(message, new OrderConfirmationPage(driver).getConfirmationText());
    }

    @And("order reference is on the page")
    public void orderReferenceIsOnThePage() {
        orderReference = new OrderConfirmationPage(driver).getOrderReverence();
        Assertions.assertEquals(9, orderReference.length());
    }

    @When("I press Back to orders")
    public void iPressBackToOrders() {
        new OrderConfirmationPage(driver).clickBackToOrdersButton();
    }


    @Then("Order history page opened. The list of orders contains an order with the current order number.")
    public void orderHistoryPageOpenedTheListOfOrdersContainsAnOrderWithTheCurrentOrderNumber() {
        Assertions.assertTrue(new OrderHistoryPage(driver).hasOrderInOrderHistory(orderReference));
    }
}