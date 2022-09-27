package com.epam.at_lab_2022cw16.ui.steps;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;
import com.epam.at_lab_2022cw16.ui.constants.PageTitles;
import com.epam.at_lab_2022cw16.ui.model.User;
import com.epam.at_lab_2022cw16.ui.page.MyStoreHomepage;
import com.epam.at_lab_2022cw16.ui.page.pageElements.Alert;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyStoreHomepageStepDefinitions {

    private User user;
    private Person person;
    private final WebDriver driver = EnvironmentUtils.getDriver();

    @When("I open main page")
    public void userOpenHomepage() {
        new MyStoreHomepage(driver).openPage();
    }

    @Then("the website opens and Home page is displayed")
    public void theWebsiteOpens() {
        assertEquals(new MyStoreHomepage(driver).getTitle(), PageTitles.HOME.getPageTitle());
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

    @Given("Random email address generated using Jfairy java library.")
    public String randomEmailAddressGeneratedUsingJfairyJavaLibrary() {
        Fairy fairy = Fairy.create();
        person = fairy.person();
        return person.getEmail();
    }

    @When("User open automationpractice.com website")
    public void userOpenAutomationpracticeComWebsite() {
        new MyStoreHomepage(driver).openPage();
    }

    @Then("Website opened")
    public void websiteOpened() {
        assertThat(new MyStoreHomepage(driver).getTitle())
                .contains(PageTitles.HOME.getPageTitle());
    }

    @When("User find field Newsletter, send email and press confirm key")
    public void userSendEmailToNewsletter() {
        MyStoreHomepage myStoreHomepage = new MyStoreHomepage(driver);
        myStoreHomepage.sendEmailToNewsletterField(person.getEmail())
                .pressSubmitNewsletterButton();
    }

    @Then("Alert success message appears: {string}")
    public void alertSuccessMessageAppears(String message) {
        Alert alert = new MyStoreHomepage(driver).getNewsletterAlert();
        assertThat(alert.isSuccess())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(message);
    }

    @Then("Alert error message appears: {string}")
    public void alertErrorMessageAppears(String message) {
        Alert alert = new MyStoreHomepage(driver).getNewsletterAlert();
        assertThat(alert.isDanger())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(message);
    }
}
