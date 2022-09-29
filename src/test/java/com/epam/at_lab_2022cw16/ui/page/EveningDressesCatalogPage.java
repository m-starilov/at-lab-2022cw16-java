package com.epam.at_lab_2022cw16.ui.page;

import org.openqa.selenium.WebDriver;

public class EveningDressesCatalogPage extends AbstractCatalogPage{
    private static final String PAGE_TITLE = "EVENING DRESSES";
    private static final String BASE_URL = "http://automationpractice.com/index.php?id_category=10&controller=category";

    public EveningDressesCatalogPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageTitleValid() {
        return getSummary().equals(PAGE_TITLE);
    }
}

