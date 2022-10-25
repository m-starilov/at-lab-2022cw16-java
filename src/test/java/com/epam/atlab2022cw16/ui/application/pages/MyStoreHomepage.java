package com.epam.atlab2022cw16.ui.application.pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class MyStoreHomepage extends AbstractBasePage {
    private static final String HOMEPAGE = "http://automationpractice.com/index.php";
    private static final String PAGE_TITLE = "Automation Practice Website";

    @FindBy(xpath = "//a[@class='login']")
    private WebElement signInButton;

    @FindBy(xpath = "//a[@title='Women']")
    private WebElement womenDressesBarButton;

    @FindBy(xpath = "//li[@class]//a[@title='Summer Dresses']")
    private WebElement summerDressesButton;

    @FindBy(xpath = "//input[@id='newsletter-input']")
    private WebElement newsletterInputEmailField;

    @FindBy(xpath = "//button[@name='submitNewsletter']")
    private WebElement submitNewsletterButton;

    public MyStoreHomepage(WebDriver driver) {
        super(driver);
    }

    @Override
    public MyStoreHomepage openPage() {
        driver.get(HOMEPAGE);
        log.info("Start page is opened");
        return this;
    }

    @Override
    public boolean isPageTitleValid() {
        return summary.getText().equals(PAGE_TITLE);
    }

    public AuthenticationPage clickSignInButton() {
        signInButton.click();
        log.info("Go to Authentication Page");
        return new AuthenticationPage(driver);
    }

    public SummerDressesCatalogPage openSummerDressesCatalog() {
        new Actions(driver).moveToElement(womenDressesBarButton).build().perform();
        summerDressesButton.click();
        log.info("Press Summer dresses catalog button");
        return new SummerDressesCatalogPage(driver);
    }

    public MyStoreHomepage sendEmailToNewsletterField(String email) {
        driverWait().until(ExpectedConditions.elementToBeClickable(newsletterInputEmailField));
        newsletterInputEmailField.sendKeys(email);
        log.info("Email address pasted");
        return this;
    }

    public MyStoreHomepage pressSubmitNewsletterButton() {
        driverWait().until(ExpectedConditions.elementToBeClickable(submitNewsletterButton)).click();
        log.info("Submit newsletter button pressed");
        return this;
    }
}
