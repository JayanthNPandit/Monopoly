package com.monopoly.gameplay;

public class Dice {
    private int diceValueOne;
    private int diceValueTwo;

    public Dice() {
        diceValueOne = 0;
        diceValueTwo = 0;
    }

    public Dice(int valueOne, int valueTwo) {
        diceValueOne = valueOne;
        diceValueTwo = valueTwo;
    }

    public void rollDice() {
        diceValueOne = (int) (Math.random() * 6) + 1;
        diceValueTwo = (int) (Math.random() * 6) + 1;
    }

    public int getDiceOneValue() {
        return diceValueOne;
    }

    public int getDiceTwoValue() {
        return diceValueTwo;
    }

    public int getTotalDiceValue() {
        return getDiceOneValue() + getDiceTwoValue();
    }

    public boolean isDoubles() {
        return (diceValueOne == diceValueTwo);
    }
}
