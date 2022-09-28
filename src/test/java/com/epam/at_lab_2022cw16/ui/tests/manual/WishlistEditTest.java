package com.epam.at_lab_2022cw16.ui.tests.manual;

import com.epam.at_lab_2022cw16.annotations.JiraTicketsLink;
import com.epam.at_lab_2022cw16.ui.constants.Constants;
import com.epam.at_lab_2022cw16.ui.model.User;
import com.epam.at_lab_2022cw16.ui.page.AuthenticationPage;
import com.epam.at_lab_2022cw16.ui.page.MyAccountPage;
import com.epam.at_lab_2022cw16.ui.page.MyStoreHomepage;
import com.epam.at_lab_2022cw16.ui.page.WishlistPage;
import com.epam.at_lab_2022cw16.ui.page.WomenCatalogPage;
import com.epam.at_lab_2022cw16.ui.page.pageElements.ProductBlock;
import com.epam.at_lab_2022cw16.ui.utils.TestListener;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static com.epam.at_lab_2022cw16.ui.constants.Constants.AlertMessageTexts.WISHLIST_DELETE_TEXT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JiraTicketsLink(id = 16319,
        description = "Edit user's Wishlist test",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16319")
@ExtendWith(TestListener.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WishlistEditTest extends AbstractBaseTest {
    private final WebDriver driver = getWebDriver();

    @Order(1)
    @Test
    public void userOpenHomepage() {
        MyStoreHomepage myStoreHomepage = new MyStoreHomepage(driver);
        myStoreHomepage.openPage();
        assertTrue(myStoreHomepage.isPageTitleValid());
    }

    @Order(2)
    @Test
    public void userLogIn() {
        MyStoreHomepage myStoreHomepage = new MyStoreHomepage(driver);
        myStoreHomepage.clickSignInButton();
        User user = new User("mikalay.murashko@gmail.com", "12345");
        MyAccountPage myAccountPage = new AuthenticationPage(driver)
                .inputEmail(user.getUsername())
                .inputPassword(user.getPassword())
                .proceedToMyAccountPage();
        assertTrue(myAccountPage.isPageTitleValid());
    }

    @Order(3)
    @Test
    public void userProceedToHomepage() {
        assertTrue(new MyAccountPage(driver).proceedToHomepage().isPageTitleValid());
    }

    @Order(4)
    @Test
    public void userProceedToSummerDressCatalog() {
        assertTrue(new MyStoreHomepage(driver)
                .openSummerDressesCatalog()
                .isPageTitleValid());
    }

    @Order(5)
    @Test
    public void userSwitchToListView() {
        WomenCatalogPage womenCatalogPage = new WomenCatalogPage(driver);
        womenCatalogPage.switchToListView();
        int productsListSize = womenCatalogPage.getProductsList().size();
        assertTrue(productsListSize > 0);
        assertEquals(productsListSize, womenCatalogPage.getListOfAddToCartButtonsNumberValue());
        assertEquals(productsListSize, womenCatalogPage.getMoreButtonsNumberValue());
        assertEquals(productsListSize, womenCatalogPage.getAddToWishListButtonsNumberValue());
        assertEquals(productsListSize, womenCatalogPage.getAddToCompareButtonsValue());
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
        WishlistPage wishlistPage = new WomenCatalogPage(driver)
                .proceedToMyAccountPage()
                .proceedToWishlist();
        assertEquals(wishlistPage.getWishlistName(), Constants.MY_WISHLIST_NAME);
        assertEquals(3, wishlistPage.getItemsInWishlistCounterValue());
    }

    @Order(8)
    @Test
    public void userPressViewWishlistButton() {
        WishlistPage wishlistPage = new WishlistPage(driver)
                .pressViewWishlistButton();
        assertEquals(3, wishlistPage.getWishlistRowSize());
    }

    @Order(9)
    @Test
    public void userRemoveFirstDressFromCart() {
        WishlistPage wishlistPage = new WishlistPage(driver)
                .removeFirstDressFromCart();
        assertEquals(2, wishlistPage.getWishlistRowSize());
    }

    @Order(10)
    @Test
    public void userProceedToTShirtsCatalog() {
        assertTrue(new WishlistPage(driver)
                .proceedToTShirtsCatalogPage()
                .isPageTitleValid());
    }

    @Order(11)
    @Test
    public void userAddTShirtToWishlist() {
        WomenCatalogPage womenCatalogPage = new WomenCatalogPage(driver);
        womenCatalogPage.getProductsList().get(0)
                .addToWishListButtonClick();
        assertTrue(womenCatalogPage.infoBoxIsDisplayed());

        womenCatalogPage.closeInfoBox();
        assertTrue(womenCatalogPage.getProductsList().get(0).isAddToWishlistSolidButtonDisplayed());
    }

    @Order(12)
    @Test
    public void userProceedToEveningDressesCatalog() {
        assertTrue(new WomenCatalogPage(driver)
                .proceedToEveningDressesPage()
                .isPageTitleValid());
    }

    @Order(13)
    @Test
    public void userAddEveningDressToWishlist() {
        WomenCatalogPage womenCatalogPage = new WomenCatalogPage(driver);
        ProductBlock product = womenCatalogPage.getProductsList().get(0);
        product.addToWishListButtonClick();
        assertTrue(womenCatalogPage.infoBoxIsDisplayed());

        womenCatalogPage.closeInfoBox();
        assertTrue(product.isAddToWishlistSolidButtonDisplayed());
    }

    @Order(14)
    @Test
    public void userProceedToWishlistPage() {
        WishlistPage wishlistPage = new WomenCatalogPage(driver)
                .proceedToMyAccountPage()
                .proceedToWishlist();
        assertEquals(wishlistPage.getWishlistName(), Constants.MY_WISHLIST_NAME);
        assertEquals(4, wishlistPage.getItemsInWishlistCounterValue());
    }

    @Order(15)
    @Test
    public void userOpenMyWishlistAndCheckItsSize() {
        WishlistPage wishlistPage = new WishlistPage(driver);
        wishlistPage.pressViewWishlistButton();
        assertEquals(4, wishlistPage.getWishlistRowSize());
    }

    @Order(16)
    @Test
    public void userDeleteWishlist() {
        WishlistPage wishlistPage = new WishlistPage(driver);
        wishlistPage.pressDeleteWishlistButton();
        assertEquals(driver.switchTo().alert().getText(), WISHLIST_DELETE_TEXT);
        wishlistPage.acceptAlertMessage();
    }

    @Order(17)
    @Test
    public void userSeeNoWishlists() {
        WishlistPage wishlistPage = new WishlistPage(driver);
        assertTrue(wishlistPage.isWishListTableNotVisible());
    }
}
