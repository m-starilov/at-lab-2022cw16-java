package com.epam.at_lab_2022cw16.ui.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CatalogPage extends AuthenticationPage {

    @FindBy(xpath = "//a[@class='addToWishlist wishlistProd_4']")
    private WebElement addEveningDressToWishlistButton;
    @FindBy(xpath = "//a[@class='account']")
    private WebElement myAccountPageButton;
    @FindBy(xpath = "//a[@class='addToWishlist wishlistProd_1']")
    private WebElement addTShirtToWishlistButton;
    @FindBy(xpath = "//div[@id='block_top_menu']//a[@title='Women']")
    private WebElement womenDressesBarButton;
    @FindBy(xpath = "//li[@class]//a[@title='Evening Dresses']")
    private WebElement eveningDressesButton;
    @FindBy(xpath = "//li[@id='list']")
    private WebElement listViewButton;
    @FindBy(xpath = "//a[@class='addToWishlist wishlistProd_5']")
    private WebElement itemID5AddToWishlistButton;
    @FindBy(xpath = "//a[@class='addToWishlist wishlistProd_6']")
    private WebElement itemID6AddToWishlistButton;
    @FindBy(xpath = "//a[@class='addToWishlist wishlistProd_7']")
    private WebElement itemID7AddToWishlistButton;
    @FindBy(xpath = "//p[contains(text(),'Added to your wishlist.')]")
    private WebElement addingConfirmationInfobox;
    @FindBy(xpath = "//a[@class='fancybox-item fancybox-close']")
    private WebElement closeInfoboxButton;
    @FindBy(xpath = "//ul[@class='product_list row list']//div[@class='row']")
    private List<WebElement> itemsInCatalog;
    @FindBy(xpath = "//span[text()='Add to cart']")
    private List<WebElement> addToCartButtons;
    @FindBy(xpath = "//span[text()='More']")
    private List<WebElement> moreButtons;
    @FindBy(xpath = "//a[contains(@class,'checked')]")
    private List<WebElement> checkedAddToWishlistSolidButtons;
    @FindBy(xpath = "//a[contains(text(),'Add to Wishlist')]")
    private List<WebElement> addToWishListButtons;
    @FindBy(xpath = "//a[contains(text(),'Add to Compare')]")
    private List<WebElement> addToCompareButtons;

    public CatalogPage(WebDriver driver) {
        super(driver);
    }

    public CatalogPage switchToListView() {
        listViewButton.click();
        return this;
    }

    public CatalogPage addEveningDressToWishlist() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(addEveningDressToWishlistButton));
        addEveningDressToWishlistButton.click();
        return this;
    }

    public MyAccountPage proceedToMyAccountPage() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(myAccountPageButton));
        myAccountPageButton.click();
        return new MyAccountPage(driver);
    }

    public CatalogPage addTShirtToWishlist() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(addTShirtToWishlistButton));
        addTShirtToWishlistButton.click();
        return this;
    }

    public CatalogPage proceedToEveningDressesPage() throws InterruptedException {
        Thread.sleep(5000);
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(womenDressesBarButton));
        new Actions(driver).moveToElement(womenDressesBarButton).build().perform();
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(eveningDressesButton));
        element.click();
        return this;
    }

    public CatalogPage addFirstItemToWishlist() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(itemID5AddToWishlistButton));
        itemID5AddToWishlistButton.click();
        return this;
    }

    public CatalogPage addSecondItemToWishlist() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(itemID6AddToWishlistButton));
        itemID6AddToWishlistButton.click();
        return this;
    }

    public CatalogPage addThirdItemToWishlist() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(itemID7AddToWishlistButton));
        itemID7AddToWishlistButton.click();
        return this;
    }

    public WebElement infoBox() {
        return new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(addingConfirmationInfobox));
    }

    public CatalogPage closeInfoBox() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(closeInfoboxButton));
        closeInfoboxButton.click();
        return this;
    }

    public List<WebElement> getListOfWishlistSolidButtons() {
        return new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfAllElements(checkedAddToWishlistSolidButtons));

    }

    public List<WebElement> getListOfItemsFromCatalog() {
        return new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfAllElements(itemsInCatalog));
    }

    public List<WebElement> getListOfAddToCartButtons() {
        return new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfAllElements(addToCartButtons));
    }

    public List<WebElement> getMoreButtons() {
        return new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfAllElements(moreButtons));
    }

    public List<WebElement> addToWishListButtons() {
        return new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfAllElements(addToWishListButtons));
    }

    public List<WebElement> addToCompareButtons() {
        return new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfAllElements(addToCompareButtons));
    }
}
