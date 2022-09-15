package com.epam.at_lab_2022cw16.ui.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AuthenticationPage extends AbstractPage {

    @FindBy(xpath = "//input[@id='email']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@id='passwd']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@id='SubmitLogin']")
    private WebElement signInButton;

    public AuthenticationPage(WebDriver driver) {
        super(driver);
    }

    public AuthenticationPage inputEmail(String email) {
        driverWait().until(ExpectedConditions.elementToBeClickable(emailField)).sendKeys(email);
        return this;
    }

    public AuthenticationPage inputPassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }

    public MyAccountPage proceedToMyAccountPage() {
        signInButton.click();
        return new MyAccountPage(driver);
    }
}
