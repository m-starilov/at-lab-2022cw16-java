package com.epam.at_lab_2022cw16.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

@Log4j2
public class OrderHistoryPage extends AbstractBasePage {
    private static final String PAGE_TITLE = "ORDER HISTORY";

    private static final String orderInOrderList = "//a[contains(text(),'%s')]";

    @FindBy(xpath = "//tr[contains(@class, 'first_item')]//a[contains(@class, 'button')]/span[contains(text(), 'Details')]")
    private WebElement lastOrderDetailsButton;

    @FindBy(xpath = "//textarea[@class='form-control']")
    private WebElement messageInput;

    @FindBy(xpath = "//button[@name='submitMessage']")
    private WebElement sendMessageButton;

    @FindBy(xpath = "//select[@name='id_product']")
    private WebElement selectProductDropdown;

    @FindBy(xpath = "//h3[text()='Messages']/following-sibling::div[@class='table_block']//tr/td[2]")
    private WebElement sentMessageText;

    @FindBy(xpath = "//*[@id='order-list']//a[@class='color-myaccount']")
    private List<WebElement> orderReferenceInHistory;

    @FindBy(xpath = "//*[@id='block-order-detail']")
    private WebElement detailsBlock;

    @FindBy(xpath = "//td[@class='return_quantity']/label/span")
    private List<WebElement> quantityEachProductsInOldOrder;

    @FindBy(xpath = "//td[@class='bold']/label")
    private List<WebElement> productsInOldOrder;

    @FindBy(xpath = "//*[@id='submitReorder']/a")
    private WebElement reorderFromDetailsBlockButton;

    @FindBy(xpath = "//tr[contains(@class, 'first_item')]//a[@title='Reorder']")
    private WebElement reorderFromOrderListButton;

    public OrderHistoryPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean verifyPageTitle() {
        return summary.getText().equals(PAGE_TITLE);
    }

    public OrderHistoryPage showLastOrderDetails() {
        lastOrderDetailsButton.click();
        log.info("Show last order details");
        return this;
    }

    public OrderHistoryPage showOrderDetails(String orderReference) {
        findElement(By.xpath(format(orderInOrderList, orderReference))).click();
        return this;
    }

    public OrderHistoryPage clickSendButton() {
        driverWait().until(ExpectedConditions.visibilityOf(sendMessageButton))
                .click();
        log.info("Trying to send message");
        return this;
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

    public boolean isDisplayedDetailsBlock() {
        return driverWait().until(ExpectedConditions.attributeToBe(detailsBlock, "style", "display: block;"));
    }

    public int getProductsQuantityFromOldOrder() {
        AtomicInteger quantity = new AtomicInteger();
        quantityEachProductsInOldOrder.stream().map(WebElement::getText).collect(Collectors.toList())
                .stream().flatMapToInt(num -> IntStream.of(Integer.parseInt(num)))
                .forEach(quantity::addAndGet);
        return quantity.get();
    }

    public List<String> getProductsNameFromOldOrder() {
        return productsInOldOrder.stream()
                .map((element -> element.getText()
                        .replaceAll(" - .*", "")
                        .trim()))
                .collect(Collectors.toList());
    }

    public OrderSummaryPage reorderOldOrderByButtonFromDetails() {
        reorderFromDetailsBlockButton.click();
        log.info("Reorder old order from details. Go to Order Summary page");
        return new OrderSummaryPage(driver);
    }

    public OrderSummaryPage reorderOldOrderByButtonFromOrderList() {
        reorderFromOrderListButton.click();
        log.info("Reorder old order from list. Go to Order Summary page");
        return new OrderSummaryPage(driver);
    }
}
