package com.epam.at_lab_2022cw16.ui.tests.manual;

import com.epam.at_lab_2022cw16.ui.constants.AlertMessageTexts;
import com.epam.at_lab_2022cw16.ui.constants.ObjectNames;
import com.epam.at_lab_2022cw16.ui.constants.PageTitles;
import com.epam.at_lab_2022cw16.ui.model.User;
import com.epam.at_lab_2022cw16.ui.page.*;
import com.epam.at_lab_2022cw16.ui.page.pageElements.ProductBlock;
import com.epam.at_lab_2022cw16.ui.utils.TestListener;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TestListener.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WishlistEditTest extends AbstractBaseTest {
    private final WebDriver driver = getWebDriver();

    @Order(1)
    @Test
    public void userOpenHomepage() {
        MyStoreHomepage myStoreHomepage = new MyStoreHomepage(driver);
        myStoreHomepage.openPage();
        assertEquals(myStoreHomepage.getTitle(), PageTitles.HOMEPAGE_TITLE.getPageTitle());
    }

    @Order(2)
    @Test
    public void userLogIn() {
        MyStoreHomepage myStoreHomepage = new MyStoreHomepage(driver);
        myStoreHomepage.pressSignInButton();
        User user = new User("mikalay.murashko@gmail.com", "12345");
        AuthenticationPage authenticationPage = new AuthenticationPage(driver);
        authenticationPage.inputEmail(user.getUsername())
                .inputPassword(user.getPassword())
                .proceedToMyAccountPage();
        assertEquals(authenticationPage.getTitle(), PageTitles.MY_ACCOUNT_PAGE_TITLE.getPageTitle());
    }

    @Order(3)
    @Test
    public void userProceedToHomepage() {
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        myAccountPage.proceedToHomepage();
        assertEquals(myAccountPage.getTitle(), PageTitles.HOMEPAGE_TITLE.getPageTitle());
    }

    @Order(4)
    @Test
    public void userProceedToSummerDressCatalog() {
        MyStoreHomepage myStoreHomepage = new MyStoreHomepage(driver);
        myStoreHomepage.openSummerDressesCatalog();
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        assertEquals(myAccountPage.getTitle(), PageTitles.PAGE_WITH_SUMMER_DRESSES_TITLE.getPageTitle());
    }

    @Order(5)
    @Test
    public void userSwitchToListView() {
        WomenCatalogPage womenCatalogPage = new WomenCatalogPage(driver);
        womenCatalogPage.switchToListView();
        List<ProductBlock> products = womenCatalogPage.getProductsList();
        assertTrue(products.size() > 0);
        assertEquals(products.size(), womenCatalogPage.getListOfAddToCartButtonsNumberValue());
        assertEquals(products.size(), womenCatalogPage.getMoreButtonsNumberValue());
        assertEquals(products.size(), womenCatalogPage.getAddToWishListButtonsNumberValue());
        assertEquals(products.size(), womenCatalogPage.getAddToCompareButtonsValue());
    }

    @Order(6)
    @Test
    public void userAddItemsToCart() {
        WomenCatalogPage womenCatalogPage = new WomenCatalogPage(driver);
        List<ProductBlock> products = womenCatalogPage.getProductsList();
        products.get(0).addToWishListButtonClick();
        assertTrue(womenCatalogPage.infoBoxIsDisplayed());
        womenCatalogPage.closeInfoBox();
        assertTrue(products.get(0).isAddToWishlistSolidButtonDisplayed());
        products.get(1).addToWishListButtonClick();
        assertTrue(womenCatalogPage.infoBoxIsDisplayed());
        womenCatalogPage.closeInfoBox();
        assertTrue(products.get(1).isAddToWishlistSolidButtonDisplayed());
        products.get(2).addToWishListButtonClick();
        assertTrue(womenCatalogPage.infoBoxIsDisplayed());
        womenCatalogPage.closeInfoBox();
        assertTrue(products.get(2).isAddToWishlistSolidButtonDisplayed());
    }

    @Order(7)
    @Test
    public void userProceedToWishlistAndCheckWishlistNameAndCounter() {
        WomenCatalogPage womenCatalogPage = new WomenCatalogPage(driver);
        womenCatalogPage.proceedToMyAccountPage()
                .proceedToWishlist();
        WishlistPage wishlistPage = new WishlistPage(driver);
        assertEquals(wishlistPage.getWishlistName(), ObjectNames.MY_WISHLIST_NAME.getObjectName());
        assertEquals(wishlistPage.getItemsInWishlistCounterValue(), 3);
    }

    @Order(8)
    @Test
    public void userPressViewWishlistButton() {
        WishlistPage wishlistPage = new WishlistPage(driver);
        wishlistPage.pressViewWishlistButton();
        assertEquals(wishlistPage.getWishlistRowSize(), 3);
    }

    @Order(9)
    @Test
    public void userRemoveFirstDressFromCart() {
        WishlistPage wishlistPage = new WishlistPage(driver);
        wishlistPage.removeFirstDressFromCart();
        assertEquals(wishlistPage.getWishlistRowSize(), 2);
    }

    @Order(10)
    @Test
    public void userProceedToTShirtsCatalog() {
        WishlistPage wishlistPage = new WishlistPage(driver);
        wishlistPage.proceedToTShirtsCatalogPage();
        assertEquals(wishlistPage.getTitle(), PageTitles.PAGE_WITH_T_SHIRTS_TITLE.getPageTitle());
    }

    @Order(11)
    @Test
    public void userAddTShirtToWishlist() {
        WomenCatalogPage womenCatalogPage = new WomenCatalogPage(driver);
        womenCatalogPage.getProductsList().get(0).addToWishListButtonClick();
        assertTrue(womenCatalogPage.infoBoxIsDisplayed());
        womenCatalogPage.closeInfoBox();
        assertTrue(womenCatalogPage.getProductsList().get(0).isAddToWishlistSolidButtonDisplayed());
    }

    @Order(12)
    @Test
    public void userProceedToEveningDressesCatalog() {
        WomenCatalogPage womenCatalogPage = new WomenCatalogPage(driver);
        womenCatalogPage.proceedToEveningDressesPage();
        assertEquals(womenCatalogPage.getTitle(), PageTitles.PAGE_WITH_EVENING_DRESSES_TITLE.getPageTitle());
    }

    @Order(13)
    @Test
    public void userAddEveningDressToWishlist() {
        WomenCatalogPage womenCatalogPage = new WomenCatalogPage(driver);
        womenCatalogPage.getProductsList().get(0).addToWishListButtonClick();
        assertTrue(womenCatalogPage.infoBoxIsDisplayed());
        womenCatalogPage.closeInfoBox();
        assertTrue(womenCatalogPage.getProductsList().get(0).isAddToWishlistSolidButtonDisplayed());
    }

    @Order(14)
    @Test
    public void userProceedToWishlistPage() {
        WomenCatalogPage womenCatalogPage = new WomenCatalogPage(driver);
        womenCatalogPage.proceedToMyAccountPage()
                .proceedToWishlist();
        WishlistPage wishlistPage = new WishlistPage(driver);
        assertEquals(wishlistPage.getWishlistName(), ObjectNames.MY_WISHLIST_NAME.getObjectName());
        assertEquals(wishlistPage.getItemsInWishlistCounterValue(), 4);
    }

    @Order(15)
    @Test
    public void userOpenMyWishlistAndCheckItsSize() {
        WishlistPage wishlistPage = new WishlistPage(driver);
        wishlistPage.pressViewWishlistButton();
        assertEquals(wishlistPage.getWishlistRowSize(), 4);
    }

    @Order(16)
    @Test
    public void userDeleteWishlist() {
        WishlistPage wishlistPage = new WishlistPage(driver);
        wishlistPage.pressDeleteWishlistButton();
        assertEquals(driver.switchTo().alert().getText(), AlertMessageTexts.WISHLIST_DELETE_TEXT.getAlertMessageText());
        wishlistPage.acceptAlertMessage();
    }

    @Order(17)
    @Test
    public void userSeeNoWishlists() {
        WishlistPage wishlistPage = new WishlistPage(driver);
        assertTrue(wishlistPage.isWishListTableNotVisible());
    }
}
