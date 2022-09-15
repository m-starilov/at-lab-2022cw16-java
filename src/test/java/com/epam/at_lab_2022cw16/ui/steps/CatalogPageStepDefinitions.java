package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.page.CatalogPage;
import com.epam.at_lab_2022cw16.ui.tests.bdd.hooks.DriverHooks;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

public class CatalogPageStepDefinitions {

    protected static WebDriver driver = DriverHooks.getWebDriver();

    @When("I add to Compare product with id {int}")
    public void addItemsToCompare(int id) {
        new CatalogPage(driver).addToCompareItemByID(id);
    }

    @When("I click to Compare button")
    public void clickToCompareButton() {
        new CatalogPage(driver).clickCompareButton();
    }
}
