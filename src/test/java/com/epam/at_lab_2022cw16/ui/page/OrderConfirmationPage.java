package com.epam.at_lab_2022cw16.ui.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class OrderConfirmationPage extends AbstractOrderPage {

    @FindBy(xpath = "//div[@id='center_column']/div/p/strong")
    private WebElement confirmationText;

    @FindBy(xpath = "//span[@class='price']/strong")
    private WebElement amountInformation;

    @FindBy(xpath = "//div[@class='box']/strong")
    private List<WebElement> bankAccountInformation;

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

}
