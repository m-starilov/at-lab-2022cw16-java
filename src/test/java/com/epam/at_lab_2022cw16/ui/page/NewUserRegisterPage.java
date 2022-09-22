package com.epam.at_lab_2022cw16.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NewUserRegisterPage extends AbstractBasePage {

    private static final String URL = "http://automationpractice.com/index.php?controller=authentication";
    private final String CREATE_ACCOUNT_ERROR_XPATH = "//*[@id=\"create_account_error\"]";

    @FindBy(xpath = "//*[@id=\"email_create\"]")
    private WebElement emailField;

    @FindBy(xpath = "//*[@id=\"SubmitCreate\"]")
    private WebElement createAccountButton;

    @FindBy(xpath = "//*[@id=\"create_account_error\"]/ol/li/text()")
    private WebElement invalid_email_address;

    public NewUserRegisterPage(WebDriver driver) {
        super(driver);
    }

    public NewUserRegisterPage openPage() {
        driver.get(URL);
        return this;
    }

    public void fillingEmailForm(String email) {
        emailField.clear();
        emailField.sendKeys(email);
        createAccountButton.click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"create_account_error\"] | //*[@id=\"submitAccount\"]")));
    }

    public boolean isErrorMessageVisible() {
        return isDisplayed(By.xpath(CREATE_ACCOUNT_ERROR_XPATH));
    }
}
