package com.epam.at_lab_2022cw16.ui.tests.manual;

import com.epam.at_lab_2022cw16.ui.constants.Constants.AlertMessageTexts;
import com.epam.at_lab_2022cw16.ui.page.OrderSummaryPage;
import com.epam.at_lab_2022cw16.ui.page.ProductPage;
import com.epam.at_lab_2022cw16.ui.page.SummerDressesCatalogPage;
import com.epam.at_lab_2022cw16.ui.utils.TestListener;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(TestListener.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MiniShoppingCartTest extends AbstractBaseTest {
    private final WebDriver driver = getWebDriver();

    @Test
    @Order(1)
    public void openSummerDressesPageTest() {
        SummerDressesCatalogPage summerDressesCatalogPage = new SummerDressesCatalogPage(driver);
        summerDressesCatalogPage.openPage();
        assertThat(summerDressesCatalogPage.verifyPageTitle()).isTrue();
        assertThat(summerDressesCatalogPage.getProductsList().size()).isEqualTo(3);
    }

    @Test
    @Order(2)
    public void addTwoItemsToCartTest() {
        SummerDressesCatalogPage summerDressesCatalogPage = new SummerDressesCatalogPage(driver);
        summerDressesCatalogPage.addToCartNumberOfItems(2);
        assertThat(summerDressesCatalogPage.getMiniCartTitleQuantity()).isEqualTo("2");
    }

    @Test
    @Order(3)
    public void moveCursorToMiniCartOnSummerDressesCatalogPageTest() {
        SummerDressesCatalogPage summerDressesCatalogPage = new SummerDressesCatalogPage(driver);
        summerDressesCatalogPage.moveToMiniCart();
        assertThat(summerDressesCatalogPage.getMiniCartNumberOfItems()).isEqualTo(2);
    }

    @Test
    @Order(4)
    public void openItemPageFromMiniCartTest() {
        SummerDressesCatalogPage summerDressesCatalogPage = new SummerDressesCatalogPage(driver);
        ProductPage productPage = summerDressesCatalogPage.openFirstItemFromMiniCart();
        assertThat(productPage.getProductTitle()).isEqualTo(productPage.getMiniCartProductTitle());
    }

    @Test
    @Order(5)
    public void moveCursorToMiniCartOnProductPageTest() {
        ProductPage productPage = new ProductPage(driver);
        productPage.moveToMiniCart();
        assertThat(productPage.getMiniCartNumberOfItems()).isEqualTo(2);
    }

    @Test
    @Order(6)
    public void deleteOneItemFromMiniCartTest() {
        ProductPage productPage = new ProductPage(driver);
        productPage.removeFirstItemFromMiniCart();
        assertThat(productPage.getMiniCartTitleQuantity()).isEqualTo("1");
        assertThat(productPage.getMiniCartNumberOfItems()).isEqualTo(1);
    }

    @Test
    @Order(7)
    public void goToOrderSummaryPageFromMiniCartTest() {
        OrderSummaryPage orderSummaryPage = new OrderSummaryPage(driver).clickCheckOutButtonInMiniCart();
        assertThat(orderSummaryPage.verifyPageTitle()).isTrue();
        assertThat(orderSummaryPage.getProductQuantity()).isEqualTo("1");
        assertThat(orderSummaryPage.getSummaryProductsQuantity()).isEqualTo("1 Product");
    }

    @Test
    @Order(8)
    public void moveCursorToMiniCartOnItemPageTest() {
        OrderSummaryPage orderSummaryPage = new OrderSummaryPage(driver);
        orderSummaryPage.moveToMiniCart();
        assertThat(orderSummaryPage.getMiniCartNumberOfItems()).isEqualTo(1);
    }

    @Test
    @Order(9)
    public void deleteOneMoreItemFromMiniCartTest() {
        OrderSummaryPage orderSummaryPage = new OrderSummaryPage(driver);
        orderSummaryPage.removeFirstItemFromMiniCart();
        assertThat(orderSummaryPage.isMiniCartEmpty()).isTrue();
        assertThat(orderSummaryPage.getAlertMessage())
                .isEqualTo(AlertMessageTexts.EMPTY_CART_TEXT);
    }

}
