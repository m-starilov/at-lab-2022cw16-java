package com.epam.at_lab_2022cw16.ui.constants;

public class Constants {

    public static final String CHOOSE = "-- Choose --";
    public static final String CUSTOMER_SERVICE = "Customer service";
    public static final String WEBMASTER = "Webmaster";
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
        public static final String QUANTITY = "quantity:desc";
        public static final String REFERENCE_ASC = "reference:asc";
        public static final String REFERENCE_DESC = "reference:desc";
    }

    public enum Color {
        ORANGE("#F39C11;", "rgb(243, 156, 17)"),
        BEIGE("#f5f5dc;", "rgb(245, 245, 220)"),
        WHITE("#ffffff;", "rgb(255, 255, 255)"),
        BLACK("#434A54;", "rgb(67, 74, 84)"),
        BLUE("#5D9CEC;", "rgb(93, 156, 236)"),
        GREEN("#A0D468;", "rgb(160, 212, 104)"),
        YELLOW("#F1C40F;", "rgb(241, 196, 15)"),
        PINK("#FCCACD;", "rgb(252, 202, 205)"),
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
