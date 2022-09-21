package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.page.OrderAddressPage;
import com.epam.at_lab_2022cw16.ui.page.OrderBankWirePaymentPage;
import com.epam.at_lab_2022cw16.ui.page.OrderConfirmationPage;
import com.epam.at_lab_2022cw16.ui.page.OrderPaymentPage;
import com.epam.at_lab_2022cw16.ui.page.OrderShippingPage;
import com.epam.at_lab_2022cw16.ui.page.OrderSummaryPage;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderPageStepDefinitions {

    private final WebDriver driver = EnvironmentUtils.getDriver();
    private final List<String> bankAccountInformation = Arrays.asList("Pradeep Macharla", "xyz", "RTP");

    @Then("I see {string} in cart")
    public void checkNumberOfItemsInCart(String numberOfItems) {
        assertThat(new OrderSummaryPage(driver).getSummaryProductsQuantity())
                .isEqualTo(numberOfItems);
    }

    @When("I enter {string} to Qty field")
    public void setQuantity(String quantity) {
        new OrderSummaryPage(driver).setProductQuantity(quantity);
    }

    @When("I click Proceed to checkout button at Summary page")
    public void clickProceedToCheckoutSummary() {
        new OrderSummaryPage(driver).clickProceedToCheckoutButton();
    }

    @When("I click Proceed to checkout button at Address page")
    public void clickProceedToCheckoutAddress() {
        new OrderAddressPage(driver).clickProceedToCheckoutButton();
    }

    @When("I click Proceed to checkout button at Shipping page")
    public void clickProceedToCheckoutShipping() {
        new OrderShippingPage(driver).clickProceedToCheckoutButton();
    }

    @Then("I see modal window is displayed")
    public void checkModalWindowDisplayed() {
        assertThat(new OrderShippingPage(driver).isFancyboxDisplayed())
                .isTrue();
    }

    @When("I close modal window")
    public void closeModalWindow() {
        new OrderShippingPage(driver).closeFancybox();
    }

    @When("I change checkbox state")
    public void changeCheckbox() {
        new OrderShippingPage(driver).changingCheckboxState();
    }

    @When("I choose Pay by bank wire method")
    public void payByBankWire() {
        new OrderPaymentPage(driver).chooseBankWirePayment();
    }

    @When("I click I confirm my order button")
    public void confirmMyOrder() {
        new OrderBankWirePaymentPage(driver).clickPaymentIConfirmMyOrderButton();
    }

    @Then("I see {string} text is displayed")
    public void checkOrderCompletion(String text) {
        assertThat(new OrderConfirmationPage(driver).getConfirmationText())
                .isEqualTo(text);
    }

    @Then("I see {string} navigation page title")
    public void checkNavigationPageTitle(String expectedTitle) {
        assertThat(new OrderAddressPage(driver).getNavigationPageTitle())
                .isEqualTo(expectedTitle);
    }

    @Then("I see amount {string}")
    public void checkAmount(String amount) {
        assertThat(new OrderConfirmationPage(driver).getAmountInformation())
                .isEqualTo(amount);
    }

    @Then("I see correct bank information")
    public void checkBankInformation() {
        assertThat(new OrderConfirmationPage(driver).getBankAccountInformation())
                .isEqualTo(bankAccountInformation);
    }
}
