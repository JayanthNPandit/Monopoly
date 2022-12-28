package com.monopoly.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.monopoly.board.Block;
import com.monopoly.board.Building;
import com.monopoly.board.ColorEnum;
import com.monopoly.board.LandBlock;
import com.monopoly.board.PropertyBlock;
import com.monopoly.displays.helper.AudioHelper;
import com.monopoly.gameplay.Bank;
import com.monopoly.gameplay.GameSettings;
import com.monopoly.gameplay.MonopolyGameEngine;

public class Player {
    private String name;
    private int playerNumber;
    private ColorEnum playerColor;
    private Block currentBlock;
    private int currentCash;
    private ArrayList<Integer> netWorthByRound;
    private boolean inJail;
    private boolean playerPassedGo;
    private int numberOfTurns = 0;
    private int numberOfRoundTheBoardTrips = 0;
    private Map<String, PropertyBlock> ownedBlocks = new HashMap<String, PropertyBlock>();

    public Player(String aName, int aPlayerNumber) {
        this(aName, aPlayerNumber, getRandomColor());
    }

    public Player(String aName, int aPlayerNumber, ColorEnum aPlayerColor) {
        name = aName;
        playerNumber = aPlayerNumber;
        playerColor = aPlayerColor;
        currentBlock = null;
        currentCash = GameSettings.getStartingPlayerCash();
        netWorthByRound = new ArrayList<Integer>();
    }

    private static ColorEnum getRandomColor() {
        int colorIndex = (int) (Math.random() * (ColorEnum.getGoodVisualColors().size() - 1)) + 1;
        return ColorEnum.getGoodVisualColors().get(colorIndex);
    }

    public int getTotalNetWorth() {
        return this.getCurrentCash() + this.getTotalPropertyValue();
    }

    public int getTotalPropertyValue() {
        // TODO: Adjust for Buildings on Blocks
        int totalValue = 0;
        for (PropertyBlock block : ownedBlocks.values()) {
            totalValue += block.getBlockPurchaseCost();
        }

        return totalValue;
    }

    public int getCurrentCash() {
        return currentCash;
    }

    public void addCash(int cashToAdd) {
        this.currentCash += cashToAdd;
    }

    private void removeCash(int cashToRemove) {
        this.currentCash -= cashToRemove;
    }

    public void recieveCashFrom(Player cashFrom, int cashToRecieve) {
        this.addCash(cashToRecieve);
        cashFrom.removeCash(cashToRecieve);
    }

    public void recieveCashFrom(Bank cashFrom, int cashToRecieve) {
        this.addCash(cashToRecieve);
        cashFrom.withdraw(cashToRecieve);
    }

    // Specified for cash earning blocks in the future, not used now
    /*
     * public void earnCash(int earnedCash) { this.addCash(earnedCash); }
     */

    public String playLottery() {
        int lotteryWinnings = 0;
        float percentageToWin = 0.0f;

        if (GameSettings.isLotteryGamePlayed() == true) {
            // My way, minimum 10%, maximum 100%
            percentageToWin = ((float) (randomInt(10, 100)) / 100);
        } else {
            // Winner takes all(100%) the lottery money
            percentageToWin = 1.0f;
        }

        lotteryWinnings = Bank.getInstance().withdrawPercentageOfLottery(percentageToWin);
        this.addCash(lotteryWinnings);

        new AudioHelper().playSound(GameSettings.SOUND_FILE_LOCATION_PREFIX + GameSettings.SOUND_FOR_WINNING_LOTTERY);
        return "You won $" + lotteryWinnings + "!";
    }

    private int randomInt(int min, int max) {
        int randValue = (int) (Math.random() * max);

        if (randValue < min) {
            randValue = min;
        } else if (randValue > max) {
            randValue = max;
        }

        return randValue;
    }

    public void giveCashTo(Player cashTo, int cashToGive) {
        cashTo.addCash(cashToGive);
        this.removeCash(cashToGive);
    }

    public void makeBankPayments(Bank cashTo, int cashToGive) {
        cashTo.depositPayments(cashToGive);
        this.removeCash(cashToGive);
    }

    public void payCashBlockFines(Bank cashTo, int cashToGive) {
        cashTo.depositCashBlockFines(cashToGive);
        this.removeCash(cashToGive);
    }

    public void payChanceCardFines(Bank cashTo, int cashToGive) {
        cashTo.depositCashBlockFines(cashToGive);
        this.removeCash(cashToGive);
    }

    public void payDirectlyToLotteryPot(int cashToGive) {
        Bank.getInstance().addToLotteryPot(cashToGive);
        this.removeCash(cashToGive);
    }

    public boolean payRent(Player reciever, int rentAmount) {
        if (this.currentCash >= rentAmount) {
            this.giveCashTo(reciever, rentAmount);
            return true;
        } else {
            System.out.println("\tPlayer " + this.getName() + " doesn't have enough cash.");
            System.out.println("\t\trentAmount: " + rentAmount + ", currentCash: " + this.currentCash);
            return false;
        }
    }

    public String getName() {
        return name;
    }

    private Map<String, PropertyBlock> getOwnedPropertyBlocks() {
        return this.ownedBlocks;
    }

    public List<PropertyBlock> getOwnedProperties() {
        return new ArrayList<PropertyBlock>(this.getOwnedPropertyBlocks().values());
    }

    public Block getCurrentBlock() {
        return currentBlock;
    }

    public void setCurrentBlock(Block aBlock) {
        currentBlock = aBlock;
    }

    public boolean equals(Object obj) {
        if (this.getName().equals(((Player) obj).getName())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ableToBuyProperty(PropertyBlock currentPropertyBlock) {
        // Next year work, decide if player wants to sell some properties or houses to
        // raise funds for buying
        // Right now, the only check is if the player has enough cash
        return currentPropertyBlock.getBlockPurchaseCost() <= this.getCurrentCash();
    }

    public boolean decideToBuyProperty(PropertyBlock currentPropertyBlock) {
        // TODO: To be implemented
        return true;
    }

    public boolean purchaseProperty(PropertyBlock propertyBlockToBuy) {
        PropertyBlock isAlreadyOwnedBlock = checkForOwnedProperty(propertyBlockToBuy);

        if (isAlreadyOwnedBlock == null) {
            this.makeBankPayments(Bank.getInstance(), propertyBlockToBuy.getBlockPurchaseCost());
            this.getOwnedPropertyBlocks().put(propertyBlockToBuy.getBlockName(), propertyBlockToBuy);
            propertyBlockToBuy.purchasedBy(this);
            return true;
        } else {
            return false;
        }
    }

    private PropertyBlock checkForOwnedProperty(PropertyBlock propertyBlockToBuy) {
        if (this.getOwnedPropertyBlocks().isEmpty() == false) {
            return this.getOwnedPropertyBlocks().get(propertyBlockToBuy.getBlockName());
        } else {
            return null;
        }
    }

    public boolean payRent(PropertyBlock propertyBlockToRent) {
        Player propertyOwner = propertyBlockToRent.getOwner();

        if (propertyOwner != null) {
            this.payRent(propertyOwner, propertyBlockToRent.calculateRent(MonopolyGameEngine.getInstance().getDice()));
            return true;
        } else {
            return false;
        }
    }

    public boolean sellProperty(PropertyBlock propertyBlockToBeSold) {
        PropertyBlock isAlreadyOwnedPropertyBlock = checkForOwnedProperty(propertyBlockToBeSold);

        if (isAlreadyOwnedPropertyBlock != null) {
            if (isAlreadyOwnedPropertyBlock.isMortgaged()) {
                this.unmortgageProperty(isAlreadyOwnedPropertyBlock);
            }
            this.getOwnedPropertyBlocks().remove(propertyBlockToBeSold.getBlockName());
            this.recieveCashFrom(Bank.getInstance(), isAlreadyOwnedPropertyBlock.getBlockPurchaseCost());
            if (isAlreadyOwnedPropertyBlock.isLandBlock()) {
                this.recieveCashFrom(Bank.getInstance(),
                        ((LandBlock) isAlreadyOwnedPropertyBlock).calculateTotalHouseValue()
                                + ((LandBlock) isAlreadyOwnedPropertyBlock).calculateTotalApartmentValue());
            }
            propertyBlockToBeSold.sold();
            return true;
        } else {
            return false;
        }
    }

    public void unmortgageProperty(PropertyBlock propertyBlock) {
        PropertyBlock isAlreadyOwnedBlock = this.checkForOwnedProperty(propertyBlock);
        if (isAlreadyOwnedBlock != null) {
            this.recieveCashFrom(Bank.getInstance(), isAlreadyOwnedBlock.getBlockPurchaseCost());
            if (propertyBlock.isLandBlock()) {
                LandBlock ownedLandBlock = (LandBlock) isAlreadyOwnedBlock;
                // To unmortgage a property, Monopoly rule is to first sell all buildings at
                // half value
                for (Building building : ownedLandBlock.getBuildings()) {
                    if (building.isHouse()) {
                        ownedLandBlock.sellHouse();
                        this.recieveCashFrom(Bank.getInstance(),
                                (int) (ownedLandBlock.getBlockHousePurchaseCost() * 0.5));
                    } else if (building.isApartment()) {
                        ownedLandBlock.sellApartment();
                        this.recieveCashFrom(Bank.getInstance(),
                                (int) (ownedLandBlock.getBlockApartmentPurchaseCost() * 0.5));
                    }
                }
            } else {
                // No extra logic for railroads and utility buildings
            }
        } else {
            throw new RuntimeException("Trying to unmortgage an unowned property: " + propertyBlock.getBlockName());
        }

        isAlreadyOwnedBlock.setMortgaged(false);
        this.makeBankPayments(Bank.getInstance(), isAlreadyOwnedBlock.getBlockMortgageValue());
    }

    public void goBankrupt(Player recieverWhoIsOwedMoney) {
        System.out.println("Gone bankrupt, have you?");

        if (this.getOwnedPropertyBlocks().isEmpty() == false) {
            for (PropertyBlock propertyBlock : this.getOwnedPropertyBlocks().values()) {
                this.sellProperty(propertyBlock);
            }
            this.giveCashTo(recieverWhoIsOwedMoney, this.getCurrentCash());
        }
    }

    @Override
    public String toString() {
        if ((this instanceof FakePlayer) == false) {
            return "Player name = " + name + ", currentBlock = " + currentBlock.getBlockName() + "("
                    + currentBlock.getBlockNumber() + ")" + ", currentCash = " + currentCash;
        } else {
            return "";
        }
    }

    public boolean hasEnoughAssetsForRent(int rentAmount) {
        // Add up combined value of all assets.
        int totalAssetsValue = this.getCurrentCash() + this.calculateTotalPropertyValue();
        if (totalAssetsValue >= rentAmount) {
            return true;
        } else {
            return false;
        }
    }

    private int calculateTotalPropertyValue() {
        int unmortgagedPropertiesTotalValue = calculateUnmortgagedPropertiesValue();
        int mortgagesPropertiesTotalValue = calculateMortgagesPropertiesValue();
        int totalHouseValue = calculateHouseValue();
        int totalApartmentValue = calculateApartmentValue();
        return unmortgagedPropertiesTotalValue + mortgagesPropertiesTotalValue + totalHouseValue + totalApartmentValue;
    }

    private int calculateUnmortgagedPropertiesValue() {
        int total = 0;

        if (this.getOwnedPropertyBlocks().isEmpty() == false) {
            for (PropertyBlock propertyBlock : this.getOwnedPropertyBlocks().values()) {
                if (propertyBlock.isMortgaged() == false) {
                    total += propertyBlock.getBlockPurchaseCost();
                }
            }
        }

        return total;
    }

    private int calculateMortgagesPropertiesValue() {
        int total = 0;

        if (this.getOwnedPropertyBlocks().isEmpty() == false) {
            for (PropertyBlock propertyBlock : this.getOwnedPropertyBlocks().values()) {
                if (propertyBlock.isMortgaged() == true) {
                    total += (propertyBlock.getBlockPurchaseCost() - propertyBlock.getBlockMortgageValue());
                }
            }
        }

        return total;
    }

    private int calculateHouseValue() {
        int total = 0;

        if (this.getOwnedPropertyBlocks().isEmpty() == false) {
            for (PropertyBlock propertyBlock : this.getOwnedPropertyBlocks().values()) {
                if (propertyBlock.isLandBlock()) {
                    total += ((LandBlock) propertyBlock).calculateTotalHouseValue();
                }
            }
        }

        return total;
    }

    private int calculateApartmentValue() {
        int total = 0;

        if (this.getOwnedPropertyBlocks().isEmpty() == false) {
            for (PropertyBlock propertyBlock : this.getOwnedPropertyBlocks().values()) {
                if (propertyBlock.isLandBlock()) {
                    total += ((LandBlock) propertyBlock).calculateTotalApartmentValue();
                }
            }
        }

        return total;
    }

    public PropertyBlock decidePropertyToSell() {
        PropertyBlock mostValuedBlock = null;
        int highestValue = 0;

        if (this.getOwnedPropertyBlocks().isEmpty() == false) {
            for (PropertyBlock propertyBlock : this.getOwnedPropertyBlocks().values()) {
                int totalPropertyValue = propertyBlock.getBlockPurchaseCost();
                if (propertyBlock.isLandBlock()) {
                    totalPropertyValue += ((LandBlock) propertyBlock).calculateTotalHouseValue()
                            + ((LandBlock) propertyBlock).calculateTotalApartmentValue();
                }
                if (totalPropertyValue > highestValue) {
                    highestValue = propertyBlock.getBlockPurchaseCost();
                    mostValuedBlock = propertyBlock;
                }
            }
        }

        return mostValuedBlock;
    }

    public int getNumberOfRoadBlocksOwned() {
        int numRoadBlocks = 0;
        if (this.getOwnedPropertyBlocks().isEmpty() == false) {
            for (PropertyBlock propertyBlock : this.getOwnedPropertyBlocks().values()) {
                if (propertyBlock.isRoadBlock()) {
                    numRoadBlocks++;
                }
            }
        }

        return numRoadBlocks;
    }

    public int getNumberOfUtilityBlocksOwned() {
        int numUtilityBlocks = 0;

        if (this.getOwnedPropertyBlocks().isEmpty() == false) {
            for (PropertyBlock propertyBlock : this.getOwnedPropertyBlocks().values()) {
                if (propertyBlock.isUtilityBlock()) {
                    numUtilityBlocks++;
                }
            }
        }

        return numUtilityBlocks;
    }

    public boolean isInJail() {
        return inJail;
    }

    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public boolean hasPlayerPassedGo() {
        return this.playerPassedGo;
    }

    public void playerPassedGo() {
        this.playerPassedGo = true;
        this.numberOfRoundTheBoardTrips++;
    }

    public void playerDidNotPassGo() {
        this.playerPassedGo = false;
    }

    public void playerNextTurn() {
        updateNetWorth();
        this.numberOfTurns++;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public int getNumberOfRoundTheBoardTrips() {
        return numberOfRoundTheBoardTrips;
    }

    public ColorEnum getPlayerColor() {
        return playerColor;
    }

    public boolean isBankrupt() {
        // TODO: Change this back to getTotalNetWorth()
        // return this.getTotalNetWorth() <= 0;
        return this.getCurrentCash() <= 0;
    }

    public int getNetWorthByRound(int roundIndex) {
        if ((netWorthByRound != null) && (netWorthByRound.size() >= roundIndex)) {
            return netWorthByRound.get(roundIndex);
        } else {
            return 0;
        }
    }

    private void updateNetWorth() {
        this.netWorthByRound.add(this.getNumberOfTurns(), this.getTotalNetWorth());
    }
}
