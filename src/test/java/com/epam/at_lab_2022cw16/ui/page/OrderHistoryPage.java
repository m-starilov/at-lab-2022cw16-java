package com.epam.at_lab_2022cw16.ui.page;

import com.epam.at_lab_2022cw16.ui.page.pageElements.Alert;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

@Log4j2
public class OrderHistoryPage extends AbstractBasePage {

    @FindBy(xpath = "//tr[contains(@class, 'first_item')]//a[contains(@class, 'button')]/span[contains(text(), 'Details')]")
    private WebElement lastOrderDetailsButton;

    @FindBy(xpath = "//textarea[@class='form-control']")
    private WebElement messageInput;

    @FindBy(xpath = "//button[@name='submitMessage']")
    private WebElement sendMessageButton;

    @FindBy(xpath = "//*[contains(@class, 'alert')]")
    private WebElement alert;

    @FindBy(xpath = "//select[@name='id_product']")
    private WebElement selectProductDropdown;

    @FindBy(xpath = "//h3[text()='Messages']/following-sibling::div[@class='table_block']//tr/td[2]")
    private WebElement sentMessageText;

    @FindBy(xpath = "//*[@id='order-list']//a[@class='color-myaccount']")
    private List<WebElement> orderReferenceInHistory;

    public OrderHistoryPage(WebDriver driver) {
        super(driver);
    }

    public OrderHistoryPage showLastOrderDetails() {
        lastOrderDetailsButton.click();
        log.info("Show last order details");
        return this;
    }

    public OrderHistoryPage clickSendButton() {
        driverWait().until(ExpectedConditions.visibilityOf(sendMessageButton))
                .click();
        log.info("Trying to send message");
        return this;
    }

    public Alert getAlert() {
        return new Alert(alert, driverWait());
    }

    public String getMessageText() {
        return driverWait().until(ExpectedConditions.visibilityOf(sentMessageText))
                .getText();
    }

    public OrderHistoryPage setMessageText(String message) {
        driverWait().until(ExpectedConditions.visibilityOf(messageInput))
                .clear();
        messageInput.sendKeys(message);
        return this;
    }

    public OrderHistoryPage selectProductFromDropdownByID(String id) {
        Select select = new Select(selectProductDropdown);
        select.selectByValue(id);
        log.info("Product with ID=" + id + " is selected");
        return this;
    }

    public boolean hasOrderInOrderHistory(String orderReference) {
        return driverWait()
                .until(ExpectedConditions.visibilityOfAllElements(orderReferenceInHistory))
                .stream()
                .anyMatch(element -> element.getText().contains(orderReference));

    }
}
