package com.monopoly.player;

import com.monopoly.board.ColorEnum;

public class HumanPlayer extends Player {
    public HumanPlayer(String aName, int aPlayerNumber) {
        super(aName, aPlayerNumber);
    }

    public HumanPlayer(String aName, int aPlayerNumber, ColorEnum aPlayerColor) {
        super(aName, aPlayerNumber, aPlayerColor);
    }
}
