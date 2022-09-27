package com.epam.at_lab_2022cw16.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

@Log4j2
public class MyStoreHomepage extends AbstractBasePage {

    private static final String HOMEPAGE = "http://automationpractice.com/index.php";

    @FindBy(xpath = "//a[@class='login']")
    private WebElement signInButton;

    @FindBy(xpath = "//a[@title='Women']")
    private WebElement womenDressesBarButton;

    @FindBy(xpath = "//li[@class]//a[@title='Summer Dresses']")
    private WebElement summerDressesButton;

    public MyStoreHomepage(WebDriver driver) {
        super(driver);
    }

    @Override
    public MyStoreHomepage openPage() {
        driver.get(HOMEPAGE);
        log.info("Start page is opened");
        return this;
    }

    public AuthenticationPage clickSignInButton() {
        signInButton.click();
        log.info("Go to Authentication Page");
        return new AuthenticationPage(driver);
    }

    public AuthenticationPage pressSignInButton() {
        signInButton.click();
        log.info("Press Sign in button");
        return new AuthenticationPage(driver);
    }

    public AbstractCatalogPage openSummerDressesCatalog() {
        new Actions(driver).moveToElement(womenDressesBarButton).build().perform();
        summerDressesButton.click();
        log.info("Press Summer dresses catalog button");
        return new SummerDressesCatalogPage(driver);
    }
}
