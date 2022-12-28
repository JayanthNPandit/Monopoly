package com.monopoly.board;

public enum SpecialCardTypeEnum {
    CHANCE_CARD("Chance Card"), COMMUNITY_CARD("Community Card");

    private String specialCardTypeName;

    private SpecialCardTypeEnum(String aSpecialCardTypeName) {
        specialCardTypeName = aSpecialCardTypeName;
    }

    public static SpecialCardTypeEnum valueFromName(String typeName) {
        for (SpecialCardTypeEnum specialCardTypeEnum : values()) {
            if (specialCardTypeEnum.specialCardTypeName.equals(typeName)) {
                return specialCardTypeEnum;
            }
        }
        return null;
    }

    public boolean isChanceCard() {
        return this.equals(CHANCE_CARD);
    }

    public boolean isCommunityCard() {
        return this.equals(COMMUNITY_CARD);
    }

    public String getSpecialCardTypeName() {
        return specialCardTypeName;
    }
}