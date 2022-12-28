package com.monopoly.board;

import java.util.ArrayList;

import com.monopoly.player.FakePlayer;
import com.monopoly.player.Player;

public abstract class Block {
    private int blockNumber;
    private String blockName;
    private BlockTypeEnum blockType;
    private String blockDisplayDetails;
    private ColorEnum blockColor;
    private int blockPurchaseCost;
    private int blockHousePurchaseCost;
    private int blockApartmentPurchaseCost;
    private int blockLandRent;
    private int blockHouseRent;
    private int blockApartmentRent;
    private int blockMaxHouses;
    private int blockMaxApartments;
    private int blockMortgageValue;
    private int blockSingleUtilityDiceRent;
    private int blockMultipleUtilityDiceRent;
    private int blockSingleRoadBlockRent;
    private int blockMultipleRoadBlockRent;
    private int blockCashAmounts;
    private String soundFileName;

    // TODO: make private
    public ArrayList<Player> playersOnBlock;

    public Block(int aBlockNumber) {
        blockNumber = aBlockNumber;
        playersOnBlock = new ArrayList<Player>();
    }

    public String getSoundFileName() {
        return soundFileName;
    }

    public void setSoundFileName(String soundFileName) {
        this.soundFileName = soundFileName;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public void addPlayerToBlock(Player movingPlayer) {
        this.playersOnBlock.add(movingPlayer);
    }

    public void removePlayerFromBlock(Player movingPlayer) {
        this.playersOnBlock.remove(movingPlayer);
    }

    public int getNumberOfPlayersInThisBlock() {
        // - 1 for the unneccessary Fake Player in index 0
        return this.playersOnBlock.size() - 1;
    }

    public boolean isPlayerInThisBlock(int playerNumber) {
        for (Player player : this.playersOnBlock) {
            if (player.getPlayerNumber() == playerNumber) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        if ((this instanceof FakeBlock) == false) {
            return "Block: BlockNumber = " + blockNumber + ", blockName = " + blockName + ", blockType = " + blockType
                    + ", blockColor = " + blockColor + ", \n\t\tplayersOnBlock = " + displayPlayersOnBlock();
        } else {
            return "";
        }
    }

    private String displayPlayersOnBlock() {
        String playerNames = "[";

        for (Player player : this.playersOnBlock) {
            if (player instanceof FakePlayer) {
                // Don't display fake player
            } else {
                playerNames += "" + player.getName() + ", ";
            }
        }
        if (playerNames.length() > 2) {
            playerNames = playerNames.substring(0, playerNames.length() - 2);
        } else {
            // No adjustment necessary
        }
        playerNames += "]";

        return playerNames;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public boolean isLandBlock() {
        return BlockTypeEnum.LAND_BLOCK.equals(this.getBlockType());
    }

    public boolean isRoadBlock() {
        return BlockTypeEnum.ROAD_BLOCK.equals(this.getBlockType());
    }

    public boolean isUtilityBlock() {
        return BlockTypeEnum.UTILITY_BLOCK.equals(this.getBlockType());
    }

    public boolean isJailBlock() {
        return BlockTypeEnum.JAIL_BLOCK.equals(this.getBlockType());
    }

    public boolean isBlockBuildable() {
        return this.getBlockType().isBlockBuildable();
    }

    public boolean isNonMonetaryBlock() {
        return this.getBlockType().isNonMonetaryBlock();
    }

    public boolean isFixedReceivableBlock() {
        return this.getBlockType().isFixedReceivableBlock();
    }

    public boolean isFixedPaymentBlock() {
        return this.getBlockType().isFixedPaymentBlock();
    }

    public boolean isBlockPurchasable() {
        return this.getBlockType().isBlockPurchasable();
    }

    public boolean isBlockRentable() {
        return this.getBlockType().isBlockRentable();
    }

    public boolean isLotteryBlock() {
        return this.getBlockType().isLotteryBlock();
    }

    public boolean isGovernmentBlock() {
        return this.getBlockType().isGovernmentBlock();
    }

    public BlockTypeEnum getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockTypeEnum blockType) {
        this.blockType = blockType;
    }

    public String getBlockDisplayDetails() {
        return blockDisplayDetails;
    }

    public void setBlockDisplayDetails(String blockDisplayDetails) {
        this.blockDisplayDetails = blockDisplayDetails;
    }

    public ColorEnum getBlockColor() {
        return blockColor;
    }

    public void setBlockColor(ColorEnum blockColor) {
        this.blockColor = blockColor;
    }

    public int getBlockPurchaseCost() {
        return blockPurchaseCost;
    }

    public void setBlockPurchaseCost(int blockPurchaseCost) {
        this.blockPurchaseCost = blockPurchaseCost;
    }

    public int getBlockHousePurchaseCost() {
        return blockHousePurchaseCost;
    }

    public void setBlockHousePurchaseCost(int blockHousePurchaseCost) {
        this.blockHousePurchaseCost = blockHousePurchaseCost;
    }

    public int getBlockApartmentPurchaseCost() {
        return blockApartmentPurchaseCost;
    }

    public void setBlockApartmentPurchaseCost(int blockApartmentPurchaseCost) {
        this.blockApartmentPurchaseCost = blockApartmentPurchaseCost;
    }

    public int getBlockLandRent() {
        return blockLandRent;
    }

    public void setBlockLandRent(int blockLandRent) {
        this.blockLandRent = blockLandRent;
    }

    public int getBlockHouseRent() {
        return blockHouseRent;
    }

    public void setBlockHouseRent(int blockHouseRent) {
        this.blockHouseRent = blockHouseRent;
    }

    public int getBlockApartmentRent() {
        return blockApartmentRent;
    }

    public void setBlockApartmentRent(int blockApartmentRent) {
        this.blockApartmentRent = blockApartmentRent;
    }

    public int getBlockMaxHouses() {
        return blockMaxHouses;
    }

    public void setBlockMaxHouses(int blockMaxHouses) {
        this.blockMaxHouses = blockMaxHouses;
    }

    public int getBlockMaxApartments() {
        return blockMaxApartments;
    }

    public void setBlockMaxApartments(int blockMaxApartments) {
        this.blockMaxApartments = blockMaxApartments;
    }

    public int getBlockMortgageValue() {
        return blockMortgageValue;
    }

    public void setBlockMortgageValue(int blockMortgageValue) {
        this.blockMortgageValue = blockMortgageValue;
    }

    public int getBlockSingleUtilityDiceRent() {
        return blockSingleUtilityDiceRent;
    }

    public void setBlockSingleUtilityDiceRent(int blockSingleUtilityDiceRent) {
        this.blockSingleUtilityDiceRent = blockSingleUtilityDiceRent;
    }

    public int getBlockMultipleUtilityDiceRent() {
        return blockMultipleUtilityDiceRent;
    }

    public void setBlockMultipleUtilityDiceRent(int blockMultipleUtilityDiceRent) {
        this.blockMultipleUtilityDiceRent = blockMultipleUtilityDiceRent;
    }

    public int getBlockSingleRoadBlockRent() {
        return blockSingleRoadBlockRent;
    }

    public void setBlockSingleRoadBlockRent(int blockSingleRoadBlockRent) {
        this.blockSingleRoadBlockRent = blockSingleRoadBlockRent;
    }

    public int getBlockMultipleRoadBlockRent() {
        return blockMultipleRoadBlockRent;
    }

    public void setBlockMultipleRoadBlockRent(int blockMultipleRoadBlockRent) {
        this.blockMultipleRoadBlockRent = blockMultipleRoadBlockRent;
    }

    public int getBlockCashAmounts() {
        return blockCashAmounts;
    }

    public void setBlockCashAmounts(int blockCashAmounts) {
        this.blockCashAmounts = blockCashAmounts;
    }

    public static Block getDummyBlock() {
        Block dummy = new LandBlock(0);
        dummy.setBlockType(BlockTypeEnum.DUMMY_BLOCK);
        dummy.setBlockName("Dummy Block @ Position 0");
        dummy.setBlockColor(ColorEnum.GREEN);
        return dummy;
    }

    public boolean isAvailableForPurchase() {
        return this.isBlockPurchasable();
    }

    public int getFixedPaymentAmount() {
        return this.getBlockCashAmounts();
    }

    public boolean isOwned() {
        // This should be overriden in Property block sub-class and given correct answer
        // based on ownership
        return false;
    }
}
