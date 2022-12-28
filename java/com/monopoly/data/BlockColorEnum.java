package com.monopoly.data;

public enum BlockColorEnum
{
    DUMMY_COLOR("N/A", 0, 0, 0), BROWN("Brown", 0, 0, 0), LIGHT_BLUE("Light Blue", 0, 0, 0), PINK("Pink", 0, 0, 0), ORANGE(
            "Orange", 0, 0,
            0), RED("Red", 0, 0, 0), YELLOW("Yellow", 0, 0, 0), GREEN("Green", 0, 0, 0), DARK_BLUE("Dark Blue", 0, 0, 0);

    private String colorName;
    private int rgbRedValue;
    private int rgbGreenValue;
    private int rgbBlueValue;

    private BlockColorEnum(String aColorName, int aRgbRedValue, int aRgbGreenValue, int aRgbBlueValue)
    {
        this.colorName = aColorName;
        this.rgbRedValue = aRgbRedValue;
        this.rgbGreenValue = aRgbGreenValue;
        this.rgbBlueValue = aRgbBlueValue;
    }

    public static BlockColorEnum valueFromName(String aColorName)
    {
        for (BlockColorEnum blockColorEnum : values())
        {
            if (blockColorEnum.colorName.equals(aColorName))
            {
                return blockColorEnum;
            }
        }
        return null;
    }

    public String getColorName()
    {
        return colorName;
    }

    public int getRgbRedValue()
    {
        return rgbRedValue;
    }

    public int getRgbGreenValue()
    {
        return rgbGreenValue;
    }

    public int getRgbBlueValue()
    {
        return rgbBlueValue;
    }
}
