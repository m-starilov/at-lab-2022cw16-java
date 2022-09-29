package com.epam.at_lab_2022cw16.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class MyAccountPage extends AbstractBasePage {

    private static final String PAGE_TITLE = "MY ACCOUNT";


    @FindBy(xpath = "//a[@title='Women']")
    private WebElement womenCatalogButton;

    @FindBy(xpath = "//a[@title='Orders']")
    private WebElement orderHistoryButton;

    @FindBy(xpath = "//a[@title='My wishlists']")
    private WebElement myWishListsButton;

    @FindBy(xpath = "//img[@class='logo img-responsive']")
    private WebElement mainLogo;

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageTitleValid() {
        return summary.getText().equals(PAGE_TITLE);
    }

    public WishlistPage proceedToWishlist() {
        myWishListsButton.click();
        log.info("My wishlist page is opened");
        return new WishlistPage(driver);
    }

    public MyStoreHomepage proceedToHomepage() {
        mainLogo.click();
        log.info("Homepage is opened");
        return new MyStoreHomepage(driver);
    }

    public WomenCatalogPage clickWomenCatalogButton() {
        driverWait().until(ExpectedConditions.elementToBeClickable(womenCatalogButton))
                .click();
        log.info("Go to Catalog Page");
        return new WomenCatalogPage(driver);
    }

    public OrderHistoryPage clickOrderHistoryButton() {
        driverWait().until(ExpectedConditions.elementToBeClickable(orderHistoryButton)).click();
        log.info("Go to Order History page");
        return new OrderHistoryPage(driver);
    }
}
