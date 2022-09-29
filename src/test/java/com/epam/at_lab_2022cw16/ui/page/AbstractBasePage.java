package com.epam.at_lab_2022cw16.ui.page;

import com.epam.at_lab_2022cw16.ui.page.pageElements.Alert;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.naming.OperationNotSupportedException;
import java.time.Duration;
import java.util.List;

@Log4j2
public abstract class AbstractBasePage {

    private static final String MINI_CART_EMPTY_TITLE_XPATH = "//div[@class='shopping_cart']"
            + "//span[@class[contains(.,'no_product')]]";
    private static final String MINI_CART_QUANTITY_XPATH = "//div[@class='shopping_cart']"
            + "//span[@class[contains(.,'cart_quantity')]]";
    private static final String MINI_CART_ELEMENTS = "//dl[@class='products']//dt";
    protected final int WAIT_TIMEOUT_SECONDS = 20;
    protected final int POLLING_EVERY_SECONDS = 1;

    protected WebDriver driver;

    @FindBy(xpath = "//h1")
    protected WebElement summary;

    @FindBy(xpath = "//a[@class='login']")
    protected WebElement signInButton;

    @FindBy(xpath = "//a[@class='logout']")
    protected WebElement signOutButton;

    @FindBy(xpath = "//a[@title='View my customer account']")
    protected WebElement viewMyAccountButton;

    @FindBy(xpath = "//a[@title='Women']")
    private WebElement womenCatalogButton;

    @FindBy(xpath = "//a[@title='Contact Us']")
    protected WebElement contactUsButton;

    @FindBy(xpath = "//div[@class='shopping_cart']/a")
    private WebElement miniCart;

    @FindBy(xpath = "//dt[@class='first_item']//img")
    private WebElement miniCartFirsItemImg;

    @FindBy(xpath = "//span[@class='remove_link']")
    private WebElement miniCartRemoveButton;

    @FindBy(xpath = "//p[@class='cart-buttons']/a")
    private WebElement miniCartCheckOutButton;

    @FindBy(xpath = "//div[@class='shopping_cart']//span[@class[contains(.,'cart_quantity')]]")
    private WebElement miniCartQuantity;

    @FindBy(xpath = "//dl[@class='products']//dt")
    private List<WebElement> miniCartItems;

    @FindBy(xpath = "//div[@class='product-name']/a")
    private WebElement miniCartProductName;

    @FindBy(xpath = "//*[contains(@class, 'alert')]")
    private WebElement alert;

    protected AbstractBasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public static boolean isDisplayed(By by) {
        try {
            return EnvironmentUtils.getDriver().findElement(by).isDisplayed();
        } catch (NoSuchElementException nsee) {
            return false;
        }
    }

    public static boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException nsee) {
            return false;
        }
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

    public AuthenticationPage clickSignInButton() {
        signInButton.click();
        log.info("Go to Authentication Page");
        return new AuthenticationPage(driver);
    }

    public void clickSignOutButton() {
        signOutButton.click();
        log.info("Sign out button clicked");
    }

    public MyAccountPage openMyAccountPage() {
        viewMyAccountButton.click();
        return new MyAccountPage(driver);
    }

    public boolean isAccountVisible() {
        return !findElements(By.className("account")).isEmpty();
    }

    public WomenCatalogPage clickWomenCatalogButton() {
        driverWait().until(ExpectedConditions.elementToBeClickable(womenCatalogButton))
                .click();
        log.info("Go to Catalog Page");
        return new WomenCatalogPage(driver);
    }

    public FluentWait<WebDriver> getNewFluentWait() {
        return new FluentWait<>(driver).withTimeout(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS)).pollingEvery(Duration.ofSeconds(POLLING_EVERY_SECONDS)).ignoring(NoSuchElementException.class);
    }

    public WebElement waitForElementClickable(WebElement element) {
        return getNewFluentWait().ignoring(ElementClickInterceptedException.class).until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitForVisibilityOf(WebElement element) {
        return getNewFluentWait().ignoring(ElementNotInteractableException.class).until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForPresenceOfElement(String locator) {
        getNewFluentWait()
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    }

    public void waitForNumberOfElementsToBeLessThan(By locator, Integer number) {
        getNewFluentWait().until(ExpectedConditions.numberOfElementsToBeLessThan(locator, number));
    }

    public void scrollTo(WebElement element) {
        new Actions(driver).scrollToElement(element).perform();
    }

    public void moveTo(WebElement element) {
        new Actions(driver).moveToElement(element).perform();
    }

    public OrderSummaryPage clickToMiniCart() {
        miniCart.click();
        return new OrderSummaryPage(driver);
    }

    public void moveToMiniCart() {
        scrollTo(miniCart);
        moveTo(miniCart);
    }

    public ProductPage openFirstItemFromMiniCart() {
        miniCartFirsItemImg.click();
        return new ProductPage(driver);
    }

    public void removeFirstItemFromMiniCart() {
        int count = getMiniCartNumberOfItems();
        waitForElementClickable(miniCartRemoveButton).click();
        waitForNumberOfElementsToBeLessThan(By.xpath(MINI_CART_ELEMENTS), count);
    }

    public OrderSummaryPage clickCheckOutButtonInMiniCart() {
        miniCartCheckOutButton.click();
        return new OrderSummaryPage(driver);
    }

    public String getMiniCartTitleQuantity() {
        return miniCartQuantity.getText();
    }

    public Integer getMiniCartNumberOfItems() {
        return miniCartItems.size();
    }

    public boolean isMiniCartEmpty() {
        return !findElements(By.xpath(MINI_CART_QUANTITY_XPATH)).get(0).isDisplayed() && findElements(By.xpath(MINI_CART_EMPTY_TITLE_XPATH)).get(0).isDisplayed();
    }

    public String getMiniCartProductTitle() {
        return miniCartProductName.getAttribute("title").trim();
    }

    public Alert getPageElementAlert() {
        return new Alert(alert, driverWait());
    }

    public ContactUsPage openContactUsPage() {
        contactUsButton.click();
        return new ContactUsPage(driver);
    }

    public boolean isPageTitleValid()throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Opening the page directly is not possible");
    }

    protected String getSummary(){
        return summary.getText().replaceAll("\n.*", "").trim();
    }

}
