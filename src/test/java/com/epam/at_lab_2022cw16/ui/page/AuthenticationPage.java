package com.epam.at_lab_2022cw16.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.epam.at_lab_2022cw16.ui.constants.PageTitles.ALREADY_REGISTERED;
import static com.epam.at_lab_2022cw16.ui.constants.PageTitles.CREATE_AN_ACCOUNT;

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

    @FindBy(xpath = "//form[@id='create-account_form']")
    private List<WebElement> createAccountForm;

    @FindBy(xpath = "//form[@id='create-account_form']/h3")
    private WebElement createAccountFormTitle;

    @FindBy(xpath = "//form[@id='login_form']")
    private List<WebElement> loginForm;

    @FindBy(xpath = "//form[@id='login_form']/h3")
    private WebElement loginFormTitle;

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
        log.info("Input email");
        return this;
    }

    public AuthenticationPage inputPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
        log.info("Input password");
        return this;
    }

    public MyAccountPage proceedToMyAccountPage() {
        signInButton.click();
        log.info("Sign In button is pressed");
        return new MyAccountPage(driver);
    }

    public boolean isCreateAccountFormVisible() {
        return waitForVisibilityOf(createAccountForm.get(0)).isDisplayed()
                && createAccountFormTitle.getText().equals(CREATE_AN_ACCOUNT.getPageTitle());
    }

    public boolean isLoginFormVisible() {
        return waitForVisibilityOf(loginForm.get(0)).isDisplayed()
                && loginFormTitle.getText().equals(ALREADY_REGISTERED.getPageTitle());
    }

    public OrderAddressPage proceedToOrderAddressPage() {
        signInButton.click();
        log.info("Sign In button is pressed");
        return new OrderAddressPage(driver);
    }
}