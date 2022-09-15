package com.epam.at_lab_2022cw16.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import static java.lang.String.format;

@Log4j2
public class CatalogPage extends AbstractBasePage {

    private static final String BASE_URL = "http://automationpractice.com/index.php?id_category=3&controller=category";
    private static final String PRODUCT = "//div[@id='center_column']/ul/li[%d]";
    private static final String ADD_TO_COMPARE_BUTTON = "//a[@class='add_to_compare'][@data-id-product='%d']";

    @FindBy(xpath = "//form[@class='compare-form']/button[@type='submit']")
    private WebElement compareButton;

    public CatalogPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public CatalogPage openPage() {
        driver.get(BASE_URL);
        return this;
    }

    public CatalogPage addToCompareItemByID(int id) {
        new Actions(driver)
                .moveToElement(findElement(By.xpath(format(PRODUCT, id))))
                .click(findElement(By.xpath(format(ADD_TO_COMPARE_BUTTON, id))))
                .build()
                .perform();
        log.info("Added product with ID=" + id);
        return this;
    }

    public ComparisonPage clickCompareButton() {
        compareButton.click();
        log.info("Go to Comparison Page");
        return new ComparisonPage(driver);
    }
}
