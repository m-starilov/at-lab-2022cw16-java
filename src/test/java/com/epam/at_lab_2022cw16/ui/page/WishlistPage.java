package com.epam.at_lab_2022cw16.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class WishlistPage extends AbstractBasePage {
    private static final String PAGE_TITLE = "MY WISHLISTS";

    @FindBy(xpath = "//a[contains(text(), 'View')]")
    private WebElement viewWishlistButton;

    @FindBy(xpath = "//li[@id='wlp_5_0']//i[@class='icon-remove-sign']")
    private WebElement removeFirstDressFromWishlistButton;

    @FindBy(xpath = "//a[@title='Women']")
    private WebElement womenDressesBarButton;

    @FindBy(xpath = "//li[@class]//a[@title='T-shirts']")
    private WebElement tShirtsButton;

    @FindBy(xpath = "//a[@class='icon']")
    private WebElement deleteWishlistButton;

    @FindBy(xpath = "//a[contains(text(),'My wishlist')]")
    private WebElement wishlistName;

    @FindBy(xpath = "//td[@class='bold align_center']")
    private WebElement itemsInWishlistCounter;

    @FindBy(xpath = "//div[@id='block-history']")
    private WebElement wishlistTable;

    public WishlistPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean verifyPageTitle() {
        return getSummary().equals(PAGE_TITLE);
    }

    public WishlistPage pressViewWishlistButton() {
        driverWait().until(ExpectedConditions.elementToBeClickable(viewWishlistButton)).click();
        log.info("Press view wishlist button");
        return this;
    }

    public WishlistPage removeFirstDressFromCart() {
        driverWait().until(ExpectedConditions.elementToBeClickable(removeFirstDressFromWishlistButton)).click();
        driverWait().until(ExpectedConditions.invisibilityOf(removeFirstDressFromWishlistButton));
        log.info("First dress has removed from catalog");
        return this;
    }

    public TShirtsCatalogPage proceedToTShirtsCatalogPage() {
        new Actions(driver).moveToElement(womenDressesBarButton).build().perform();
        tShirtsButton.click();
        log.info("Press T-Shirt catalog button");
        return new TShirtsCatalogPage(driver);
    }

    public WishlistPage pressDeleteWishlistButton() {
        deleteWishlistButton.click();
        log.info("Press delete wishlist button");
        return this;
    }

    public WishlistPage acceptAlertMessage() {
        driver.switchTo().alert().accept();
        driverWait().until(ExpectedConditions.invisibilityOf(deleteWishlistButton));
        log.info("Alert message has been accepted");
        return this;
    }

    public String getWishlistName() {
        log.info("Alert message has been accepted");
        return driverWait().until(ExpectedConditions.elementToBeClickable(wishlistName)).getText();
    }

    public int getItemsInWishlistCounterValue() {
        return Integer.parseInt(driverWait().until(ExpectedConditions.elementToBeClickable(itemsInWishlistCounter)).getText());
    }

    public int getWishlistRowSize() {
        List<WebElement> listOfAllElements = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions
                        .presenceOfAllElementsLocatedBy(By.xpath("//ul[@class='row wlp_bought_list']//li")));
        return listOfAllElements.stream().filter(WebElement::isDisplayed).collect(Collectors.toList()).size();
    }

    public boolean isWishListTableNotVisible() {
        return driverWait().until(ExpectedConditions.invisibilityOf(wishlistTable));
    }

    public int getWishListTableSize() {
        List<WebElement> listOfAllElements = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions
                        .presenceOfAllElementsLocatedBy(By.xpath("//div[@id='block-history']")));
        return listOfAllElements.stream().filter(WebElement::isDisplayed).collect(Collectors.toList()).size();
    }
}
