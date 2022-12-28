package com.monopoly.board;

import com.monopoly.gameplay.Dice;
import com.monopoly.player.Player;

public abstract class PropertyBlock extends Block {
    private Player owner;
    private boolean mortgaged = false;

    public PropertyBlock(int aBlockNumber) {
        super(aBlockNumber);
    }

    public Player getOwner() {
        return owner;
    }

    public void purchasedBy(Player buyer) {
        this.owner = buyer;
    }

    public void sold() {
        this.owner = null;
    }

    public boolean isOwned() {
        if (this.getOwner() != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOwnedByPlayer(int playerNumber) {
        if (this.isOwned()) {
            return this.getOwner().getPlayerNumber() == playerNumber;
        } else {
            return false;
        }
    }

    public boolean isMortgaged() {
        return mortgaged;
    }

    public void setMortgaged(boolean mortgaged) {
        this.mortgaged = mortgaged;
    }

    @Override
    public boolean isAvailableForPurchase() {
        return ((this.isBlockPurchasable() == true) && (this.isOwned() == false));
    }

    public String toString() {
        return super.toString() + ", isOwned = " + this.isOwned() + ", owner = " + this.getOwner();
    }

    public abstract int calculateRent(Dice dice);
}
