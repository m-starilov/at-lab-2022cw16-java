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

public class WishlistPage extends AbstractPage {
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
    @FindBy(xpath = "//img[@class='logo img-responsive']")
    private WebElement mainLogo;
    @FindBy(xpath = "//a[contains(text(),'My wishlist')]")
    private WebElement wishlistName;
    @FindBy(xpath = "//td[@class='bold align_center']")
    private WebElement itemsInWishlistCounter;
    @FindBy(xpath = "//div[@id='block-history']")
    private WebElement wishlistTable;

    public WishlistPage pressViewWishlistButton() {
        viewWishlistButton.click();
        return this;
    }

    public WishlistPage removeFirstDressFromCart() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(removeFirstDressFromWishlistButton));
        removeFirstDressFromWishlistButton.click();
        return this;
    }

    public CatalogPage proceedToTShirtsCatalogPage() {
        new Actions(driver).moveToElement(womenDressesBarButton).build().perform();
        tShirtsButton.click();
        return new CatalogPage(driver);
    }

    public WishlistPage pressDeleteWishlistButton() {
        deleteWishlistButton.click();
        return this;
    }

    public WishlistPage acceptAlertMessage() {
        driver.switchTo().alert().accept();
        return this;
    }

    public MyStoreHomepage returnToHomePage() {
        mainLogo.click();
        return new MyStoreHomepage(driver);
    }

    public WebElement getWishlistName() {
        return new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(wishlistName));
    }

    public WebElement getItemsInWishlistCounter() {
        return new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(itemsInWishlistCounter));
    }

    public List<WebElement> getWishlistRow() throws InterruptedException {
        Thread.sleep(3000);
        List<WebElement> listOfAllElements = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions
                        .presenceOfAllElementsLocatedBy(By.xpath("//ul[@class='row wlp_bought_list']//li")));
        return listOfAllElements.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
    }

    public WebElement getWishlistTable() {
        return new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(wishlistTable));
    }

    public List<WebElement> isWishListTableDisplayed() throws InterruptedException {
        Thread.sleep(5000);
        return driver.findElements(By.xpath("//div[@id='block-history']"));
    }

    public WishlistPage(WebDriver driver) {
        super(driver);
    }
}
