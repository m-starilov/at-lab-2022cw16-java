package com.epam.at_lab_2022cw16.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyRegisteredAccountPage extends AbstractBasePage {
    private final String MY_ACCOUNT_PAGE_HEADER = "//*[@id=\"center_column\"]/h1";

    public MyRegisteredAccountPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageOpened() {
        return isDisplayed(By.xpath(MY_ACCOUNT_PAGE_HEADER));
    }
}
