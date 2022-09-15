package com.epam.at_lab_2022cw16.ui.constants;

public enum PageTitles {

    HOMEPAGE_TITLE("My Store"),
    MY_ACCOUNT_PAGE_TITLE("My account - My Store"),
    PAGE_WITH_SUMMER_DRESSES_TITLE("Summer Dresses - My Store"),
    PAGE_WITH_T_SHIRTS_TITLE("T-shirts - My Store"),
    PAGE_WITH_EVENING_DRESSES_TITLE("Evening Dresses - My Store");


    private final String pageTitle;

    PageTitles(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getPageTitle() {
        return pageTitle;
    }


}
