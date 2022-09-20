package com.epam.at_lab_2022cw16.ui.constants;

public enum SortParams {
    PRICE_ASC("price:asc"),
    PRICE_DESC("price:desc"),
    NAME_ASC("name:asc"),
    NAME_DESC("name:desc"),
    QUANTITY("quantity:desc"),
    REFERENCE_ASC("reference:asc"),
    REFERENCE_DESC("reference:desc");

    private String sortParam;

    SortParams(String sortParam) {
        this.sortParam = sortParam;
    }

    public String getSortParam() {
        return sortParam;
    }
}
