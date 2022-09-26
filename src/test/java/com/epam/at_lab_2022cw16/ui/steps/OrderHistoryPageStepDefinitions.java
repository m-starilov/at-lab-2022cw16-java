package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.constants.AlertMessageTexts;
import com.epam.at_lab_2022cw16.ui.page.OrderHistoryPage;
import com.epam.at_lab_2022cw16.ui.page.pageElements.Alert;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderHistoryPageStepDefinitions {

    private final WebDriver driver = EnvironmentUtils.getDriver();

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
        Alert alert = new OrderHistoryPage(driver).getAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isDanger())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(AlertMessageTexts.ORDER_HISTORY_PAGE_DANGER_MESSAGE.getAlertMessageText());
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
        Alert alert = new OrderHistoryPage(driver).getAlert();
        assertThat(alert.isDisplayed())
                .isTrue();
        assertThat(alert.isSuccess())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(AlertMessageTexts.ORDER_HISTORY_PAGE_SUCCESS_MESSAGE.getAlertMessageText());
    }

    @Then("I see user message {string}")
    public void checkUserMessage(String message) {
        assertThat(new OrderHistoryPage(driver).getMessageText())
                .isEqualTo(message);
    }
}
