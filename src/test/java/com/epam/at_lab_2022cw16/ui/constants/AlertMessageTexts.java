package com.epam.at_lab_2022cw16.ui.constants;

public enum AlertMessageTexts {

    ALERT_MESSAGE_TEXT("Do you really want to delete this wishlist ?");
    private final String alertMessageText;

    AlertMessageTexts(String alertMessageText) {
        this.alertMessageText = alertMessageText;
    }

    public String getAlertMessageText() {
        return alertMessageText;
    }
}
