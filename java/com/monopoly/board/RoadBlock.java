package com.monopoly.board;

import com.monopoly.gameplay.Dice;

public class RoadBlock extends PropertyBlock {

    public RoadBlock(int aBlockNumber) {
        super(aBlockNumber);
    }

    @Override
    public int calculateRent(Dice dice) {
        int numRoadBlocksOwned = 0;

        if (super.isOwned()) {
            numRoadBlocksOwned = super.getOwner().getNumberOfRoadBlocksOwned();
        } else {
            numRoadBlocksOwned = 0;
        }

        int rentMultiplier = 0;
        if (numRoadBlocksOwned == 1) {
            rentMultiplier = super.getBlockSingleRoadBlockRent();
        } else {
            rentMultiplier = super.getBlockMultipleRoadBlockRent();
        }

        int totalRent = numRoadBlocksOwned * rentMultiplier;

        return totalRent;
    }

}
