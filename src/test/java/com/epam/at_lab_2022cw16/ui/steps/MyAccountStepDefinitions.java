package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.page.MyAccountPage;
import com.epam.at_lab_2022cw16.ui.tests.bdd.hooks.DriverHooks;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

public class MyAccountStepDefinitions {

    protected static WebDriver driver = DriverHooks.getWebDriver();

    @When("I go to Catalog page")
    public void clickCatalogButton() {
        new MyAccountPage(driver).clickWomenCatalogButton();
    }
}
