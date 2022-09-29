package com.epam.at_lab_2022cw16.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Log4j2
public class ComparisonPage extends AbstractBasePage {

    private static final String PAGE_TITLE = "PRODUCT COMPARISON";
    private static final String PRODUCTS = "//*[@id='product_comparison']//td[contains(@class, 'ajax_block_product')]";
    private static final String PRODUCT_DELETE_BUTTON = PRODUCTS.concat("/div[@class='remove']/a[@data-id-product" +
            "='%d']");
    private static final String PRODUCT_ADD_TO_CART_BUTTON = PRODUCTS.concat("//div[@class='button-container" +
            "']/a[@data-id-product='%d']");

    @FindBy(xpath = "//span[@title='Continue shopping']")
    private WebElement continueShoppingButton;

    @FindBy(xpath = "//a[@title='View my shopping cart']")
    private WebElement cartButton;

    public ComparisonPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageTitleValid() {
        return summary.getText().equals(PAGE_TITLE);
    }

    public int getNumberOfComparableItems() {
        List<WebElement> listOfItems = findElements(By.xpath(PRODUCTS));
        if ((listOfItems.size() == 1) && (!listOfItems.get(0).isDisplayed())) {
            return 0;
        }
        return listOfItems.size();
    }

    public ComparisonPage deleteItemFromComparisonByID(int id) {
        WebElement item = findElement(By.xpath(String.format(PRODUCT_DELETE_BUTTON, id)));
        item.click();
        driverWait().until(ExpectedConditions.invisibilityOf(item));
        log.info("Item with ID=" + id + "is deleted from comparison");
        return this;
    }

    public ComparisonPage addToCartItemByID(int id) {
        findElement(By.xpath(String.format(PRODUCT_ADD_TO_CART_BUTTON, id))).click();
        log.info("Item with ID=" + id + "is added to Order");
        return this;
    }

    public ComparisonPage clickContinueShoppingButton() {
        driverWait().until(ExpectedConditions.visibilityOf(continueShoppingButton))
                .click();
        log.info("Continue shopping after adding item to Order");
        return this;
    }

    public OrderSummaryPage clickViewSoppingCartButton() {
        driverWait().until(ExpectedConditions.elementToBeClickable(cartButton))
                .click();
        log.info("Go to Order Page");
        return new OrderSummaryPage(driver);
    }
}
