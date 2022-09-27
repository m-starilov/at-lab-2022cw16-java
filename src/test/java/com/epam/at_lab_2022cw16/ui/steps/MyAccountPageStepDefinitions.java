package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.constants.PageTitles;
import com.epam.at_lab_2022cw16.ui.page.MyAccountPage;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyAccountPageStepDefinitions {

    private final WebDriver driver = EnvironmentUtils.getDriver();

    @Then("My account page opened")
    public void myAccountPageOpened() {
        assertEquals(new MyAccountPage(driver).getTitle(), PageTitles.MY_ACCOUNT_PAGE_TITLE.getPageTitle());
    }

    @When("I click \"Home\" button")
    public void userClickHomeButton() {
        new MyAccountPage(driver).proceedToHomepage();
    }

    @Then("Home page opened")
    public void homePageOpened() {
        assertEquals(new MyAccountPage(driver).getTitle(), PageTitles.HOMEPAGE_TITLE.getPageTitle());
    }
}
