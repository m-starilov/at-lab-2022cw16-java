package com.epam.at_lab_2022cw16.ui.constants;

public enum ColorRGB {
    ORANGE("rgb(243, 156, 17)"),
    BEIGE("rgb(245, 245, 220)"),
    WHITE("rgb(255, 255, 255)"),
    BLACK("rgb(67, 74, 84)"),
    BLUE("rgb(93, 156, 236)"),
    GREEN("rgb(160, 212, 104)"),
    YELLOW("rgb(241, 196, 15)"),
    PINK("rgb(252, 202, 205)"),
    RED("rgb(255, 0, 0)");

    private final String colorRGB;

    ColorRGB(String colorRGB) {
        this.colorRGB = colorRGB;
    }

    public String getColorRGB() {
        return colorRGB;
    }
}
