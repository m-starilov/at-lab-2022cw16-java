package com.epam.at_lab_2022cw16.ui.tests.manual;

import com.epam.at_lab_2022cw16.ui.constants.AlertMessageTexts;
import com.epam.at_lab_2022cw16.ui.constants.ObjectNames;
import com.epam.at_lab_2022cw16.ui.constants.PageTitles;
import com.epam.at_lab_2022cw16.ui.model.User;
import com.epam.at_lab_2022cw16.ui.page.*;
import com.epam.at_lab_2022cw16.ui.page.pageElements.ProductBlock;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WishlistEditTest extends AbstractBaseTest {

    private static final String HOMEPAGE_URL = "http://automationpractice.com/index.php";

    @Order(1)
    @Test
    public void userOpenHomepage() {
        MyStoreHomepage myStoreHomepage = new MyStoreHomepage(driver);
        myStoreHomepage.openPage();
        assertEquals(myStoreHomepage.getTitle(driver), PageTitles.HOMEPAGE_TITLE.getPageTitle());
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
        assertEquals(authenticationPage.getTitle(driver), PageTitles.MY_ACCOUNT_PAGE_TITLE.getPageTitle());
    }

    @Order(3)
    @Test
    public void userProceedToHomepage() {
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        myAccountPage.proceedToHomepage();
        assertEquals(myAccountPage.getTitle(driver), PageTitles.HOMEPAGE_TITLE.getPageTitle());
    }

    @Order(4)
    @Test
    public void userProceedToSummerDressCatalog() {
        MyStoreHomepage myStoreHomepage = new MyStoreHomepage(driver);
        myStoreHomepage.openSummerDressesCatalog();
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        assertEquals(myAccountPage.getTitle(driver), PageTitles.PAGE_WITH_SUMMER_DRESSES_TITLE.getPageTitle());
    }

    @Order(5)
    @Test
    public void userSwitchToListView() {
        CatalogPage catalogPage = new CatalogPage(driver);
        catalogPage.switchToListView();
        List<ProductBlock> products = catalogPage.getProducts();
        assertTrue(products.size() > 0);
        assertEquals(products.size(), catalogPage.getListOfAddToCartButtonsNumberValue());
        assertEquals(products.size(), catalogPage.getMoreButtonsNumberValue());
        assertEquals(products.size(), catalogPage.getAddToWishListButtonsNumberValue());
        assertEquals(products.size(), catalogPage.getAddToCompareButtonsValue());
    }

    @Order(6)
    @Test
    public void userAddItemsToCart() {
        CatalogPage catalogPage = new CatalogPage(driver);
        List<ProductBlock> products = catalogPage.getProducts();
        products.get(0).addToWishListButtonClick();
        assertTrue(catalogPage.infoBoxIsDisplayed());
        catalogPage.closeInfoBox();
        assertTrue(products.get(0).isAddToWishlistSolidButtonDisplayed());
        products.get(1).addToWishListButtonClick();
        assertTrue(catalogPage.infoBoxIsDisplayed());
        catalogPage.closeInfoBox();
        assertTrue(products.get(1).isAddToWishlistSolidButtonDisplayed());
        products.get(2).addToWishListButtonClick();
        assertTrue(catalogPage.infoBoxIsDisplayed());
        catalogPage.closeInfoBox();
        assertTrue(products.get(2).isAddToWishlistSolidButtonDisplayed());
    }

    @Order(7)
    @Test
    public void userProceedToWishlistAndCheckWishlistNameAndCounter() {
        CatalogPage catalogPage = new CatalogPage(driver);
        catalogPage.proceedToMyAccountPage()
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
        assertEquals(wishlistPage.getTitle(driver), PageTitles.PAGE_WITH_T_SHIRTS_TITLE.getPageTitle());
    }

    @Order(11)
    @Test
    public void userAddTShirtToWishlist() {
        CatalogPage catalogPage = new CatalogPage(driver);
        catalogPage.getProducts().get(0).addToWishListButtonClick();
        assertTrue(catalogPage.infoBoxIsDisplayed());
        catalogPage.closeInfoBox();
        assertTrue(catalogPage.getProducts().get(0).isAddToWishlistSolidButtonDisplayed());
    }

    @Order(12)
    @Test
    public void userProceedToEveningDressesCatalog() {
        CatalogPage catalogPage = new CatalogPage(driver);
        catalogPage.proceedToEveningDressesPage();
        assertEquals(catalogPage.getTitle(driver), PageTitles.PAGE_WITH_EVENING_DRESSES_TITLE.getPageTitle());
    }

    @Order(13)
    @Test
    public void userAddEveningDressToWishlist() {
        CatalogPage catalogPage = new CatalogPage(driver);
        catalogPage.getProducts().get(0).addToWishListButtonClick();
        assertTrue(catalogPage.infoBoxIsDisplayed());
        catalogPage.closeInfoBox();
        assertTrue(catalogPage.getProducts().get(0).isAddToWishlistSolidButtonDisplayed());
    }

    @Order(14)
    @Test
    public void userProceedToWishlistPage() {
        CatalogPage catalogPage = new CatalogPage(driver);
        catalogPage.proceedToMyAccountPage()
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
        assertEquals(driver.switchTo().alert().getText(), AlertMessageTexts.ALERT_MESSAGE_TEXT.getAlertMessageText());
        wishlistPage.acceptAlertMessage();
    }

    @Order(17)
    @Test
    public void userSeeNoWishlists() {
        WishlistPage wishlistPage = new WishlistPage(driver);
        assertEquals(wishlistPage.isWishListTableDisplayed().size(), 0);
    }
}
