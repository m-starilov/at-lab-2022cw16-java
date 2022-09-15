package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.page.ComparisonPage;
import com.epam.at_lab_2022cw16.ui.tests.bdd.hooks.DriverHooks;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ComparisonPageStepDefinitions {

    protected static WebDriver driver = DriverHooks.getWebDriver();

    @Then("I see {int} items is displayed")
    public void checkNumberOfItems(int expectedNumber) {
        assertThat(new ComparisonPage(driver).getNumberOfComparableItems())
                .isEqualTo(expectedNumber);
    }

    @When("I add to Cart product with id {int}")
    public void addToCart(int id) {
        new ComparisonPage(driver).addToCartItemByID(id);
    }

    @When("I click Continue shopping button")
    public void clickContinueShoppingButton() {
        new ComparisonPage(driver).clickContinueShoppingButton();
    }

    @When("I delete from comparison item with id {int}")
    public void deleteItemFromComparison(int id) {
        new ComparisonPage(driver).deleteItemFromComparisonByID(id);
    }

    @When("I open Cart page")
    public void openCart() {
        new ComparisonPage(driver).clickViewSoppingCartButton();
    }
}
