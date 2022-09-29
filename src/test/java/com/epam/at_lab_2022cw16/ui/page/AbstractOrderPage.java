package com.epam.at_lab_2022cw16.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Log4j2
public abstract class AbstractOrderPage extends AbstractBasePage {

    @FindBy(xpath = "//div[@id='center_column']//*[contains(text(),'Proceed to checkout')]")
    private WebElement proceedToCheckoutButton;

    public AbstractOrderPage(WebDriver driver) {
        super(driver);
    }

    public void clickProceedToCheckoutButtonCommon() {
        proceedToCheckoutButton.click();
    }
}
