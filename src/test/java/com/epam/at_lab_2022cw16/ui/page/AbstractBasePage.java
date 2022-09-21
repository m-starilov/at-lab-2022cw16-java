package com.epam.at_lab_2022cw16.ui.page;

import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.naming.OperationNotSupportedException;
import java.time.Duration;
import java.util.List;

public abstract class AbstractBasePage {
    protected WebDriver driver;
    protected final int WAIT_TIMEOUT_SECONDS = 20;

    @FindBy(xpath = "//a[@class='login']")
    protected WebElement signInButton;

    @FindBy(xpath = "//a[@title='Women']")
    protected WebElement womenDressesBarButton;

    @FindBy(xpath = "//li[@class]//a[@title='Summer Dresses']")
    protected WebElement summerDressesButton;

    @FindBy(xpath = "//a[@title='View my customer account']")
    protected WebElement viewMyAccountButton;

    protected AbstractBasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected WebDriverWait driverWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));
    }

    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    public String getCSSValue(WebElement element, String cssParam) {
        return element.getCssValue(cssParam);
    }

    public AbstractBasePage openPage() throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Opening the page directly is not possible");
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public MyAccountPage openMyAccountPage() {
        viewMyAccountButton.click();
        return new MyAccountPage(driver);
    }

    public static boolean isDisplayed(By by) {
        try {
            return EnvironmentUtils.getDriver().findElement(by).isDisplayed();
        } catch (NoSuchElementException nsee) {
            return false;
        }
    }
}
