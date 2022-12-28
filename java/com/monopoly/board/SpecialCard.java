package com.monopoly.board;

public class SpecialCard {
    private SpecialCardTypeEnum specialCardType;
    private String specialCardTitle;
    private String specialCardDescription;
    private SpecialCardRuleEnum specialCardRule;
    private boolean goodSpecialCard;
    private int specialCardFixedCashAmount;
    private int specialCardDiceCashMultiplier;
    private int specialCardLandCashMultiplier;
    private int specialCardHouseCashMultiplier;
    private int specialCardApartmentCashMultiplier;
    private int specialCardNumberOfBlocks;
    private boolean specialCardParallelUniverse;
    private int specialCardGoToBlockNumber;
    private boolean specialCardBypassGoBlock;
    private String specialCardSoundFileName;

    public SpecialCard() {
    }

    public String getSpecialCardSoundFileName() {
        return specialCardSoundFileName;
    }

    public void setSpecialCardSoundFileName(String specialCardSoundFileName) {
        this.specialCardSoundFileName = specialCardSoundFileName;
    }

    public SpecialCardTypeEnum getSpecialCardType() {
        return specialCardType;
    }

    public void setSpecialCardType(SpecialCardTypeEnum specialCardType) {
        this.specialCardType = specialCardType;
    }

    public String getSpecialCardTitle() {
        return specialCardTitle;
    }

    public void setSpecialCardTitle(String specialCardTitle) {
        this.specialCardTitle = specialCardTitle;
    }

    public String getSpecialCardDescription() {
        return specialCardDescription;
    }

    public void setSpecialCardDescription(String specialCardDescription) {
        this.specialCardDescription = specialCardDescription;
    }

    public SpecialCardRuleEnum getSpecialCardRule() {
        return specialCardRule;
    }

    public void setSpecialCardRule(SpecialCardRuleEnum specialCardRule) {
        this.specialCardRule = specialCardRule;
    }

    public boolean isGoodSpecialCard() {
        return goodSpecialCard;
    }

    public void setGoodSpeicalCard(boolean goodSpeicalCard) {
        this.goodSpecialCard = goodSpeicalCard;
    }

    public int getSpecialCardFixedCashAmount() {
        return specialCardFixedCashAmount;
    }

    public void setSpecialCardFixedCashAmount(int specialCardFixedCashAmount) {
        this.specialCardFixedCashAmount = specialCardFixedCashAmount;
    }

    public int getSpecialCardDiceCashMultiplier() {
        return specialCardDiceCashMultiplier;
    }

    public void setSpecialCardDiceCashMultiplier(int specialCardDiceCashMultiplier) {
        this.specialCardDiceCashMultiplier = specialCardDiceCashMultiplier;
    }

    public int getSpecialCardLandCashMultiplier() {
        return specialCardLandCashMultiplier;
    }

    public void setSpecialCardLandCashMultiplier(int specialCardLandCashMultiplier) {
        this.specialCardLandCashMultiplier = specialCardLandCashMultiplier;
    }

    public int getSpecialCardHouseCashMultiplier() {
        return specialCardHouseCashMultiplier;
    }

    public void setSpecialCardHouseCashMultiplier(int specialCardHouseCashMultiplier) {
        this.specialCardHouseCashMultiplier = specialCardHouseCashMultiplier;
    }

    public int getSpecialCardApartmentCashMultiplier() {
        return specialCardApartmentCashMultiplier;
    }

    public void setSpecialCardApartmentCashMultiplier(int specialCardApartmentCashMultiplier) {
        this.specialCardApartmentCashMultiplier = specialCardApartmentCashMultiplier;
    }

    public int getSpecialCardNumberOfBlocks() {
        return specialCardNumberOfBlocks;
    }

    public void setSpecialCardNumberOfBlocks(int specialCardNumberOfBlocks) {
        this.specialCardNumberOfBlocks = specialCardNumberOfBlocks;
    }

    public boolean isSpecialCardParallelUniverse() {
        return specialCardParallelUniverse;
    }

    public void setSpecialCardParallelUniverse(boolean specialCardParallelUniverse) {
        this.specialCardParallelUniverse = specialCardParallelUniverse;
    }

    public int getSpecialCardGoToBlockNumber() {
        return specialCardGoToBlockNumber;
    }

    public void setSpecialCardGoToBlockNumber(int specialCardGoToBlockNumber) {
        this.specialCardGoToBlockNumber = specialCardGoToBlockNumber;
    }

    public boolean isSpecialCardBypassGoBlock() {
        return specialCardBypassGoBlock;
    }

    public void setSpecialCardBypassGoBlock(boolean specialCardBypassGoBlock) {
        this.specialCardBypassGoBlock = specialCardBypassGoBlock;
    }

    public String toString2() {
        return "specialCardApartmentCashMultiplier=" + specialCardApartmentCashMultiplier
                + ", specialCardBypassGoBlock=" + specialCardBypassGoBlock + ", specialCardDiceCashMultiplier="
                + specialCardDiceCashMultiplier + ", specialCardFixedCashAmount=" + specialCardFixedCashAmount
                + ", specialCardGoToBlockNumber=" + specialCardGoToBlockNumber + ", specialCardHouseCashMultiplier="
                + specialCardHouseCashMultiplier + ", specialCardLandCashMultiplier=" + specialCardLandCashMultiplier
                + ", specialCardNumberOfBlocks=" + specialCardNumberOfBlocks + ", specialCardParallelUniverse="
                + specialCardParallelUniverse;
    }

    @Override
    public String toString() {
        return "SpecialCard [" + specialCardTitle + ", " + specialCardDescription + "]\n\n";
    }

}