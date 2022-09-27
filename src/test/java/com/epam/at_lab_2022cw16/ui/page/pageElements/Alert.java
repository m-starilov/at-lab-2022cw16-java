package com.epam.at_lab_2022cw16.ui.page.pageElements;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Log4j2
public class Alert {

    private final WebElement element;
    private final WebDriverWait wait;

    public Alert(WebElement element, WebDriverWait wait) {
        this.element = element;
        this.wait = wait;
    }

    public boolean isSuccess() {
        log.info("Success message displayed");
        return wait.until(ExpectedConditions.attributeContains(element, "class", "alert-success"));
    }

    public boolean isDanger() {
        log.info("Failure message displayed");
        return wait.until(ExpectedConditions.attributeContains(element, "class", "alert-danger"));
    }

    public boolean isDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
    }

    public String getMessage() {
        return element.getText();
    }
}
