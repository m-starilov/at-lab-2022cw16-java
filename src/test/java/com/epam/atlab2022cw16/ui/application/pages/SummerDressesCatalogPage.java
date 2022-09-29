package com.epam.atlab2022cw16.ui.application.pages;

import org.openqa.selenium.WebDriver;

public class SummerDressesCatalogPage extends AbstractCatalogPage {
    private static final String PAGE_TITLE = "SUMMER DRESSES";

    private static final String BASE_URL = "http://automationpractice.com/index.php?id_category=11&controller=category";

    public SummerDressesCatalogPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public SummerDressesCatalogPage openPage() {
        driver.get(BASE_URL);
        return this;
    }

    @Override
    public boolean isPageTitleValid() {
        return getSummary().equals(PAGE_TITLE);
    }
}
