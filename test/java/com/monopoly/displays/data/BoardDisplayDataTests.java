package com.monopoly.displays.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.monopoly.displays.helper.GeneralHelper;
import com.monopoly.game.MonopolyGameController;
import com.monopoly.gameplay.GameSettings;
import com.monopoly.gameplay.MonopolyGameEngine;
import com.monopoly.displays.adapter.GameEngineGraphicalDisplayAdapter;
import org.junit.jupiter.api.Test;

public class BoardDisplayDataTests {
    @Test
    public void testNewGameStartedWithBoardDisplayData() {
        int NEW_GAME_TURN_COUNT = 1;
        int NEW_TURN_INSTRUCTIONS_COUNT = 0;

        BoardDisplayData boardDisplayData = new BoardDisplayData();

        assertTrue(boardDisplayData.isReadyToStartTurn());
        assertFalse(boardDisplayData.isReadyForNextPlayer());
        assertFalse(boardDisplayData.isCurrentPlayerPlaying());
        // assertFalse(boardDisplayData.isCurrentPlayerPassedGo());

        assertEquals(BoardDisplayData.EMPTY_DICE_ROLL, boardDisplayData.getDiceRoll_1());
        assertEquals(BoardDisplayData.EMPTY_DICE_ROLL, boardDisplayData.getDiceRoll_2());
        assertEquals(NEW_TURN_INSTRUCTIONS_COUNT, boardDisplayData.getGameMessages().size());
        assertEquals(NEW_GAME_TURN_COUNT, boardDisplayData.getGamePlayTurnNumber());

        assertEquals(BoardDisplayData.BLOCK_NUMBER_FOR_GO_BLOCK,
                boardDisplayData.getCurrentPlayerBlock().getBlockNumber());
        assertEquals(GameSettings.FIRST_PLAYER_NUMBER, boardDisplayData.getCurrentPlayerNumber());
        for (int playerNumber = 1; playerNumber <= boardDisplayData.getNumberOfPlayers(); playerNumber++) {
            assertEquals(BoardDisplayData.BLOCK_NUMBER_FOR_GO_BLOCK,
                    boardDisplayData.getPlayerBlock(playerNumber).getBlockNumber());
        }
        assertFalse(boardDisplayData.isCurrentPlayerAllowedToBuildHousesHotelsOnCurentBlock());
        assertFalse(boardDisplayData.isCurrentPlayerRequiredToPayRentForCurrentBlock());
    }

    @Test
    public void testDiceRolled() {
        BoardDisplayData boardDisplayData = new BoardDisplayData();
        int DICE_ROLL_1 = GeneralHelper.randomInt(1, 6);
        int DICE_ROLL_2 = GeneralHelper.randomInt(1, 6);

        MonopolyGameEngine.getInstance().rollDice(DICE_ROLL_1, DICE_ROLL_2);
        assertEquals(DICE_ROLL_1, boardDisplayData.getDiceRoll_1());
        assertEquals(DICE_ROLL_2, boardDisplayData.getDiceRoll_2());
        assertEquals(DICE_ROLL_1 + DICE_ROLL_2, boardDisplayData.getDiceRoll_1() + boardDisplayData.getDiceRoll_2());
        assertEquals(DICE_ROLL_1 + DICE_ROLL_2, boardDisplayData.getDiceTotal());
    }

    @Test
    public void testBuildHousesHotelsWithBoardDisplayData() {
        BoardDisplayData boardDisplayData = new BoardDisplayData();

        boardDisplayData.buildHousesHotels(true);

        assertFalse(boardDisplayData.isReadyForNextPlayer());
    }

    @Test
    public void testNoMandatoryActionsFromPlayer() {
        BoardDisplayData boardDisplayData = new BoardDisplayData();

        boardDisplayData.noMandatoryActionsFromPlayer();

        assertTrue(boardDisplayData.isReadyForNextPlayer());
    }

    @Test
    public void testPayRentWithBoardDisplayData() {
        BoardDisplayData boardDisplayData = new BoardDisplayData();

        boardDisplayData.payRent();

        assertFalse(boardDisplayData.isReadyForNextPlayer());
        assertTrue(boardDisplayData.isCurrentPlayerRequiredActionsDone());
    }

    @Test
    public void testPerformGovernmentActionWithBoardDisplayData() {
        BoardDisplayData boardDisplayData = new BoardDisplayData();

        boardDisplayData.displayGovernmentActionMessage("Test message", true);

        assertTrue(boardDisplayData.isCurrentPlayerRequiredActionsDone());
    }

    @Test
    public void testPickChanceCommunityCardWithBoardDisplayData() {
        BoardDisplayData boardDisplayData = new BoardDisplayData();

        boardDisplayData.pickChanceCommunityCard();

        assertTrue(boardDisplayData.isCurrentPlayerRequiredActionsDone());
    }

    @Test
    public void testPurchasePropertyWithBoardDisplayData() {
        BoardDisplayData boardDisplayData = new BoardDisplayData();

        int playerNumber = GeneralHelper.randomInt(GameSettings.FIRST_PLAYER_NUMBER,
                boardDisplayData.getNumberOfPlayers());

        // boardDisplayData.setCurrentPlayerNumber(playerNumber);
        boardDisplayData.purchaseProperty();

        assertFalse(boardDisplayData.isReadyForNextPlayer());
        assertFalse(boardDisplayData.isCurrentPlayerAllowedToPurchaseCurentBlock());
    }

    @Test
    public void testMovePlayerBasedOnDiceThrow() {
        int DICE_ROLL_1 = GeneralHelper.randomInt(BoardDisplayData.MIN_DICE_VALUE, BoardDisplayData.MAX_DICE_VALUE);
        int DICE_ROLL_2 = GeneralHelper.randomInt(BoardDisplayData.MIN_DICE_VALUE, BoardDisplayData.MAX_DICE_VALUE);

        BoardDisplayData boardDisplayData = new BoardDisplayData();

        MonopolyGameEngine.getInstance().rollDice(DICE_ROLL_1, DICE_ROLL_2);
        boardDisplayData.moveCurrentPlayerBasedOnDiceThrow();

        // Do "minus 1" because the 0 position is not used
        assertEquals(DICE_ROLL_1 + DICE_ROLL_2, boardDisplayData.getCurrentPlayerBlock().getBlockNumber() - 1);
    }

    @Test
    public void testMovePlayerBasedOnDiceThrowGoingPastGo() {
        // Make sure the Dice Roll forces the token to go past "GO Block"
        int DICE_ROLL_1 = GameSettings.MAX_BLOCKS_PER_BOARD
                + GeneralHelper.randomInt(BoardDisplayData.MIN_DICE_VALUE, BoardDisplayData.MAX_DICE_VALUE);
        int DICE_ROLL_2 = GeneralHelper.randomInt(BoardDisplayData.MIN_DICE_VALUE, BoardDisplayData.MAX_DICE_VALUE);

        BoardDisplayData boardDisplayData = new BoardDisplayData();

        MonopolyGameEngine.getInstance().rollDice(DICE_ROLL_1, DICE_ROLL_2);
        boardDisplayData.moveCurrentPlayerBasedOnDiceThrow();

        // Do "minus 1" because the 0 position is not used
        // Subtract the NUM_BLOCKS_PER_BOARD to simulate going around the "GO Block" and
        // starting from the beginning of the board (at position 1)
        assertEquals(DICE_ROLL_1 + DICE_ROLL_2 - GameSettings.MAX_BLOCKS_PER_BOARD,
                boardDisplayData.getCurrentPlayerBlock().getBlockNumber() - 1);
    }

    @Test
    public void testResetForNextPlayer() {
        int NEW_GAME_TURN_COUNT = 1;
        int NEW_TURN_INSTRUCTIONS_COUNT = 0;
        int CURRENT_PLAYER = GameSettings.FIRST_PLAYER_NUMBER;

        BoardDisplayData boardDisplayData = new BoardDisplayData();

        assertTrue(boardDisplayData.isReadyToStartTurn());
        assertFalse(boardDisplayData.isReadyForNextPlayer());
        assertFalse(boardDisplayData.isCurrentPlayerPlaying());
        // assertFalse(boardDisplayData.isCurrentPlayerPassedGo());
        assertEquals(NEW_TURN_INSTRUCTIONS_COUNT, boardDisplayData.getGameMessages().size());
        assertEquals(NEW_GAME_TURN_COUNT, boardDisplayData.getGamePlayTurnNumber());
        assertEquals(CURRENT_PLAYER, boardDisplayData.getCurrentPlayerNumber());

        boardDisplayData.resetForNextPlayer();

        assertTrue(boardDisplayData.isReadyToStartTurn());
        assertFalse(boardDisplayData.isReadyForNextPlayer());
        // assertFalse(boardDisplayData.isCurrentPlayerPassedGo());
        assertEquals(NEW_TURN_INSTRUCTIONS_COUNT, boardDisplayData.getGameMessages().size());

        NEW_GAME_TURN_COUNT++;
        assertEquals(NEW_GAME_TURN_COUNT, boardDisplayData.getGamePlayTurnNumber());
        assertEquals(CURRENT_PLAYER + 1, boardDisplayData.getCurrentPlayerNumber());
    }

    @Test
    public void testResetForNextPlayerAfterLastPlayer() {
        int NEW_GAME_TURN_COUNT = 1;
        int NEW_TURN_INSTRUCTIONS_COUNT = 0;

        BoardDisplayData boardDisplayData = new BoardDisplayData();

        int FIRST_PLAYER = GameSettings.FIRST_PLAYER_NUMBER;
        int LAST_PLAYER = boardDisplayData.getNumberOfPlayers();
        int LAST_GAME_TURN_COUNT = boardDisplayData.getNumberOfPlayers();

        assertTrue(boardDisplayData.isReadyToStartTurn());
        assertFalse(boardDisplayData.isReadyForNextPlayer());
        assertFalse(boardDisplayData.isCurrentPlayerPlaying());
        // assertFalse(boardDisplayData.isCurrentPlayerPassedGo());
        assertEquals(NEW_TURN_INSTRUCTIONS_COUNT, boardDisplayData.getGameMessages().size());
        assertEquals(NEW_GAME_TURN_COUNT, boardDisplayData.getGamePlayTurnNumber());
        assertEquals(GameSettings.FIRST_PLAYER_NUMBER, boardDisplayData.getCurrentPlayerNumber());

        // Loop through all the players BUT NOT including the last player
        for (int playerIndex = FIRST_PLAYER; playerIndex < boardDisplayData.getNumberOfPlayers(); playerIndex++) {
            boardDisplayData.resetForNextPlayer();

            assertTrue(boardDisplayData.isReadyToStartTurn());
            assertFalse(boardDisplayData.isReadyForNextPlayer());
            // assertFalse(boardDisplayData.isCurrentPlayerPassedGo());
            assertEquals(NEW_TURN_INSTRUCTIONS_COUNT, boardDisplayData.getGameMessages().size());

            NEW_GAME_TURN_COUNT++;
            assertEquals(NEW_GAME_TURN_COUNT, boardDisplayData.getGamePlayTurnNumber());

            // After resetting for Next Player, the "current player" is already ahead of the
            // for-loop counter
            assertEquals(playerIndex + 1, boardDisplayData.getCurrentPlayerNumber());
        }

        assertEquals(LAST_GAME_TURN_COUNT, boardDisplayData.getGamePlayTurnNumber());
        assertEquals(LAST_PLAYER, boardDisplayData.getCurrentPlayerNumber());

        // Go beyond the last player and see if the next player is the "first player"
        boardDisplayData.resetForNextPlayer();

        assertTrue(boardDisplayData.isReadyToStartTurn());
        assertFalse(boardDisplayData.isReadyForNextPlayer());
        // assertFalse(boardDisplayData.isCurrentPlayerPassedGo());
        assertEquals(NEW_TURN_INSTRUCTIONS_COUNT, boardDisplayData.getGameMessages().size());

        LAST_GAME_TURN_COUNT++;
        assertEquals(LAST_GAME_TURN_COUNT, boardDisplayData.getGamePlayTurnNumber());
        assertEquals(FIRST_PLAYER, boardDisplayData.getCurrentPlayerNumber());
    }
}
