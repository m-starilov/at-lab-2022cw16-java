package com.epam.at_lab_2022cw16.ui.steps;

import com.epam.at_lab_2022cw16.ui.constants.PageTitles;
import com.epam.at_lab_2022cw16.ui.model.User;
import com.epam.at_lab_2022cw16.ui.page.MyStoreHomepage;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyStoreHomepageStepDefinitions {

    private User user;
    private final WebDriver driver = EnvironmentUtils.getDriver();

    @When("I open main page")
    public void userOpenHomepage() {
        new MyStoreHomepage(driver).openPage();
    }

    @Then("the website opens and Home page is displayed")
    public void theWebsiteOpens() {
        assertEquals(new MyStoreHomepage(driver).getTitle(), PageTitles.HOMEPAGE_TITLE.getPageTitle());
    }

    @When("I sign in with valid credentials")
    public void userSignInWithValidCredentials() {
        user = new User("mikalay.murashko@gmail.com", "12345");
        new MyStoreHomepage(driver).pressSignInButton().inputEmail(user.getUsername())
                .inputPassword(user.getPassword()).proceedToMyAccountPage();
    }

    @When("I chose \"Summer dresses\" from site top menu \\(Women>Dresses>Summer Dresses)")
    public void userChoseFromSiteTopMenuSummerDresses() {
        new MyStoreHomepage(driver).openSummerDressesCatalog();
    }


}
