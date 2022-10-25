package com.epam.atlab2022cw16.ui.application.pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

@Log4j2
public class ContactUsPage extends AbstractBasePage {

    private static final String PAGE_TITLE = "CUSTOMER SERVICE - CONTACT US";
    private static final String BASE_URL = "http://automationpractice.com/index.php?controller=contact";
    private static final String productDropdown = "//div[@id='uniform-%s_order_products']";
    private String orderValue;

    @FindBy(xpath = "//button[@id='submitMessage']")
    private WebElement submitMessageButton;

    @FindBy(xpath = "//input[@id='email']")
    private WebElement emailAddressInput;

    @FindBy(xpath = "//select[@id='id_contact']")
    private WebElement subjectHeadingDropdown;

    @FindBy(xpath = "//select[@name='id_order']")
    private WebElement orderReferenceDropdown;

    @FindBy(xpath = "//textarea[@id='message']")
    private WebElement messageInput;

    public ContactUsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public ContactUsPage openPage() {
        driver.get(BASE_URL);
        log.info("Contact Us page is opened");
        return this;
    }

    @Override
    public boolean isPageTitleValid() {
        return summary.getText().equals(PAGE_TITLE);
    }

    public ContactUsPage clickSendMessageButton() {
        submitMessageButton.click();
        log.info("Try to send message");
        return this;
    }

    public ContactUsPage inputEmail(String email) {
        emailAddressInput.clear();
        emailAddressInput.sendKeys(email);
        log.info("Email address: " + email);
        return this;
    }

    public ContactUsPage selectSubjectHeading(String name) {
        Select select = new Select(subjectHeadingDropdown);
        select.selectByVisibleText(name);
        log.info("Selected subject is " + name);
        return this;
    }

    public ContactUsPage selectOrderReference(int index) {
        Select select = new Select(orderReferenceDropdown);
        select.selectByIndex(index);
        orderValue = select.getWrappedElement().getAttribute("value");
        log.info("Select order reference");
        return this;
    }

    public boolean isProductDropdownVisible() {
        return driverWait()
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath(String.format(productDropdown, orderValue))))
                .isDisplayed();
    }

    public ContactUsPage inputMessage(String message) {
        messageInput.clear();
        messageInput.sendKeys(message);
        log.info("Message is " + message);
        return this;
    }
}
