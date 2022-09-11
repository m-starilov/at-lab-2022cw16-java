package com.epam.at_lab_2022cw16.ui.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends AbstractPage {
    @FindBy(xpath = "//a[@title='My wishlists']")
    private WebElement myWishListsButton;

    public WishlistPage proceedToWishlist() {
        myWishListsButton.click();
        return new WishlistPage(driver);
    }

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }
}
