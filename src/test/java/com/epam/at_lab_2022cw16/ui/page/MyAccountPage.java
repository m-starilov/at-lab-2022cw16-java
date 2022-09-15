package com.epam.at_lab_2022cw16.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class MyAccountPage extends AbstractBasePage {

    @FindBy(xpath = "//a[@title='Women']")
    private WebElement womenCatalogButton;

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    public CatalogPage clickWomenCatalogButton() {
        driverWait().until(ExpectedConditions.elementToBeClickable(womenCatalogButton))
                .click();
        log.info("Go to Catalog Page");
        return new CatalogPage(driver);
    }
}
