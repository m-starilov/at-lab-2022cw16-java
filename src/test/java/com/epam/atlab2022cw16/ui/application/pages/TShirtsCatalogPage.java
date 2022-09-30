package com.epam.atlab2022cw16.ui.application.pages;

import org.openqa.selenium.WebDriver;

public class TShirtsCatalogPage extends AbstractCatalogPage {
    private static final String PAGE_TITLE = "T-SHIRTS";

    private static final String BASE_URL = "http://automationpractice.com/index.php?id_category=5&controller=category";

    public TShirtsCatalogPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public TShirtsCatalogPage openPage() {
        driver.get(BASE_URL);
        return this;
    }

    @Override
    public boolean isPageTitleValid() {
        return getSummary().equals(PAGE_TITLE);
    }
}
