package com.epam.at_lab_2022cw16.ui.page;

import com.epam.at_lab_2022cw16.ui.page.pageElements.ProductBlock;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogPage extends AuthenticationPage {

    private static final String ADD_TO_CART_BUTTONS = "//span[text()='Add to cart']";
    private static final String MORE_BUTTONS = "//span[text()='More']";
    private static final String ADD_TO_WISH_LIST_BUTTONS = "//a[contains(@class,'addToWishlist wishlistProd')]";
    private static final String ADD_TO_COMPARE_BUTTONS = "//a[contains(text(),'Add to Compare')]";


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

    @FindBy(xpath = "//p[contains(text(),'Added to your wishlist.')]")
    private WebElement addingConfirmationInfobox;

    @FindBy(xpath = "//a[@class='fancybox-item fancybox-close']")
    private WebElement closeInfoboxButton;

    @FindBy(xpath = "//ul[@class='product_list row list']/li")
    private List<WebElement> itemsInCatalog;

    @FindBy(xpath = "//a[contains(@class,'checked')]")
    private List<WebElement> checkedAddToWishlistSolidButtons;

    @FindBy(xpath = "//ul[contains(@class,'product_list')]/li")
    private List<WebElement> products;

    public CatalogPage(WebDriver driver) {
        super(driver);
    }

    public CatalogPage switchToListView() {
        listViewButton.click();
        return this;
    }

    public MyAccountPage proceedToMyAccountPage() {
        driverWait().until(ExpectedConditions.elementToBeClickable(myAccountPageButton)).click();
        return new MyAccountPage(driver);
    }

    public CatalogPage proceedToEveningDressesPage() {
        driverWait().until(ExpectedConditions.elementToBeClickable(womenDressesBarButton));
        new Actions(driver).moveToElement(womenDressesBarButton).build().perform();
        driverWait().until(ExpectedConditions.elementToBeClickable(eveningDressesButton)).click();
        return this;
    }

    public boolean infoBoxIsDisplayed() {
        return driverWait().until(ExpectedConditions.elementToBeClickable(addingConfirmationInfobox)).isDisplayed();
    }

    public CatalogPage closeInfoBox() {
        driverWait().until(ExpectedConditions.elementToBeClickable(closeInfoboxButton)).click();
        driverWait().until(ExpectedConditions.invisibilityOf(addingConfirmationInfobox));
        return this;
    }

    public int getListOfAddToCartButtonsNumberValue() {
        List<WebElement> listOfAllElements = driverWait()
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(ADD_TO_CART_BUTTONS)));
        return listOfAllElements.stream().filter(WebElement::isDisplayed).collect(Collectors.toList()).size();
    }

    public int getMoreButtonsNumberValue() {
        List<WebElement> listOfAllElements = driverWait()
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(MORE_BUTTONS)));
        return listOfAllElements.stream().filter(WebElement::isDisplayed).collect(Collectors.toList()).size();
    }

    public int getAddToWishListButtonsNumberValue() {
        List<WebElement> listOfAllElements = driverWait()
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(ADD_TO_WISH_LIST_BUTTONS)));
        return listOfAllElements.stream().filter(WebElement::isDisplayed).collect(Collectors.toList()).size();
    }

    public int getAddToCompareButtonsValue() {
        List<WebElement> listOfAllElements = driverWait()
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(ADD_TO_COMPARE_BUTTONS)));
        return listOfAllElements.stream().filter(WebElement::isDisplayed).collect(Collectors.toList()).size();
    }

    public List<ProductBlock> getProducts(){
        return products.stream().map(ProductBlock::new).collect(Collectors.toList());
    }
}
