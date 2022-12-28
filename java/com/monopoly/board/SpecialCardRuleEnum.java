package com.monopoly.board;

public enum SpecialCardRuleEnum {
    ADVANCE_TO_BLOCK("Advance-to-Block"), MOVE_FORWARD_TO_ROAD("Move-Forward-to-Road"),
    PLAYER_GETS_CASH_FROM_BANK("Player-Gets-Cash-from-Bank"), PLAYER_PAYS_CASH_TO_BANK("Player-Pays-Cash-to-Bank"),
    PLAYER_GETS_CASH_FROM_ALL_OTHER_PLAYERS("Player-Gets-Cash-from-All-Other-Players"),
    PLAYER_PAYS_CASH_TO_NEXT_PLAYER("Player-Pays-Cash-to-Next-Player"),
    PLAYER_PAYS_CASH_TO_FREE_PARKING("Pay-to-Free-Parking"), GO_TO_GO("Go-to-Go"),
    GO_BACKWARDS_TO_GO("Go-Backwards-to-Go"), MOVE_FORWARD("Move-Forward"), MOVE_BACKWARD("Move-Backward"),
    GO_DIRECTLY_TO_JAIL("Go-Directly-to-Jail");

    private String specialCardRuleName;

    private SpecialCardRuleEnum(String aSpecialCardRuleName) {
        specialCardRuleName = aSpecialCardRuleName;
    }

    public static SpecialCardRuleEnum valueFromName(String ruleName) {
        for (SpecialCardRuleEnum specialCardRuleEnum : values()) {
            if (specialCardRuleEnum.specialCardRuleName.equals(ruleName)) {
                return specialCardRuleEnum;
            }
        }
        return null;
    }

    public boolean isAdvanceToBlock() {
        return this.equals(ADVANCE_TO_BLOCK);
    }

    public boolean isMoveForwardToBlock() {
        return this.equals(MOVE_FORWARD_TO_ROAD);
    }

    public boolean isPlayerGetsCashFromBank() {
        return this.equals(PLAYER_GETS_CASH_FROM_BANK);
    }

    public boolean isPlayerPaysCashToBank() {
        return this.equals(PLAYER_PAYS_CASH_TO_BANK);
    }

    public boolean isPlayerGetsCashFromAllOtherPlayers() {
        return this.equals(PLAYER_GETS_CASH_FROM_ALL_OTHER_PLAYERS);
    }

    public boolean isPlayerPaysCashToNextPlayer() {
        return this.equals(PLAYER_PAYS_CASH_TO_NEXT_PLAYER);
    }

    public boolean isPlayerPaysCashToFreeParking() {
        return this.equals(PLAYER_PAYS_CASH_TO_FREE_PARKING);
    }

    public boolean isGoToGO() {
        return this.equals(GO_TO_GO);
    }

    public boolean isGoBackwardsToGO() {
        return this.equals(GO_BACKWARDS_TO_GO);
    }

    public boolean isMoveForward() {
        return this.equals(MOVE_FORWARD);
    }

    public boolean isMoveBackward() {
        return this.equals(MOVE_BACKWARD);
    }

    public boolean isGoDirectlyToJail() {
        return this.equals(GO_DIRECTLY_TO_JAIL);
    }

    public String getSpecialCardRuleName() {
        return specialCardRuleName;
    }

    public void setSpecialCardRuleName(String specialCardRuleName) {
        this.specialCardRuleName = specialCardRuleName;
    }
}