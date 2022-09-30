package com.epam.atlab2022cw16.ui.steps;

import com.epam.atlab2022cw16.ui.application.pages.AuthenticationPage;
import com.epam.atlab2022cw16.ui.application.pages.ComparisonPage;
import com.epam.atlab2022cw16.ui.application.pages.ContactUsPage;
import com.epam.atlab2022cw16.ui.application.pages.CreateAnAccountPage;
import com.epam.atlab2022cw16.ui.application.pages.EveningDressesCatalogPage;
import com.epam.atlab2022cw16.ui.application.pages.MyAccountPage;
import com.epam.atlab2022cw16.ui.application.pages.MyStoreHomepage;
import com.epam.atlab2022cw16.ui.application.pages.OrderAddressPage;
import com.epam.atlab2022cw16.ui.application.pages.OrderBankWirePaymentPage;
import com.epam.atlab2022cw16.ui.application.pages.OrderConfirmationPage;
import com.epam.atlab2022cw16.ui.application.pages.OrderHistoryPage;
import com.epam.atlab2022cw16.ui.application.pages.OrderPaymentPage;
import com.epam.atlab2022cw16.ui.application.pages.OrderShippingPage;
import com.epam.atlab2022cw16.ui.application.pages.OrderSummaryPage;
import com.epam.atlab2022cw16.ui.application.pages.SummerDressesCatalogPage;
import com.epam.atlab2022cw16.ui.application.pages.TShirtsCatalogPage;
import com.epam.atlab2022cw16.ui.application.pages.WishlistPage;
import com.epam.atlab2022cw16.ui.application.pages.WomenCatalogPage;
import com.epam.atlab2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonStepDefinitions {
    private final WebDriver driver = EnvironmentUtils.getDriver();

    @Then("I see {string} page title")
    public void checkPageTitle(String pageName) {
        assertThat(isCurrentTitle(pageName,driver)).isTrue();
    }

    private static boolean isCurrentTitle(String pageName, WebDriver driver) {
        boolean isTrue = false;
        switch (pageName) {
            case "Authentication":
                isTrue = new AuthenticationPage(driver).isPageTitleValid();
                break;
            case "Addresses":
                isTrue = new OrderAddressPage(driver).isPageTitleValid();
                break;
            case "Shipping":
                isTrue = new OrderShippingPage(driver).isPageTitleValid();
                break;
            case "Comparison":
                isTrue = new ComparisonPage(driver).isPageTitleValid();
                break;
            case "ContactUs":
                isTrue = new ContactUsPage(driver).isPageTitleValid();
                break;
            case "CreateAnAccount":
                isTrue = new CreateAnAccountPage(driver).isPageTitleValid();
                break;
            case "Evening Dresses":
                isTrue = new EveningDressesCatalogPage(driver).isPageTitleValid();
                break;
            case "My Account":
                isTrue = new MyAccountPage(driver).isPageTitleValid();
                break;
            case "Home page":
                isTrue = new MyStoreHomepage(driver).isPageTitleValid();
                break;
            case "Bank wire payment":
                isTrue = new OrderBankWirePaymentPage(driver).isPageTitleValid();
                break;
            case "Confirmation page":
                isTrue = new OrderConfirmationPage(driver).isPageTitleValid();
                break;
            case "Order History":
                isTrue = new OrderHistoryPage(driver).isPageTitleValid();
                break;
            case "Order Payment":
                isTrue = new OrderPaymentPage(driver).isPageTitleValid();
                break;
            case "Shopping Cart":
                isTrue = new OrderSummaryPage(driver).isPageTitleValid();
                break;
            case "Summer Dresses":
                isTrue = new SummerDressesCatalogPage(driver).isPageTitleValid();
                break;
            case "T-Shirt":
                isTrue = new TShirtsCatalogPage(driver).isPageTitleValid();
                break;
            case "Wishlist":
                isTrue = new WishlistPage(driver).isPageTitleValid();
                break;
            case "Women":
                isTrue = new WomenCatalogPage(driver).isPageTitleValid();
        }
        return isTrue;
    }


}
