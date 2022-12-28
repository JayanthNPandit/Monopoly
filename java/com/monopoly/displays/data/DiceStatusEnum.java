package com.monopoly.displays.data;

public enum DiceStatusEnum
{
    NOT_ROLLING, DICE_ROLLING_NOW, DICE_FINISHED_ROLLING;

    public boolean isDiceRolling()
    {
        return this.equals(DICE_ROLLING_NOW);
    }

    public boolean isDiceFinishedRolling()
    {
        return this.equals(DICE_FINISHED_ROLLING);
    }
}
