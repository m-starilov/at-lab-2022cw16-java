package com.epam.at_lab_2022cw16.ui.constants;

public enum PageTitles {

    HOME("My Store"),
    LOGIN("Login - My Store"),
    MY_ACCOUNT("My account - My Store"),
    ORDER("Order - My Store"),
    WOMEN_CATALOG("Women - My Store"),
    SUMMER_DRESSES_CATALOG("Summer Dresses - My Store"),
    T_SHIRTS_CATALOG("T-shirts - My Store"),
    EVENING_DRESSES_CATALOG("Evening Dresses - My Store"),
    ORDER_HISTORY("Order history - My Store"),
    YOUR_CART("Your shopping cart"),
    CREATE_AN_ACCOUNT("CREATE AN ACCOUNT"),
    ALREADY_REGISTERED("ALREADY REGISTERED?"),
    CONTACT_US("Contact us - My Store");

    private final String pageTitle;

    PageTitles(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getPageTitle() {
        return pageTitle;
    }

}
