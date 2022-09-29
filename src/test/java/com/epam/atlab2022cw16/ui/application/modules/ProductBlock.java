package com.epam.atlab2022cw16.ui.application.modules;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Log4j2
public class ProductBlock {

    private static final String ADD_TO_WISHLIST_BUTTON = ".//a[contains(@class,'addToWishlist wishlistProd')]";
    private static final String ADD_TO_WISHLIST_SOLID_BUTTON = ".//a[contains(@class,'checked')]";

    private WebElement sourceElement;

    public ProductBlock(WebElement element) {
        this.sourceElement = element;
    }

    public void addToWishListButtonClick() {
        sourceElement.findElement(By.xpath(ADD_TO_WISHLIST_BUTTON)).click();
        log.info("Item has been added to wishlist");
    }

    public boolean isAddToWishlistSolidButtonDisplayed() {
        return sourceElement.findElement(By.xpath(ADD_TO_WISHLIST_SOLID_BUTTON)).isDisplayed();
    }
}
