package com.monopoly.player;

import com.monopoly.board.ColorEnum;

public class ComputerPlayer extends Player {
    public ComputerPlayer(String aName, int aPlayerNumber) {
        super(aName, aPlayerNumber);
    }

    public ComputerPlayer(String aName, int aPlayerNumber, ColorEnum aPlayerColor) {
        super(aName, aPlayerNumber, aPlayerColor);
    }
}
