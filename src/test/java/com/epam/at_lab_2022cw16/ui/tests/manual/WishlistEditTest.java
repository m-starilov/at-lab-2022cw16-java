package com.epam.at_lab_2022cw16.ui.tests.manual;

import com.epam.at_lab_2022cw16.ui.page.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class WishlistEditTest extends CommonConditions {
    private static final String HOMEPAGE_URL = "http://automationpractice.com/index.php";
    private static final String HOMEPAGE_TITLE = "My Store";
    private static final String MY_ACCOUNT_PAGE_TITLE = "My account - My Store";
    private static final String PAGE_WITH_SUMMER_DRESSES_TITLE = "Summer Dresses - My Store";
    private static final String PAGE_WITH_T_SHIRTS_TITLE = "T-shirts - My Store";
    private static final String PAGE_WITH_EVENING_DRESSES_TITLE = "Evening Dresses - My Store";
    private static final String MY_WISHLIST_NAME = "My wishlist";
    private static final String ALERT_MESSAGE_TEXT = "Do you really want to delete this wishlist ?";

    @Test(description = "browser setup")
    public void editWishlistTest() throws InterruptedException {
        MyStoreHomepage myStoreHomepage = new MyStoreHomepage(driver);
        myStoreHomepage.openPage(HOMEPAGE_URL);
        Assert.assertEquals(driver.getTitle(), HOMEPAGE_TITLE);

        myStoreHomepage.pressSignInButton();
        AuthenticationPage authenticationPage = new AuthenticationPage(driver);
        authenticationPage.inputEmail("mikalay.murashko@gmail.com")
                .inputPassword("12345")
                .proceedToMyAccountPage();
        Assert.assertEquals(driver.getTitle(), MY_ACCOUNT_PAGE_TITLE);

        MyAccountPage myAccountPage = new MyAccountPage(driver);
        myAccountPage.proceedToWishlist()
                .returnToHomePage();
        Assert.assertEquals(driver.getTitle(), HOMEPAGE_TITLE);

        CatalogPage catalogPage = new CatalogPage(driver);
        myStoreHomepage.openSummerDressesCatalog();
        Assert.assertEquals(driver.getTitle(), PAGE_WITH_SUMMER_DRESSES_TITLE);
        catalogPage.switchToListView();

        List<WebElement> rows = catalogPage.getListOfItemsFromCatalog();
        Assert.assertTrue(rows.size() > 0);
        Assert.assertEquals(rows.size(), catalogPage.getListOfAddToCartButtons().size());
        Assert.assertEquals(rows.size(), catalogPage.getMoreButtons().size());
        Assert.assertEquals(rows.size(), catalogPage.addToWishListButtons().size());
        Assert.assertEquals(rows.size(), catalogPage.addToCompareButtons().size());

        catalogPage.addFirstItemToWishlist();
        Assert.assertTrue(catalogPage.infoBox().isDisplayed());
        catalogPage.closeInfoBox()
                .addSecondItemToWishlist();
        Assert.assertTrue(catalogPage.infoBox().isDisplayed());
        catalogPage.closeInfoBox()
                .addThirdItemToWishlist();
        Assert.assertTrue(catalogPage.infoBox().isDisplayed());
        catalogPage.closeInfoBox();
        Assert.assertEquals(rows.size(), catalogPage.getListOfWishlistSolidButtons().size());
        WishlistPage wishlistPage = new WishlistPage(driver);
        catalogPage.proceedToMyAccountPage()
                .proceedToWishlist();

        Assert.assertEquals(wishlistPage.getWishlistName().getText(), MY_WISHLIST_NAME);
        Assert.assertEquals(wishlistPage.getItemsInWishlistCounter().getText(), "3");
        wishlistPage.pressViewWishlistButton();

        Assert.assertEquals(wishlistPage.getWishlistRow().size(), 3);
        wishlistPage.removeFirstDressFromCart();

        Assert.assertEquals(wishlistPage.getWishlistRow().size(), 2);
        wishlistPage.proceedToTShirtsCatalogPage();

        Assert.assertEquals(driver.getTitle(), PAGE_WITH_T_SHIRTS_TITLE);
        catalogPage.addTShirtToWishlist();

        Assert.assertTrue(catalogPage.infoBox().isDisplayed());
        catalogPage.closeInfoBox();
        Assert.assertEquals(rows.size(), catalogPage.getListOfWishlistSolidButtons().size());
        catalogPage.proceedToEveningDressesPage();

        Assert.assertEquals(driver.getTitle(), PAGE_WITH_EVENING_DRESSES_TITLE);
        catalogPage.addEveningDressToWishlist();

        Assert.assertTrue(catalogPage.infoBox().isDisplayed());
        catalogPage.closeInfoBox();
        Assert.assertEquals(rows.size(), catalogPage.getListOfWishlistSolidButtons().size());
        catalogPage.proceedToMyAccountPage()
                .proceedToWishlist();

        Assert.assertEquals(wishlistPage.getWishlistName().getText(), MY_WISHLIST_NAME);
        Assert.assertEquals(wishlistPage.getItemsInWishlistCounter().getText(), "4");

        wishlistPage.pressViewWishlistButton();
        Assert.assertEquals(wishlistPage.getWishlistRow().size(), 4);
        wishlistPage.pressDeleteWishlistButton();

        Assert.assertEquals(driver.switchTo().alert().getText(), ALERT_MESSAGE_TEXT);
        wishlistPage.acceptAlertMessage();

        Assert.assertEquals(wishlistPage.isWishListTableDisplayed().size(), 0);
    }
}
