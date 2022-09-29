package com.epam.at_lab_2022cw16.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Log4j2
public class OrderBankWirePaymentPage extends AbstractOrderPage {
    private static final String PAGE_TITLE = "ORDER SUMMARY";


    @FindBy(xpath = "//div[@id='center_column']//button")
    private WebElement paymentIConfirmMyOrderButton;

    @FindBy(xpath = "//span[@id='amount']")
    private WebElement totalOrderPrice;

    public OrderBankWirePaymentPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean verifyPageTitle() {
        return summary.getText().equals(PAGE_TITLE);
    }

    public OrderConfirmationPage clickPaymentIConfirmMyOrderButton() {
        paymentIConfirmMyOrderButton.click();
        log.info("Go to Order confirmation");
        return new OrderConfirmationPage(driver);
    }

    public String getTotalPriceInformation() {
        log.info("Total Price is " + totalOrderPrice.getText());
        return totalOrderPrice.getText();
    }
}
