package com.epam.atlab2022cw16.ui.steps;

import com.epam.atlab2022cw16.ui.application.pages.MyAccountPage;
import com.epam.atlab2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

public class MyAccountStepDefinitions {

    private final WebDriver driver = EnvironmentUtils.getDriver();

    @When("I go to Catalog page")
    public void clickCatalogButton() {
        new MyAccountPage(driver).clickWomenCatalogButton();
    }

    @When("I go to Order History page from My Account Page")
    public void openOrderHistoryPageFromMyAccount() {
        new MyAccountPage(driver).clickOrderHistoryButton();
    }

    @When("I click \"Home\" button")
    public void userClickHomeButton() {
        new MyAccountPage(driver).proceedToHomepage();
    }
}
