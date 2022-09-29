package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.page.AuthenticationPage;
import com.epam.at_lab_2022cw16.ui.page.ComparisonPage;
import com.epam.at_lab_2022cw16.ui.page.ContactUsPage;
import com.epam.at_lab_2022cw16.ui.page.CreateAnAccountPage;
import com.epam.at_lab_2022cw16.ui.page.EveningDressesCatalogPage;
import com.epam.at_lab_2022cw16.ui.page.MyAccountPage;
import com.epam.at_lab_2022cw16.ui.page.MyStoreHomepage;
import com.epam.at_lab_2022cw16.ui.page.OrderAddressPage;
import com.epam.at_lab_2022cw16.ui.page.OrderBankWirePaymentPage;
import com.epam.at_lab_2022cw16.ui.page.OrderConfirmationPage;
import com.epam.at_lab_2022cw16.ui.page.OrderHistoryPage;
import com.epam.at_lab_2022cw16.ui.page.OrderPaymentPage;
import com.epam.at_lab_2022cw16.ui.page.OrderShippingPage;
import com.epam.at_lab_2022cw16.ui.page.OrderSummaryPage;
import com.epam.at_lab_2022cw16.ui.page.SummerDressesCatalogPage;
import com.epam.at_lab_2022cw16.ui.page.TShirtsCatalogPage;
import com.epam.at_lab_2022cw16.ui.page.WishlistPage;
import com.epam.at_lab_2022cw16.ui.page.WomenCatalogPage;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
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
