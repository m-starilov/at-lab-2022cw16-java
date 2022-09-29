package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.constants.Constants.AlertMessageTexts;
import com.epam.at_lab_2022cw16.ui.page.OrderHistoryPage;
import com.epam.at_lab_2022cw16.ui.page.pageElements.Alert;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderHistoryPageStepDefinitions {

    private final WebDriver driver = EnvironmentUtils.getDriver();
    private final String orderReference = OrderPageStepDefinitions.orderReferenceThreadLocal.get();
    static ThreadLocal<List<String>> listThreadLocal = new ThreadLocal<>();

    @When("I open details of last order")
    public void openDetailsOfLastOrder() {
        new OrderHistoryPage(driver).showLastOrderDetails();
    }

    @When("I click Send button")
    public void sendCommentMessage() {
        new OrderHistoryPage(driver).clickSendButton();
    }

    @Then("I see Alert-Danger message at Order History page")
    public void checkDangerMessage() {
        Alert alert = new OrderHistoryPage(driver).getPageElementAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isDanger())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(AlertMessageTexts.ORDER_HISTORY_PAGE_DANGER_MESSAGE);
    }

    @When("I select product with id {string} from dropdown menu")
    public void selectProductFromDropdownByID(String id) {
        new OrderHistoryPage(driver).selectProductFromDropdownByID(id);
    }

    @When("I send text message {string}")
    public void setMessageText(String text) {
        new OrderHistoryPage(driver).setMessageText(text);
    }

    @Then("I see Alert-Success message at Order History page")
    public void checkSuccessMessage() {
        Alert alert = new OrderHistoryPage(driver).getPageElementAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isSuccess())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(AlertMessageTexts.ORDER_HISTORY_PAGE_SUCCESS_MESSAGE);
    }

    @Then("I see user message {string}")
    public void checkUserMessage(String message) {
        assertThat(new OrderHistoryPage(driver).getMessageText())
                .isEqualTo(message);
    }

    @Then("Order history page opened. The list of orders contains an order with the current order number.")
    public void isOpenedOrderHistoryPageListOfOrdersContainsAnOrderWithTheCurrentOrderNumber() {
        assertTrue(new OrderHistoryPage(driver).hasOrderInOrderHistory(orderReference));
    }

    @When("I click Details button")
    public void clickDetailsButton() {

        new OrderHistoryPage(driver).showOrderDetails(orderReference);
    }

    @Then("order information is displayed at the bottom of the page \\(expected product name {string} and quantity {int})")
    public void isDisplayedOrderInformationAtBottomPage(String productName, int quantity) {
        OrderHistoryPage orderHistoryPage = new OrderHistoryPage(driver);
        assertTrue(orderHistoryPage.showOrderDetails(orderReference).isDisplayedDetailsBlock());

        List<String> productsFromOldOrder = orderHistoryPage.getProductsNameFromOldOrder();
        listThreadLocal.set(productsFromOldOrder);

        assertTrue(productsFromOldOrder.stream().anyMatch(name -> name.contains(productName)));
        assertEquals(quantity, orderHistoryPage.getProductsQuantityFromOldOrder());
        assertTrue(new OrderHistoryPage(driver).isDisplayedDetailsBlock());
    }

    @When("I press Reorder button in the block with information about the order")
    public void pressReorderButtonInTheBlockWithInformationAboutTheOrder() {
        new OrderHistoryPage(driver).reorderOldOrderByButtonFromDetails();
    }

    @When("I press Reorder button in block with list of orders")
    public void pressReorderButtonInBlockWithListOfOrders() {
        OrderHistoryPage orderHistoryPage = new OrderHistoryPage(driver)
                .showOrderDetails(orderReference);
        listThreadLocal.set(orderHistoryPage.getProductsNameFromOldOrder());
        orderHistoryPage.reorderOldOrderByButtonFromOrderList();
    }
}
