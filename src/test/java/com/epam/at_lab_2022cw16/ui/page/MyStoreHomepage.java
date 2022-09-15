package com.epam.at_lab_2022cw16.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;

@Log4j2
public class MyStoreHomepage extends AbstractBasePage {
    private static final String HOMEPAGE = "http://automationpractice.com/index.php";

    public MyStoreHomepage(WebDriver driver) {
        super(driver);
    }

    @Override
    public MyStoreHomepage openPage() {
        driver.get(HOMEPAGE);
        log.info("Start page is opened");
        return this;
    }

    public AuthenticationPage clickSignInButton() {
        signInButton.click();
        log.info("Go to Authentication Page");
        return new AuthenticationPage(driver);
    }
}
