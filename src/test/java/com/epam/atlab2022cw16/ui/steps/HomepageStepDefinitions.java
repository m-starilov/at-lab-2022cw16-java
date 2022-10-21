package com.epam.atlab2022cw16.ui.steps;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;
import com.epam.atlab2022cw16.ui.application.modules.Alert;
import com.epam.atlab2022cw16.ui.application.pages.MyStoreHomepage;
import com.epam.atlab2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomepageStepDefinitions {

    private final WebDriver driver = EnvironmentUtils.getDriver();
    private Person person;

    @When("I open Home Page")
    public void openHomepage() {
        new MyStoreHomepage(driver).openPage();
    }

    @When("I click to Sign in button")
    public void signIn() {
        new MyStoreHomepage(driver).clickSignInButton();
    }

    @And("I click to My account button")
    public void clickToMyAccountButton() {
        new MyStoreHomepage(driver).openMyAccountPage();
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

    @When("User find field Newsletter, send email and press confirm key")
    public void userSendEmailToNewsletter() {
        MyStoreHomepage myStoreHomepage = new MyStoreHomepage(driver);
        myStoreHomepage.sendEmailToNewsletterField(person.getEmail())
                .pressSubmitNewsletterButton();
    }

    @Then("Alert success message appears: {string}")
    public void alertSuccessMessageAppears(String message) {
        Alert alert = new MyStoreHomepage(driver).getPageElementAlert();
        assertThat(alert.isSuccess())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(message);
    }

    @Then("Alert error message appears: {string}")
    public void alertErrorMessageAppears(String message) {
        Alert alert = new MyStoreHomepage(driver).getPageElementAlert();
        assertThat(alert.isDanger())
                .isTrue();
        assertThat(alert.getMessage())
                .contains(message);
    }

    @Then("Home page is opened")
    public void authenticationPageIsOpenedTitleOfPageIs() {
        assertTrue(new MyStoreHomepage(driver).isPageTitleValid());
    }
}
