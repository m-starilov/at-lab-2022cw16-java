package com.epam.at_lab_2022cw16.ui.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyStoreHomepage extends AbstractPage {

    private static final String HOMEPAGE = "http://automationpractice.com/index.php";

    @FindBy(xpath = "//a[@class='login']")
    private WebElement signInButton;
    @FindBy(xpath = "//a[@title='Women']")
    private WebElement womenDressesBarButton;
    @FindBy(xpath = "//li[@class]//a[@title='Summer Dresses']")
    private WebElement summerDressesButton;


    public MyStoreHomepage openPage(String url) {
        driver.get(HOMEPAGE);
        return this;
    }

    public AuthenticationPage pressSignInButton() {
        signInButton.click();
        return new AuthenticationPage(driver);
    }

    public CatalogPage openSummerDressesCatalog() {
        new Actions(driver).moveToElement(womenDressesBarButton).build().perform();
        summerDressesButton.click();
        return new CatalogPage(driver);
    }

    public MyStoreHomepage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
