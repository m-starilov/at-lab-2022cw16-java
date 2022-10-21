package com.epam.atlab2022cw16.ui.application.constants;

public class Constants {

    public static final String CUSTOMER_SERVICE = "Customer service";
    public static final String MY_WISHLIST_NAME = "My wishlist";
    public static final String CREATE_AN_ACCOUNT = "CREATE AN ACCOUNT";
    public static final String ALREADY_REGISTERED = "ALREADY REGISTERED?";

    public static class AlertMessageTexts {

        public static final String WISHLIST_DELETE_TEXT = "Do you really want to delete this wishlist ?";
        public static final String EMPTY_CART_TEXT = "Your shopping cart is empty.";
        public static final String EMAIL_REQUIRED = "An email address required.";
        public static final String AUTH_FAIL = "Authentication failed.";
        public static final String ORDER_HISTORY_PAGE_DANGER_MESSAGE = "The message cannot be blank.";
        public static final String ORDER_HISTORY_PAGE_SUCCESS_MESSAGE = "Message successfully sent";
        public static final String NEWSLETTER_SUCCESS_MESSAGE = "Newsletter : You have successfully subscribed to this newsletter.";
        public static final String NEWSLETTER_FAILURE_MESSAGE = "Newsletter : This email address is already registered.";
        public static final String CONTACT_US_PAGE_INVALID_EMAIL_MESSAGE = "Invalid email address.";
        public static final String CONTACT_US_PAGE_BLANK_MESSAGE = "The message cannot be blank.";
        public static final String CONTACT_US_PAGE_NO_SUBJECT_MESSAGE = "Please select a subject from the list provided.";
        public static final String CONTACT_US_PAGE_SUCCESS_MESSAGE = "Your message has been successfully sent to our team.";

    }

    public static class SortParams {
        public static final String PRICE_ASC = "price:asc";
        public static final String PRICE_DESC = "price:desc";
        public static final String NAME_ASC = "name:asc";
        public static final String NAME_DESC = "name:desc";
    }

    public enum Color {
        YELLOW("#F1C40F;", "rgb(241, 196, 15)"),
        RED("#ff0000", "rgb(255, 0, 0)"),
        RED_ALERT("#f13340", "");

        private final String colorHex;
        private final String colorRGB;

        Color(String colorHex, String colorRGB) {
            this.colorHex = colorHex;
            this.colorRGB = colorRGB;
        }

        public String getColorHex() {
            return colorHex;
        }

        public String getColorRGB() {
            return colorRGB;
        }
    }

}
