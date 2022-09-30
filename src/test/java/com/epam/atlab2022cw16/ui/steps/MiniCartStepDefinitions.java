package com.epam.atlab2022cw16.ui.steps;

import com.epam.atlab2022cw16.ui.application.pages.OrderSummaryPage;
import com.epam.atlab2022cw16.ui.application.pages.ProductPage;
import com.epam.atlab2022cw16.ui.application.pages.SummerDressesCatalogPage;
import com.epam.atlab2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class MiniCartStepDefinitions {

    private final WebDriver driver = EnvironmentUtils.getDriver();

    @When("I open Summer Dresses page")
    public void open() {
        new SummerDressesCatalogPage(driver).openPage();
    }

    @Then("Catalog page was opened and list of {int} items")
    public void checkCatalogCategoryName(int quantity) {
        SummerDressesCatalogPage summerDressesCatalogPage = new SummerDressesCatalogPage(driver);
        assertThat(summerDressesCatalogPage.isPageTitleValid()).isTrue();
        assertThat(summerDressesCatalogPage.getProductsList().size()).isEqualTo(quantity);
    }

    @When("I add {int} items to cart")
    public void addTwoItems(int number) {
        new SummerDressesCatalogPage(driver).addToCartNumberOfItems(number);
    }

    @Then("I see mini cart quantity is {string}")
    public void checkMiniCartQuantity(String quantity) {
        assertThat(new SummerDressesCatalogPage(driver).getMiniCartTitleQuantity()).isEqualTo(quantity);
    }

    @When("I move cursor to mini cart")
    public void moveToMiniCart() {
        new SummerDressesCatalogPage(driver).moveToMiniCart();
    }

    @Then("I see mini cart is displayed as a block with {int} items")
    public void checkMiniCartNumberOfItems(Integer number) {
        assertThat(new SummerDressesCatalogPage(driver).getMiniCartNumberOfItems()).isEqualTo(number);
    }

    @When("I open item page from mini cart list")
    public void openFirstItemFromMiniCart() {
        new SummerDressesCatalogPage(driver).openFirstItemFromMiniCart();
    }

    @Then("Item page is opened. I see correct title")
    public void checkItemPageTitle() {
        ProductPage productPage = new ProductPage(driver);
        assertThat(productPage.getProductTitle()).isEqualTo(productPage.getMiniCartProductTitle());
    }

    @When("I delete 1 item from mini cart")
    public void removeFirstItemFromMiniCart() {
        new ProductPage(driver).removeFirstItemFromMiniCart();
    }

    @Then("I see mini cart quantity {string} and {int} item in list")
    public void checkMiniCartTitleQuantityAndNumberOfItems(String quantity, int number) {
        ProductPage productPage = new ProductPage(driver);
        assertThat(productPage.getMiniCartTitleQuantity()).isEqualTo(quantity);
        assertThat(productPage.getMiniCartNumberOfItems()).isEqualTo(number);
    }

    @When("I go to cart page from mini cart by click Check out button")
    public void clickMiniCartCheckOutButton() {
        new ProductPage(driver).clickCheckOutButtonInMiniCart();
    }

    @Then("Cart page is opened. I see shopping cart with {string} item and {string} " +
            "summary products quantity")
    public void checkOrderPageTitleAndQuantity(String quantity, String summary) {
        OrderSummaryPage orderSummaryPage = new OrderSummaryPage(driver);
        assertThat(orderSummaryPage.isPageTitleValid()).isTrue();
        assertThat(orderSummaryPage.getProductQuantity()).isEqualTo(quantity);
        assertThat(orderSummaryPage.getSummaryProductsQuantity()).isEqualTo(summary);
    }

    @Then("I see empty mini cart and cart page has {string} alert message")
    public void checkThatAllCartsAreEmpty(String alertMessage) {
        OrderSummaryPage orderSummaryPage = new OrderSummaryPage(driver);
        assertThat(orderSummaryPage.isMiniCartEmpty()).isTrue();
        assertThat(orderSummaryPage.getAlertMessage()).isEqualTo(alertMessage);
    }

}
