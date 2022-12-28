package com.monopoly.player;

import com.monopoly.board.ColorEnum;

public class FakePlayer extends Player {

    protected FakePlayer(String aName, int aPlayerNumber) {
        super(aName, aPlayerNumber);
    }

    public FakePlayer(String aName, int aPlayerNumber, ColorEnum aPlayerColor) {
        super(aName, aPlayerNumber, aPlayerColor);
    }

    public FakePlayer() {
        this("Fake Player", 0);
    }

}