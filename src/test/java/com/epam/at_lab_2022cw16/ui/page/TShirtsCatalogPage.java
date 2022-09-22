package com.epam.at_lab_2022cw16.ui.page;

import org.openqa.selenium.WebDriver;

public class TShirtsCatalogPage extends AbstractCatalogPage{

    private static final String BASE_URL = "http://automationpractice.com/index.php?id_category=5&controller=category";

    public TShirtsCatalogPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public TShirtsCatalogPage openPage() {
        driver.get(BASE_URL);
        return this;
    }
}
