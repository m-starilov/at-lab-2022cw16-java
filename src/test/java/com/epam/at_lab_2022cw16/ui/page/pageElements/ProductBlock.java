package com.epam.at_lab_2022cw16.ui.page.pageElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ProductBlock {

    private static final String ADD_TO_WISHLIST_BUTTON = ".//a[contains(@class,'addToWishlist wishlistProd')]";
    private static final String ADD_TO_WISHLIST_SOLID_BUTTON = ".//a[contains(@class,'checked')]";

    private WebElement sourceElement;

    public ProductBlock(WebElement element) {
        this.sourceElement = element;
    }

    public void addToWishListButtonClick() {
        sourceElement.findElement(By.xpath(ADD_TO_WISHLIST_BUTTON)).click();
    }

    public boolean isAddToWishlistSolidButtonDisplayed() {
        return sourceElement.findElement(By.xpath(ADD_TO_WISHLIST_SOLID_BUTTON)).isDisplayed();
    }
}
