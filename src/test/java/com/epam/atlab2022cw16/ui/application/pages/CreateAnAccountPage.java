package com.epam.atlab2022cw16.ui.application.pages;

import com.epam.atlab2022cw16.ui.application.models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;

import static java.lang.String.format;

public class CreateAnAccountPage extends AbstractBasePage {
    private static final String birthDay = "//*[@id='days']/option[@value=%s]";
    private static final String birthMonth = "//*[@id='months']/option[@value=%s]";
    private static final String birthYear = "//*[@id='years']/option[@value=%s]";
    private static final String PAGE_TITLE = "CREATE AN ACCOUNT";

    @FindBy (xpath = "//*[@id='center_column']/div[@class='alert alert-danger']")
    private WebElement alertMessage;
    @FindBy(xpath = "//*[@id='submitAccount']")
    private WebElement registerButton;

    @FindBy(xpath = "//*[@id='id_gender1']")
    private WebElement genderCheckbox;

    @FindBy(xpath = "//*[@id='customer_firstname']")
    private WebElement firstNameField;

    @FindBy(xpath = "//*[@id='customer_lastname']")
    private WebElement lastNameField;

    @FindBy(xpath = "//*[@id='passwd']")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id='days']")
    private WebElement birthDaySelect;

    @FindBy(xpath = "//*[@id='months']")
    private WebElement monthSelect;

    @FindBy(xpath = "//*[@id='years']")
    private WebElement yearSelect;

    @FindBy(xpath = "//*[@id='address1']")
    private WebElement addressField;

    @FindBy(xpath = "//*[@id='city']")
    private WebElement cityField;

    @FindBy(xpath = "//*[@id='id_state']")
    private WebElement stateSelect;

    @FindBy(xpath = "//*[@id='id_state']/option[46]")
    private WebElement stateTexas;

    @FindBy(xpath = "//*[@id='postcode']")
    private WebElement postCode;

    @FindBy(xpath = "//*[@id='phone_mobile']")
    private WebElement mobileField;

    @FindBy(xpath = "//*[@id='center_column']/div/p")
    private WebElement numberOfErrorsEmptyReg;

    @FindBy(xpath = "//*[@id='center_column']/div/ol/li[1]")
    private WebElement invalidLastNameErrorText;

    @FindBy(xpath = "//*[@id='center_column']/div/ol/li[2]")
    private WebElement invalidFirstNameErrorText;

    @FindBy(xpath = "//*[@id='center_column']/div/ol/li[3]")
    private WebElement invalidMobileErrorText;

    @FindBy(xpath = "//*[@id='center_column']/div/ol/li[4]")
    private WebElement invalidZipErrorText;

    public CreateAnAccountPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageTitleValid() {
        return summary.getText().equals(PAGE_TITLE);
    }

    public String registerErrorMessageText() {
        return numberOfErrorsEmptyReg.getText();
    }

    public void fillingRegisterForm(User user) {
        genderCheckbox.click();
        firstNameField.clear();
        firstNameField.sendKeys(user.getFirstName());
        lastNameField.clear();
        lastNameField.sendKeys(user.getLastName());
        passwordField.clear();
        passwordField.sendKeys(user.getPassword());
        birthDaySelect.click();
        findElement(By.xpath(format(birthDay, user.getBirthDay()))).click();
        monthSelect.click();
        findElement(By.xpath(format(birthMonth, user.getBirthMonth()))).click();
        yearSelect.click();
        findElement(By.xpath(format(birthYear, user.getBirthYear()))).click();
        addressField.clear();
        addressField.sendKeys(user.getAddress());
        cityField.clear();
        cityField.sendKeys(user.getCity());
        stateSelect.click();
        stateTexas.click();
        postCode.clear();
        postCode.sendKeys(user.getPostalCode());
        mobileField.clear();
        mobileField.sendKeys(user.getMobilePhone());
    }

    public String getBorderColor() {
        return Color.fromString(firstNameField.getCssValue("border-color")).asHex();
    }

    public void clickRegisterButton() {
        registerButton.click();
    }

    public boolean isAlertMessageVisible() {
        return isDisplayed(alertMessage);
    }

    public String invalidLastNameText() {
        return invalidLastNameErrorText.getText();
    }

    public String invalidFirstNameText() {
        return invalidFirstNameErrorText.getText();
    }

    public String invalidMobileText() {
        return invalidMobileErrorText.getText();
    }

    public String invalidZipText() {
        return invalidZipErrorText.getText();
    }
}
