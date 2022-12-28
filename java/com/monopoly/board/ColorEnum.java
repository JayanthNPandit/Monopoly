package com.monopoly.board;

import java.util.ArrayList;

public enum ColorEnum {
    DUMMY_COLOR(0, "Dummy", 1, false), BROWN(1, "Brown", 2, false), LIGHT_BLUE(2, "Light Blue", 3, true),
    PINK(3, "Pink", 3, true), ORANGE(4, "Orange", 3, true), RED(5, "Red", 3, true), YELLOW(6, "Yellow", 3, false),
    GREEN(7, "Green", 3, true), DARK_BLUE(8, "Dark Blue", 2, true);

    private int colorId;
    private String colorName;
    private int monopolyCardCount;
    private boolean goodVisualColor;

    private ColorEnum(int aColorId, String aColorName, int aMonopolyCardCount, boolean aGoodVisualColor) {
        colorId = aColorId;
        colorName = aColorName;
        monopolyCardCount = aMonopolyCardCount;
        goodVisualColor = aGoodVisualColor;
    }

    public static ColorEnum valueFromName(String colorName) {
        for (ColorEnum colorEnum : values()) {
            if (colorEnum.getColorName().equals(colorName)) {
                return colorEnum;
            }
        }
        return null;
    }

    public int getColorId() {
        return colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public int getMonopolyCardCount() {
        return monopolyCardCount;
    }

    public boolean isVisualColorGood() {
        return this.goodVisualColor;
    }

    public static ArrayList<ColorEnum> getGoodVisualColors() {
        ArrayList<ColorEnum> goodColors = new ArrayList<ColorEnum>();
        for (ColorEnum color : ColorEnum.values()) {
            if (color.isVisualColorGood() == true) {
                goodColors.add(color);
            }
        }

        return goodColors;
    }
}
