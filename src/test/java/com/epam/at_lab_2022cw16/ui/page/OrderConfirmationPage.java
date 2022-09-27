package com.epam.at_lab_2022cw16.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
@Log4j2
public class OrderConfirmationPage extends AbstractOrderPage {

    @FindBy(xpath = "//div[@class='box']")
    private WebElement confirmationAllText;

    @FindBy(xpath = "//a[@title='Back to orders']")
    private WebElement backToOrdersButton;

    @FindBy(xpath = "//div[@id='center_column']/div/p/strong")
    private WebElement confirmationText;

    @FindBy(xpath = "//span[@class='price']/strong")
    private WebElement amountInformation;

    @FindBy(xpath = "//div[@class='box']/strong")
    private List<WebElement> bankAccountInformation;

    @FindBy(xpath = "//*[@id='header']//a[contains(text(),'Sign out')]")
    private WebElement signOutButton;

    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public String getConfirmationText() {
        return confirmationText.getText();
    }

    public String getAmountInformation() {
        return amountInformation.getText();
    }

    public List<String> getBankAccountInformation() {
        List<String> orderInformation = new ArrayList<>();
        for (int i = 0; i < bankAccountInformation.size() - 1; i++) {
            orderInformation.add(bankAccountInformation.get(i).getText());
        }
        return orderInformation;
    }

    public String getOrderReverence() {
        String[] strings = confirmationAllText.getText().split("\n");
        String orderReference = null;
        for (String str : strings) {
            if (str.contains("order reference")) {
                orderReference = (str.replace("- Do","")).replaceAll("[^A-Z]*", "").trim();
                break;
            }
        }
        return orderReference;
    }

    public OrderHistoryPage clickBackToOrdersButton() {
        backToOrdersButton.click();
        return new OrderHistoryPage(driver);
    }

    public void clickSignOut(){
        signOutButton.click();
        log.info("Sign out");
    }
}
