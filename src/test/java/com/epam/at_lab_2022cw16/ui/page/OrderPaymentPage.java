package com.epam.at_lab_2022cw16.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Log4j2
public class OrderPaymentPage extends AbstractOrderPage {
    private static final String PAGE_TITLE = "PLEASE CHOOSE YOUR PAYMENT METHOD";

    @FindBy(xpath = "//a[@title='Pay by bank wire']")
    private WebElement payByBankWireButton;

    public OrderPaymentPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageTitleValid() {
        return summary.getText().equals(PAGE_TITLE);
    }

    public OrderBankWirePaymentPage chooseBankWirePayment() {
        payByBankWireButton.click();
        log.info("Proceeded to Bank-wire Payment method");
        return new OrderBankWirePaymentPage(driver);
    }
}
