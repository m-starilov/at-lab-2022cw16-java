package com.epam.at_lab_2022cw16.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Log4j2
public class OrderAddressPage extends AbstractOrderPage {

    @FindBy(xpath = "//div[@id='center_column']//button")
    private WebElement proceedToCheckoutButton;

    public OrderAddressPage(WebDriver driver) {
        super(driver);
    }

    public OrderShippingPage clickProceedToCheckoutButton() {
        proceedToCheckoutButton.click();
        log.info("Go to Shipping information");
        return new OrderShippingPage(driver);
    }
}
