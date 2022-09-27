package com.epam.at_lab_2022cw16.ui.constants;

public enum AlertMessageTexts {

    WISHLIST_DELETE_TEXT("Do you really want to delete this wishlist ?"),
    EMPTY_CART_TEXT("Your shopping cart is empty."),
    ORDER_HISTORY_PAGE_DANGER_MESSAGE("The message cannot be blank."),
    ORDER_HISTORY_PAGE_SUCCESS_MESSAGE("Message successfully sent"),
    NEWSLETTER_SUCCESS_MESSAGE("Newsletter : You have successfully subscribed to this newsletter."),
    NEWSLETTER_FAILURE_MESSAGE("Newsletter : This email address is already registered.");


    private final String alertMessageText;

    AlertMessageTexts(String alertMessageText) {
        this.alertMessageText = alertMessageText;
    }

    public String getAlertMessageText() {
        return alertMessageText;
    }
}
