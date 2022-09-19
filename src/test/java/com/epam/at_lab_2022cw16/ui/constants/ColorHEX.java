package com.epam.at_lab_2022cw16.ui.constants;

public enum ColorHEX {
    ORANGE("#F39C11;"),
    BEIGE("#f5f5dc;"),
    WHITE("#ffffff;"),
    BLACK("#434A54;"),
    BLUE("#5D9CEC;"),
    GREEN("#A0D468;"),
    YELLOW("#F1C40F;"),
    PINK("#FCCACD;"),
    RED("#ff0000");

    private final String colorHex;

    ColorHEX(String colorHex) {
        this.colorHex = colorHex;
    }

    public String getColorHex() {
        return colorHex;
    }
}
