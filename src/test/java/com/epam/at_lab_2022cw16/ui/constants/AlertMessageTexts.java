package com.epam.at_lab_2022cw16.ui.constants;

public enum AlertMessageTexts {

    WISHLIST_DELETE_TEXT("Do you really want to delete this wishlist ?"),
    EMPTY_CART_TEXT("Your shopping cart is empty.");
    private final String alertMessageText;

    AlertMessageTexts(String alertMessageText) {
        this.alertMessageText = alertMessageText;
    }

    public String getAlertMessageText() {
        return alertMessageText;
    }
}
