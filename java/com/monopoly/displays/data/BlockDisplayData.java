package com.monopoly.displays.data;

import com.monopoly.board.Block;
import com.monopoly.board.BlockTypeEnum;
import com.monopoly.board.ColorEnum;
import com.monopoly.board.LandBlock;
import com.monopoly.board.PropertyBlock;
import com.monopoly.displays.helper.DisplayHelper;
import com.monopoly.gameplay.Bank;
import com.monopoly.gameplay.MonopolyGameEngine;

public class BlockDisplayData {
    private static final Object NA_VALUE = "N/A";
    private int displayCoordinates_X;
    private int displayCoordinates_Y;
    private int displayWidth;
    private int displayHeight;

    private Block block;
    // private boolean[] playerInThisBlock;

    public BlockDisplayData(Block block, int numberOfPlayers) {
        this.setBlock(block);
        // this.playerInThisBlock = new boolean[numberOfPlayers + 1];
    }

    public Block getBlock() {
        return block;
    }

    private void setBlock(Block block) {
        this.block = block;
    }

    public int getDisplayCoordinates_X() {
        return displayCoordinates_X;
    }

    public void setDisplayCoordinates_X(int displayCoordinates_X) {
        this.displayCoordinates_X = displayCoordinates_X;
    }

    public int getDisplayCoordinates_Y() {
        return displayCoordinates_Y;
    }

    public void setDisplayCoordinates_Y(int displayCoordinates_Y) {
        this.displayCoordinates_Y = displayCoordinates_Y;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
    }

    public int getNumberOfPlayersInThisBlock() {
        return this.block.getNumberOfPlayersInThisBlock();
    }

    public boolean isPlayerInThisBlock(int playerNumber) {
        return this.block.isPlayerInThisBlock(playerNumber);
    }

    public void playerLandedOnBlock(int playerNumber) {
        /*
         * if (this.isPlayerInThisBlock(playerNumber) == false) { throw new
         * IllegalStateException(
         * "The player tried to land on his friend's block but failed miserably: " +
         * playerNumber); }
         */

        // this.playerInThisBlock[playerNumber] = true;

    }

    public void playerMovedFromBlock(int playerNumber) {
        /*
         * if (this.isPlayerInThisBlock(playerNumber) == true) { throw new
         * IllegalStateException(
         * "The player tried to jump off his friend's block but failed miserably: " +
         * playerNumber); }
         */

        // this.playerInThisBlock[playerNumber] = false;
    }

    public String getBlockDetailDisplay() {
        String displayDetail = null;
        Block currentBlock = this.getBlock();

        if (currentBlock.isBlockPurchasable() && currentBlock.isAvailableForPurchase()) {
            displayDetail = "Purchase for $" + DisplayHelper.getFormattedAmount(this.getPurchaseCost());
        } else if (currentBlock.isBlockPurchasable() && (currentBlock.isAvailableForPurchase() == false)) {
            // TODO: Make sure rent is displayed after ROAD block is bought; Doesn't work as
            // of
            // 10/18
            displayDetail = "Pay Rent of $" + DisplayHelper.getFormattedAmount(this.getRentAmount());
        } else if (currentBlock.isFixedPaymentBlock()) {
            displayDetail = "Pay $" + DisplayHelper.getFormattedAmount(currentBlock.getFixedPaymentAmount()) + " fine";
        } else if (currentBlock.isLotteryBlock()) {
            displayDetail = "Cash Pot $" + DisplayHelper.getFormattedAmount(Bank.getInstance().getLotteryBalance());
        } else if (NA_VALUE.equals(this.getBlockDetails()) == false) {
            displayDetail = this.getBlockDetails();
        } else {
            displayDetail = "";
        }

        return displayDetail;

    }

    public String toString() {
        return String.format("%02d, %s (%s) @ (%s).", this.getBlockNumber(), this.getBlockTitle(), this.getBlockType(),
                this.getBlockDetails());
    }

    public int getBlockNumber() {
        if (this.getBlock() != null) {
            return this.getBlock().getBlockNumber();
        } else {
            return 0;
        }
    }

    public ColorEnum getBlockColor() {
        if (this.getBlock() != null) {
            if (this.getBlock().isLandBlock()) {
                return ((LandBlock) this.getBlock()).getBlockColor();
            } else {
                return ColorEnum.DUMMY_COLOR;
            }
        } else {
            return ColorEnum.DUMMY_COLOR;
        }
    }

    public BlockTypeEnum getBlockType() {
        if (this.getBlock() != null) {
            return this.getBlock().getBlockType();
        } else {
            return BlockTypeEnum.DUMMY_BLOCK;
        }
    }

    public String getBlockTitle() {
        if (this.getBlock() != null) {
            return this.getBlock().getBlockName();
        } else {
            return "";
        }
    }

    public int getNumberOfHouses() {
        if ((this.getBlock() != null) && (this.getBlock().isLandBlock())) {
            return ((LandBlock) this.getBlock()).getNumberOfHouses();
        } else {
            return 0;
        }
    }

    public int getNumberOfApartments() {
        if ((this.getBlock() != null) && (this.getBlock().isLandBlock())) {
            return ((LandBlock) this.getBlock()).getNumberOfApartments();
        } else {
            return 0;
        }
    }

    public String getBlockDetails() {
        if (this.getBlock() != null) {
            return this.getBlock().getBlockDisplayDetails();
        } else {
            return null;
        }
    }

    public int getPurchaseCost() {
        if (this.getBlock() != null) {
            return this.getBlock().getBlockPurchaseCost();
        } else {
            return 0;
        }
    }

    public int getRentAmount() {
        if (this.getBlock() != null) {
            if (this.getBlock().getBlockType().isBlockPurchasable()) {
                return ((PropertyBlock) this.getBlock()).calculateRent(MonopolyGameEngine.getInstance().getDice());
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public boolean isBlockCurrentlyOwned() {
        if (this.getBlock() != null) {
            if (this.getBlock().getBlockType().isBlockPurchasable()) {
                return ((PropertyBlock) this.getBlock()).isOwned();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean isOwnedByPlayer(int currentPlayer) {
        if (this.getBlock() != null) {
            if (this.getBlock().getBlockType().isBlockPurchasable()) {
                return ((PropertyBlock) this.getBlock()).getOwner().getPlayerNumber() == currentPlayer;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean isBlockBuildable() {
        if (this.getBlock() != null) {
            return this.getBlock().isBlockBuildable();
        } else {
            return false;
        }
    }

    public String getBlockName() {
        if (this.getBlock() != null) {
            return this.getBlock().getBlockName();
        } else {
            return "Unknown Block Name";
        }
    }

    public String getCurrentOwnerName() {
        if (this.getBlock() != null) {
            if (this.getBlock().getBlockType().isBlockPurchasable()) {
                return ((PropertyBlock) this.getBlock()).getOwner().getName();
            } else {
                return "Unknown Block Owner";
            }
        } else {
            return "Unknown Block Owner";
        }
    }

    public boolean isChanceOrCommunityBlock() {
        if (this.getBlock() != null) {
            return this.getBlock().getBlockType().isChanceBlock() || this.getBlock().getBlockType().isCommunityBlock();
        } else {
            return false;
        }
    }
}
