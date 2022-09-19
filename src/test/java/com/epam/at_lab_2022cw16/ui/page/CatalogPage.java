package com.epam.at_lab_2022cw16.ui.page;

import lombok.extern.log4j.Log4j2;
import com.epam.at_lab_2022cw16.ui.model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Log4j2
public class CatalogPage extends AbstractBasePage {

    private static final String BASE_URL = "http://automationpractice.com/index.php?id_category=3&controller=category";
    private static final String PRODUCT = "//div[@id='center_column']/ul/li[%d]";
    private static final String ADD_TO_COMPARE_BUTTON = "//a[@class='add_to_compare'][@data-id-product='%d']";
    private static final String LOADED_CATALOG = "//ul[@style='opacity: 1;']";
    private static final String SORTING_MENU = "//select[@id='selectProductSort']";
    private static final String SORTING_TYPE = "%s/option[@value='%s']";
    private static final String COLOR_FILTER = "//input[contains(@style,'%s')]";
    private static final String PRODUCT_COLOR_LIST = "./descendant-or-self::*[@class='color_pick']";
    private static final String PRODUCT_NAME = "./descendant-or-self::h5[@itemprop='name']";
    private static final String PRODUCT_PRICE = "./descendant-or-self::*[contains(@class,'right-block')]//*[@itemprop='price']";

    @FindBy(xpath = "//li[contains(@class,'ajax_block_product')]")
    private List<WebElement> products;

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

    public CatalogPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public CatalogPage openPage() {
        driver.get(BASE_URL);
        return this;
    }

    public CatalogPage addToCompareItemByID(int id) {
        new Actions(driver)
                .moveToElement(findElement(By.xpath(format(PRODUCT, id))))
                .click(findElement(By.xpath(format(ADD_TO_COMPARE_BUTTON, id))))
                .build()
                .perform();
        log.info("Added product with ID=" + id);
        return this;
    }

    public ComparisonPage clickCompareButton() {
        compareButton.click();
        log.info("Go to Comparison Page");
        return new ComparisonPage(driver);
    }

    public CatalogPage sortByType(String sortingType) {
        findElement(By.xpath(SORTING_MENU)).click();
        findElement(By.xpath(format(SORTING_TYPE, SORTING_MENU, sortingType))).click();
        return this;
    }

    public CatalogPage filterByColor(String color) {
        findElement(By.xpath(format(COLOR_FILTER, color))).click();
        waitCatalogUpload();
        return this;
    }

    public CatalogPage filterByPrice() {
        Actions actions = new Actions(driver);
        actions.dragAndDropBy(uiSlider, 20, 0).release().build().perform();
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

    public CatalogPage removePriceFilter() {
        Actions actions = new Actions(driver);
        actions.dragAndDropBy(uiSlider, -20, 0).release().build().perform();
        return this;
    }

    public CatalogPage removeAllFilters() {
        waitCatalogUpload();
        filterDeleteButton.forEach(WebElement::click);
        return this;
    }

    public String[] getBreadCrumbs() {
        waitCatalogUpload();
        return breadCrumbs.getText().split(">");
    }

    public List<String> findAppliedFilters() {
        waitCatalogUpload();
        List<String> filters = new ArrayList<>();
        appliedFilter.forEach(element -> filters.add(element.getText().toLowerCase(Locale.ROOT)));
        return filters;
    }


    public String getBorderColor(WebElement element) {
        waitCatalogUpload();
        return element.getCssValue("border-color");
    }

    public String getBorderColorFilterByColor(String selectedColor) {
        WebElement appliedColorFilter = findElement(By.xpath(format(COLOR_FILTER, selectedColor)));
        return getBorderColor(appliedColorFilter);
    }

    public List<Product> getProducts() {
        waitCatalogUpload();
        List<Product> productList = new ArrayList<>();
        for (WebElement productElement : products) {
            productList.add(createProduct(productElement));
        }
        return productList;
    }

    private void waitCatalogUpload() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS)).pollingEvery(Duration.ofSeconds(2))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(LOADED_CATALOG)));
    }

    private double parseDoubleFromString(String string) {
        return Double.parseDouble(string.replace("$", "").trim());
    }

    private Product createProduct(WebElement productElement) {
        waitCatalogUpload();
        Product product = new Product();
        product.setProductName(productElement.findElement(By.xpath(PRODUCT_NAME)).getText());
        product.setProductColor(productElement.findElements(By.xpath(PRODUCT_COLOR_LIST))
                .stream().map(element -> element.getAttribute("style"))
                .collect(Collectors.toList()));
        product.setProductPrice(parseDoubleFromString(productElement.findElement(By.xpath(PRODUCT_PRICE)).getText()));
        return product;
    }
}

