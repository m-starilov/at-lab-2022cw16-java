package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.constants.PageTitles;
import com.epam.at_lab_2022cw16.ui.page.MyAccountPage;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class MyAccountStepDefinitions {

    private final WebDriver driver = EnvironmentUtils.getDriver();

    @When("I go to Catalog page")
    public void clickCatalogButton() {
        new MyAccountPage(driver).clickWomenCatalogButton();
    }

    @When("I go to Order History page from Catalog Page")
    public void openOrderHistoryPageFromMyAccount() {
        new MyAccountPage(driver).clickOrderHistoryButton();
    }

    @Then("the My account page is opened")
    public void myAccountPageIsOpened() {
        assertThat(new MyAccountPage(driver).getTitle())
                .isEqualTo(PageTitles.MY_ACCOUNT.getPageTitle());
    }
}
