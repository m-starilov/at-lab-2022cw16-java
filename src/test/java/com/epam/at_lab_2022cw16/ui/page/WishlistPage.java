package com.epam.at_lab_2022cw16.ui.page;

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

public class WishlistPage extends AbstractBasePage {

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

    public WishlistPage pressViewWishlistButton() {
        driverWait().until(ExpectedConditions.elementToBeClickable(viewWishlistButton)).click();
        return this;
    }

    public WishlistPage removeFirstDressFromCart() {
        driverWait().until(ExpectedConditions.elementToBeClickable(removeFirstDressFromWishlistButton)).click();
        driverWait().until(ExpectedConditions.invisibilityOf(removeFirstDressFromWishlistButton));
        return this;
    }

    public AbstractCatalogPage proceedToTShirtsCatalogPage() {
        new Actions(driver).moveToElement(womenDressesBarButton).build().perform();
        tShirtsButton.click();
        return new TShirtsCatalogPage(driver);
    }

    public WishlistPage pressDeleteWishlistButton() {
        deleteWishlistButton.click();
        return this;
    }

    public WishlistPage acceptAlertMessage() {
        driver.switchTo().alert().accept();
        driverWait().until(ExpectedConditions.invisibilityOf(deleteWishlistButton));
        return this;
    }

    public String getWishlistName() {
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

    public List<WebElement> isWishListTableDisplayed() {
        return driver.findElements(By.xpath("//div[@id='block-history']"));
    }

}
