package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.constants.AlertMessageTexts;
import com.epam.at_lab_2022cw16.ui.constants.PageTitles;
import com.epam.at_lab_2022cw16.ui.page.SummerDressesCatalogPage;
import com.epam.at_lab_2022cw16.ui.page.WishlistPage;
import com.epam.at_lab_2022cw16.ui.page.WomenCatalogPage;
import com.epam.at_lab_2022cw16.ui.page.pageElements.ProductBlock;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WishlistPageStepDefinitions {

    private final WebDriver driver = EnvironmentUtils.getDriver();

    @Then("page with Summer dresses opened")
    public void pageWithSummerDressesOpened() {
        assertEquals(new SummerDressesCatalogPage(driver).getTitle(), PageTitles.PAGE_WITH_SUMMER_DRESSES_TITLE
                .getPageTitle());
    }

    @And("in \"Add to Wishlist\" link outline heart icon changed to solid")
    public void inLinkOutlineHeartIconChangedToSolid() {
        List<ProductBlock> productList = new WomenCatalogPage(driver).getProductsList();
        assertTrue(productList.get(0).isAddToWishlistSolidButtonDisplayed());
    }

    @When("I chose \"T-shirts\" from site top menu \\(Women>Dresses>T-shirts)")
    public void userChoseFromSiteTopMenuTShirts() {
        new WishlistPage(driver).proceedToTShirtsCatalogPage();
    }

    @Then("My Wishlists page displayed with one {string} included {int} items")
    public void myWishlistsPageDisplayedWithOneIncludedItems(String wishlistName, int numberOfItems) {
        assertEquals(new WishlistPage(driver).getWishlistName(), wishlistName);
        assertEquals(new WishlistPage(driver).getItemsInWishlistCounterValue(), numberOfItems);
    }

    @Then("confirmation message with request \\(OK and Cancel) displayed")
    public void confirmationMessageWithRequestOKAndCancelDisplayed() {
        assertEquals(driver.switchTo().alert().getText(), AlertMessageTexts.WISHLIST_DELETE_TEXT.getAlertMessageText());
    }

    @When("I click \"OK\" button")
    public void userClickOKButton() {
        new WishlistPage(driver).acceptAlertMessage();
    }

    @When("I delete one item")
    public void userDeleteOneItem() {
        new WishlistPage(driver).removeFirstDressFromCart();
    }

    @Then("displayed {int} added items")
    public void displayedAddedItemsInWishlist(int numberOfItems) {
        assertEquals(new WishlistPage(driver).getWishlistRowSize(), numberOfItems);
    }

    @When("I click \"View\" link")
    public void userClickViewWishlistLink() {
        new WishlistPage(driver).pressViewWishlistButton();
    }

    @Then("page with T-shirts opened")
    public void pageWithTShirtsOpened() {
        assertEquals(new WishlistPage(driver).getTitle(), PageTitles.PAGE_WITH_T_SHIRTS_TITLE.getPageTitle());
    }

    @Then("Displayed {int} added items")
    public void displayedAddedItems(int arg0) {
        assertEquals(new WishlistPage(driver).getWishlistRowSize(), 4);
    }

    @When("I click cross icon in table")
    public void userClickCrossIconInTable() {
        new WishlistPage(driver).pressDeleteWishlistButton();
    }

    @Then("no Wishlist displayed on page")
    public void noWishlistDisplayedOnPage() {
        assertTrue(new WishlistPage(driver).isWishListTableNotVisible());
    }

    @When("I click \"My wishlist\" link")
    public void userClickMyWishlistLink() {
        new WishlistPage(driver).pressViewWishlistButton();
    }
}
