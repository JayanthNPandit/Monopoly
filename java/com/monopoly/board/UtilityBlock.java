package com.monopoly.board;

import com.monopoly.gameplay.Dice;

public class UtilityBlock extends PropertyBlock {
    public UtilityBlock(int aBlockNumber) {
        super(aBlockNumber);
    }

    @Override
    public int calculateRent(Dice dice) {
        int numUtilityBlocksOwned = 0;

        if (super.getOwner() != null) {
            numUtilityBlocksOwned = super.getOwner().getNumberOfUtilityBlocksOwned();
        } else {
            numUtilityBlocksOwned = 0;
        }

        int rentMultiplier = 0;
        if (numUtilityBlocksOwned == 1) {
            rentMultiplier = super.getBlockSingleUtilityDiceRent() * dice.getTotalDiceValue();
        } else {
            rentMultiplier = super.getBlockMultipleUtilityDiceRent() * dice.getTotalDiceValue();
        }

        int totalRent = numUtilityBlocksOwned * rentMultiplier;

        return totalRent;
    }
}
