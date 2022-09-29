package com.epam.at_lab_2022cw16.ui.page;

import com.epam.at_lab_2022cw16.ui.model.Product;
import com.epam.at_lab_2022cw16.ui.page.pageElements.ProductBlock;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Log4j2
public abstract class AbstractCatalogPage extends AbstractBasePage {

    private static final String PRODUCT = "//div[@id='center_column']/ul/li[%d]";
    private static final String ADD_TO_COMPARE_BUTTON = "//a[@class='add_to_compare'][@data-id-product='%d']";
    private static final String LOADED_CATALOG = "//ul[@style='opacity: 1;']";
    private static final String SORTING_MENU = "//select[@id='selectProductSort']";
    private static final String SORTING_TYPE = "%s/option[@value='%s']";
    private static final String COLOR_FILTER = "//input[contains(@style,'%s')]";
    private static final String PRODUCT_COLOR_LIST = "./descendant-or-self::*[@class='color_pick']";
    private static final String PRODUCT_NAME = "./descendant-or-self::h5[@itemprop='name']";
    private static final String PRODUCT_PRICE = "./descendant-or-self::*[contains(@class,'right-block')]//*[@itemprop='price']";
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

    @FindBy(xpath = "//a[contains(@class,'checked')]")
    private List<WebElement> checkedAddToWishlistSolidButtons;

    @FindBy(xpath = "//span[@class='product-name']")
    private WebElement addedToCardProductName;

    @FindBy(xpath = "//li[contains(@class,'ajax_block_product')]")
    private List<WebElement> itemsInCatalog;

    @FindBy(xpath = "//*[@id='layered_price_range']")
    private WebElement priceFilterValue;

    @FindBy(xpath = "//*[@id='layered_price_slider']/a[1]")
    private WebElement uiSlider;

    @FindBy(xpath = "//h1/span[@class='cat-name']")
    private WebElement breadCrumbs;

    @FindBy(xpath = "//div[@id='enabled_filters']//li")
    private List<WebElement> appliedFilter;

    @FindBy(xpath = "//div[@id='enabled_filters']//a[@title='Cancel']/i")
    private List<WebElement> filterDeleteButton;

    @FindBy(xpath = "//form[@class='compare-form']/button[@type='submit']")
    private WebElement compareButton;

    @FindBy(xpath = "//span[@class='available-now']")
    private WebElement available;

    @FindBy(xpath = "//span[contains(text(),'Add to cart')]")
    private List<WebElement> addToCartButtons;

    @FindBy(xpath = "//h2[contains(., 'Product successfully added to your shopping cart')]")
    private WebElement productAddedTitle;

    @FindBy(xpath = "//span/span[contains(.,'Continue shopping')]")
    private WebElement continueShoppingButton;

    @FindBy(xpath = "//span[contains(.,'Proceed to checkout')]")
    private WebElement proceedToCheckoutButton;

    public AbstractCatalogPage(WebDriver driver) {
        super(driver);
    }

    public AbstractCatalogPage switchToListView() {
        listViewButton.click();
        return this;
    }

    public MyAccountPage proceedToMyAccountPage() {
        driverWait().until(ExpectedConditions.elementToBeClickable(myAccountPageButton)).click();
        return new MyAccountPage(driver);
    }

    public EveningDressesCatalogPage proceedToEveningDressesPage() {
        driverWait().until(ExpectedConditions.elementToBeClickable(womenDressesBarButton));
        new Actions(driver).moveToElement(womenDressesBarButton).build().perform();
        driverWait().until(ExpectedConditions.elementToBeClickable(eveningDressesButton)).click();
        return new EveningDressesCatalogPage(driver);
    }

    public boolean infoBoxIsDisplayed() {
        return driverWait().until(ExpectedConditions.elementToBeClickable(addingConfirmationInfobox)).isDisplayed();
    }

    public AbstractCatalogPage closeInfoBox() {
        driverWait().until(ExpectedConditions.elementToBeClickable(closeInfoboxButton)).click();
        driverWait().until(ExpectedConditions.invisibilityOf(addingConfirmationInfobox));
        return this;
    }

    public int getListOfAddToCartButtonsNumberValue() {
        List<WebElement> listOfAllElements = driverWait()
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(ADD_TO_CART_BUTTONS)));
        return (int) listOfAllElements.stream().filter(WebElement::isDisplayed).count();
    }

    public int getMoreButtonsNumberValue() {
        List<WebElement> listOfAllElements = driverWait()
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(MORE_BUTTONS)));
        return (int) listOfAllElements.stream().filter(WebElement::isDisplayed).count();
    }

    public int getAddToWishListButtonsNumberValue() {
        List<WebElement> listOfAllElements = driverWait()
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(ADD_TO_WISH_LIST_BUTTONS)));
        return (int) listOfAllElements.stream().filter(WebElement::isDisplayed).count();
    }

    public int getAddToCompareButtonsValue() {
        List<WebElement> listOfAllElements = driverWait()
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(ADD_TO_COMPARE_BUTTONS)));
        return (int) listOfAllElements.stream().filter(WebElement::isDisplayed).count();
    }

    public List<ProductBlock> getProductsList() {
        return itemsInCatalog.stream().map(ProductBlock::new).collect(Collectors.toList());
    }

    public List<Product> getItemsInCatalog() {
        waitForPresenceOfElement(LOADED_CATALOG);
        return itemsInCatalog.stream().map(this::createProduct).collect(Collectors.toList());
    }

    public AbstractCatalogPage addToCompareItemByID(int id) {
        new Actions(driver)
                .moveToElement(findElement(By.xpath(format(PRODUCT, id))))
                .click(findElement(By.xpath(format(ADD_TO_COMPARE_BUTTON, id))))
                .build()
                .perform();
        log.info("Added product with ID=" + id);
        return this;
    }

    public AbstractCatalogPage addProductToCart(String productName) {
        WebElement product = itemsInCatalog.stream()
                .filter(element -> element.findElement(By.xpath(PRODUCT_NAME)).getText().contains(productName))
                .findFirst()
                .orElseThrow();
        new Actions(driver)
                .moveToElement(product).pause(Duration.ofSeconds(3)).moveToElement(addToCartButtons.get(0))
                .click(addToCartButtons.get(0))
                .build()
                .perform();
        log.info("Add item in cart");
        return this;
    }

    public boolean isProductAddedToCart(String productName) {
        return driverWait().until(ExpectedConditions.visibilityOf(addedToCardProductName)).getText().equals(productName);
    }

    public ComparisonPage clickCompareButton() {
        compareButton.click();
        log.info("Go to Comparison Page");
        return new ComparisonPage(driver);
    }

    public AbstractCatalogPage sortByType(String sortingType) {
        findElement(By.xpath(SORTING_MENU)).click();
        findElement(By.xpath(format(SORTING_TYPE, SORTING_MENU, sortingType))).click();
        log.info("Sort items by " + sortingType);
        return this;
    }

    public AbstractCatalogPage filterByColor(String color) {
        findElement(By.xpath(format(COLOR_FILTER, color))).click();
        waitForPresenceOfElement(LOADED_CATALOG);
        log.info("Filter items by color " + color);
        return this;
    }

    public AbstractCatalogPage filterByPrice() {
        Actions actions = new Actions(driver);
        actions.dragAndDropBy(uiSlider, 20, 0).release().build().perform();
        log.info("Filter items by price");
        return this;
    }

    public List<Double> getMinMaxPrice() {
        List<Double> rangePrice = new ArrayList<>();
        for (String price : getPriceFilterRange().split(" - ")) {
            rangePrice.add(parseDoubleFromString(price));
        }
        return rangePrice;
    }

    public String getPriceFilterRange() {
        return priceFilterValue.getText();
    }

    public AbstractCatalogPage removePriceFilter() {
        Actions actions = new Actions(driver);
        actions.dragAndDropBy(uiSlider, -20, 0).release().build().perform();
        log.info("Remove filter by price");
        return this;
    }

    public AbstractCatalogPage removeAllFilters() {
        waitForPresenceOfElement(LOADED_CATALOG);
        filterDeleteButton.forEach(WebElement::click);
        log.info("Remove all filters accept by price");
        return this;
    }

    public String[] getBreadCrumbs() {
        waitForPresenceOfElement(LOADED_CATALOG);
        return breadCrumbs.getText().split(">");
    }

    public List<String> findAppliedFilters() {
        waitForPresenceOfElement(LOADED_CATALOG);
        List<String> filters = new ArrayList<>();
        appliedFilter.forEach(element -> filters.add(element.getText().toLowerCase(Locale.ROOT)));
        return filters;
    }

    public String getBorderColor(WebElement element) {
        waitForPresenceOfElement(LOADED_CATALOG);
        return element.getCssValue("border-color");
    }

    public String getBorderColorFilterByColor(String selectedColor) {
        WebElement appliedColorFilter = findElement(By.xpath(format(COLOR_FILTER, selectedColor)));
        return getBorderColor(appliedColorFilter);
    }

    private double parseDoubleFromString(String string) {
        return Double.parseDouble(string.replace("$", "").trim());
    }

    private Product createProduct(WebElement productElement) {
        Product product = new Product();
        product.setProductName(productElement.findElement(By.xpath(PRODUCT_NAME)).getText());
        product.setProductColor(productElement.findElements(By.xpath(PRODUCT_COLOR_LIST))
                .stream().map(element -> element.getAttribute("style"))
                .collect(Collectors.toList()));
        product.setProductPrice(parseDoubleFromString(productElement.findElement(By.xpath(PRODUCT_PRICE)).getText()));
        return product;
    }

    public void addToCartNumberOfItems(int number) {
        scrollTo(available);
        for (int i = 0; i < number; i++) {
            moveTo(itemsInCatalog.get(i));
            waitForVisibilityOf(addToCartButtons.get(i)).click();
            waitForVisibilityOf(continueShoppingButton).click();
        }
        log.info(format("Added %d item(s) to the cart", number));
    }

    public void addOneItemToCart() {
        scrollTo(available);
        moveTo(itemsInCatalog.get(0));
        waitForVisibilityOf(addToCartButtons.get(0)).click();
        log.info("Added one item to the cart");
    }

    public boolean isProductAddedTitleVisible() {
        log.info("Check is product added title is visible");
        return waitForVisibilityOf(productAddedTitle).isDisplayed();
    }

    public OrderSummaryPage proceedToCheckout() {
        waitForElementClickable(proceedToCheckoutButton).click();
        log.info("Go to cart page");
        return new OrderSummaryPage(driver);
    }
}
