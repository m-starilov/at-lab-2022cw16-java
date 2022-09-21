package com.epam.at_lab_2022cw16.ui.page.pageElements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Alert {

    private final WebElement element;
    private final WebDriverWait wait;

    public Alert(WebElement element, WebDriverWait wait) {
        this.element = element;
        this.wait = wait;
    }

    public boolean isSuccess() {
        return wait.until(ExpectedConditions.attributeContains(element, "class", "alert-success"));
    }

    public boolean isDanger() {
        return wait.until(ExpectedConditions.attributeContains(element, "class", "alert-danger"));
    }

    public boolean isDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
    }

    public String getMessage() {
        return element.getText();
    }
}
