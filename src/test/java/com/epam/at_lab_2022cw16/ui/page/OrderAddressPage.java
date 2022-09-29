package com.epam.at_lab_2022cw16.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;

@Log4j2
public class OrderAddressPage extends AbstractOrderPage {
    private static final String PAGE_TITLE = "ADDRESSES";

    public OrderAddressPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageTitleValid() {
        return summary.getText().equals(PAGE_TITLE);
    }
}
