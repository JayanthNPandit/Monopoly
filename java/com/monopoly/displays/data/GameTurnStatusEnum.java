package com.monopoly.displays.data;

public enum GameTurnStatusEnum
{
    DUMMY_STATUS, READY_TO_START, PENDING_MANDATORY_STEPS, PENDING_OPTIONAL_STEPS, READY_FOR_NEXT_PLAYER;

    public boolean isReadyToStartTurn()
    {
        return this.equals(READY_TO_START);
    }

    public boolean isDiceRollAllowed()
    {
        return isReadyToStartTurn();
    }

    public boolean isCurrentPlayerPlaying()
    {
        return this.equals(PENDING_MANDATORY_STEPS) || this.equals(PENDING_OPTIONAL_STEPS);
    }

    public boolean isMandatoryStepsAllowed()
    {
        return this.equals(PENDING_MANDATORY_STEPS);
    }

    public boolean isOptionalStepsAllowed()
    {
        return this.equals(PENDING_OPTIONAL_STEPS);
    }

    public boolean isReadyForNextPlayer()
    {
        return this.equals(READY_FOR_NEXT_PLAYER) || this.equals(PENDING_OPTIONAL_STEPS);
    }
}
