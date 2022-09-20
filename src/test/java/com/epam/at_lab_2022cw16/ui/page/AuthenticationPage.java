package com.epam.at_lab_2022cw16.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Log4j2
public class AuthenticationPage extends AbstractBasePage {

    private static final String BASE_URL = "http://automationpractice.com/index" +
            ".php?controller=authentication&back=my-account";

    @FindBy(xpath = "//input[@id='email']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@id='passwd']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@id='SubmitLogin']")
    private WebElement signInButton;

    public AuthenticationPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public AuthenticationPage openPage() {
        driver.get(BASE_URL);
        return this;
    }

    public AuthenticationPage inputEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
        return this;
    }

    public AuthenticationPage inputPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    public MyAccountPage proceedToMyAccountPage() {
        signInButton.click();
        log.info("Sign In button is pressed");
        return new MyAccountPage(driver);
    }
}
