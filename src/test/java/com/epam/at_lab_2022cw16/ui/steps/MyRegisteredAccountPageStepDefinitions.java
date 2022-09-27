package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.page.MyRegisteredAccountPage;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class MyRegisteredAccountPageStepDefinitions {
    private final WebDriver driver = EnvironmentUtils.getDriver();
    MyRegisteredAccountPage myRegisteredAccountPage = new MyRegisteredAccountPage(driver);

    @Then("My account page is opened")
    public void verifyMyAccountPageIsOpened() {
        assertThat(myRegisteredAccountPage.isPageOpened()).isTrue();
    }
}
