package com.epam.at_lab_2022cw16.ui.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class AbstractOrderPage extends AbstractBasePage {

    @FindBy(xpath = "//a[@class='home']/following-sibling::span[@class='navigation_page'][last()]")
    protected WebElement navigationPageTitle;

    public AbstractOrderPage(WebDriver driver) {
        super(driver);
    }

    public String getNavigationPageTitle() {
        return navigationPageTitle.getText();
    }
}
