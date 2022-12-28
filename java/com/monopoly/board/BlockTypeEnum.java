package com.monopoly.board;

public enum BlockTypeEnum {
    DUMMY_BLOCK("Dummy Block"), LAND_BLOCK("Land Block"), ROAD_BLOCK("Road Block"), UTILITY_BLOCK("Utility Block"),
    LOTTERY_BLOCK("Lottery Block"), JAIL_BLOCK("Jail Block"), GO_TO_JAIL_BLOCK("Go to Jail Block"),
    CHANCE_BLOCK("Chance Block"), COMMUNITY_BLOCK("Community Block"), CASH_PAYMENT_BLOCK("Cash Payment Block"),
    GO_BLOCK("Go Block");

    private String blockTypeName;

    private BlockTypeEnum(String aBlockTypeName) {
        blockTypeName = aBlockTypeName;
    }

    public static BlockTypeEnum valueFromName(String typeName) {
        for (BlockTypeEnum blockTypeEnum : values()) {
            if (blockTypeEnum.blockTypeName.equals(typeName)) {
                return blockTypeEnum;
            }
        }
        return null;
    }

    public boolean isNonMonetaryBlock() {
        return this.equals(GO_BLOCK) || this.equals(GO_TO_JAIL_BLOCK) || this.equals(CHANCE_BLOCK)
                || this.equals(COMMUNITY_BLOCK) || this.equals(JAIL_BLOCK);
    }

    public boolean isFixedReceivableBlock() {
        return this.equals(LOTTERY_BLOCK);
    }

    public boolean isFixedPaymentBlock() {
        return this.equals(CASH_PAYMENT_BLOCK);
    }

    public boolean isBlockPurchasable() {
        return this.equals(LAND_BLOCK) || this.equals(ROAD_BLOCK) || this.equals(UTILITY_BLOCK);
    }

    public boolean isLotteryBlock() {
        return this.equals(LOTTERY_BLOCK);
    }

    public boolean isBlockBuildable() {
        return this.equals(LAND_BLOCK);
    }

    public boolean isBlockRentable() {
        return this.equals(LAND_BLOCK) || this.equals(ROAD_BLOCK) || this.equals(UTILITY_BLOCK);
    }

    public boolean isGovernmentBlock() {
        return this.equals(LOTTERY_BLOCK) || this.equals(JAIL_BLOCK) || this.equals(GO_TO_JAIL_BLOCK)
                || this.equals(CHANCE_BLOCK) || this.equals(COMMUNITY_BLOCK) || this.equals(CASH_PAYMENT_BLOCK)
                || this.equals(GO_BLOCK);
    }

    public boolean isChanceBlock() {
        return this.equals(CHANCE_BLOCK);
    }

    public boolean isCommunityBlock() {
        return this.equals(COMMUNITY_BLOCK);
    }

    public String getBlockTypeName() {
        return blockTypeName;
    }
}
