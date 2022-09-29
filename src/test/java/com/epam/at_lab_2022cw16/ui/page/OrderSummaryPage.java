package com.epam.at_lab_2022cw16.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class OrderSummaryPage extends AbstractOrderPage {
    private static final String PAGE_TITLE = "SHOPPING-CART SUMMARY";

    @FindBy(xpath = "//span[@id='summary_products_quantity']")
    private WebElement summaryProductsQuantity;

    @FindBy(xpath = "//input[contains(@class, 'cart_quantity_input')]")
    private WebElement productQuantityInput;

    @FindBy(xpath = "//td[contains(@class, 'cart_quantity')]/input[@type='hidden']")
    private WebElement productQuantityHiddenInput;

    @FindBy(xpath = "//p[contains(@class, 'alert')]")
    private WebElement alertMessage;

    @FindBy(xpath = "//table//tr[@id]")
    private WebElement productInTable;

    @FindBy(xpath = "//td/p[@class='product-name']")
    private List<WebElement> productNames;

    public OrderSummaryPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean verifyPageTitle() {
        return getSummary().equals(PAGE_TITLE);
    }

    public String getSummaryProductsQuantity() {
        return summaryProductsQuantity.getText();
    }

    public int getSummaryProductsQuantityAsInt() {
        return Integer.parseInt(summaryProductsQuantity.getText().replaceAll("\\D*", "").trim());
    }

    public String getProductQuantity() {
        return productQuantityHiddenInput.getAttribute("value");
    }

    public OrderSummaryPage setProductQuantity(String qty) {
        productQuantityInput.clear();
        productQuantityInput.sendKeys(qty);
        log.info("Try to set Product quantity value to \"" + qty + "\"");
        if (qty.matches("[0-9]+")) {
            driverWait().until((ExpectedCondition<Boolean>) driver -> (productQuantityHiddenInput.getAttribute("value").equals(qty)));
        }
        return this;
    }

    public String getAlertMessage() {
        return waitForVisibilityOf(alertMessage).getText().trim();
    }

    public boolean isProductVisible() {
        return waitForVisibilityOf(productInTable).isDisplayed();
    }

    public List<String> getAddedProductNames() {
        return productNames.stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
