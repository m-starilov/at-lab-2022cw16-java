package com.epam.at_lab_2022cw16.ui.constants;

public enum ObjectNames {
    MY_WISHLIST_NAME("My wishlist");
    private final String objectName;

    ObjectNames(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectName() {
        return objectName;
    }
}
