package com.epam.at_lab_2022cw16.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;

@Log4j2
public class OrderSummaryPage extends AbstractOrderPage {

    @FindBy(xpath = "//div[@id='center_column']//a[@title='Proceed to checkout']")
    private WebElement proceedToCheckoutButton;

    @FindBy(xpath = "//span[@id='summary_products_quantity']")
    private WebElement summaryProductsQuantity;

    @FindBy(xpath = "//input[contains(@class, 'cart_quantity_input')]")
    private WebElement productQuantityInput;

    @FindBy(xpath = "//td[contains(@class, 'cart_quantity')]/input[@type='hidden']")
    private WebElement productQuantityHiddenInput;

    @FindBy(xpath = "//span[@id='total_price']")
    private WebElement totalOrderPrice;

    public OrderSummaryPage(WebDriver driver) {
        super(driver);
    }

    public OrderAddressPage clickProceedToCheckoutButton() {
        proceedToCheckoutButton.click();
        log.info("Go to Address information");
        return new OrderAddressPage(driver);
    }

    public OrderSummaryPage setProductQuantity(String qty) {
        productQuantityInput.clear();
        productQuantityInput.sendKeys(qty);
        log.info("Try to set Product quantity value to \"" + qty + "\"");
        if (qty.matches("[0-9]+")) {
            driverWait().until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return (productQuantityHiddenInput.getAttribute("value").equals(qty));
                }
            });
        }
        return this;
    }

    public String getSummaryProductsQuantity() {
        return summaryProductsQuantity.getText();
    }

    public String getTotalPrice() {
        return totalOrderPrice.getText();
    }
}
