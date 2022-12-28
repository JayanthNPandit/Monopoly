package com.monopoly.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import com.monopoly.displays.data.BoardDisplayData;
import com.monopoly.gameplay.GameSettings;
import com.monopoly.gameplay.MonopolyGameEngine;
import com.monopoly.displays.adapter.GameEngineGraphicalDisplayAdapter;

public class MonopolyGameControllerTests {
    @Test
    public void testNewGameStartedWithMonopolyGameController() {
        int NEW_GAME_TURN_COUNT = 1;
        int NEW_GAME_INSTRUCTIONS_COUNT = 1;

        BoardDisplayData boardDisplayData = new BoardDisplayData();
        MonopolyGameController monopolyGameController = MonopolyGameController.createInstance(boardDisplayData);

        assertTrue(boardDisplayData.isReadyToStartTurn());
        assertFalse(boardDisplayData.isReadyForNextPlayer());
        assertFalse(boardDisplayData.isCurrentPlayerPlaying());
        // assertFalse(boardDisplayData.isCurrentPlayerPassedGo());

        assertEquals(BoardDisplayData.EMPTY_DICE_ROLL, boardDisplayData.getDiceRoll_1());
        assertEquals(BoardDisplayData.EMPTY_DICE_ROLL, boardDisplayData.getDiceRoll_2());
        assertEquals(NEW_GAME_INSTRUCTIONS_COUNT, boardDisplayData.getGameMessages().size());
        assertEquals(NEW_GAME_TURN_COUNT, boardDisplayData.getGamePlayTurnNumber());

        assertEquals(BoardDisplayData.BLOCK_NUMBER_FOR_GO_BLOCK,
                boardDisplayData.getCurrentPlayerBlock().getBlockNumber());
        assertEquals(GameSettings.FIRST_PLAYER_NUMBER,
                MonopolyGameEngine.getInstance().getCurrentlyPlayingPlayerNumber());
        for (int playerNumber = 1; playerNumber <= boardDisplayData.getNumberOfPlayers(); playerNumber++) {
            assertEquals(BoardDisplayData.BLOCK_NUMBER_FOR_GO_BLOCK,
                    boardDisplayData.getPlayerBlock(playerNumber).getBlockNumber());
        }
        assertFalse(boardDisplayData.isCurrentPlayerAllowedToBuildHousesHotelsOnCurentBlock());
        assertFalse(boardDisplayData.isCurrentPlayerRequiredToPayRentForCurrentBlock());

        assertFalse(monopolyGameController.isCurrentPlayerAllowedToBuildHousesHotelsOnCurentBlock());
        assertFalse(monopolyGameController.isCurrentPlayerPlaying());
        assertFalse(monopolyGameController.isReadyForNextPlayer());
        assertTrue(monopolyGameController.isReadyToStartTurn());
    }

    @Test
    public void testBuildHousesHotelsWithMonopolyGameController() {
        BoardDisplayData boardDisplayData = new BoardDisplayData();
        MonopolyGameController monopolyGameController = MonopolyGameController.createInstance(boardDisplayData);

        monopolyGameController.buildHousesHotels();

        assertTrue(boardDisplayData.isReadyForNextPlayer());
        assertTrue(monopolyGameController.isReadyForNextPlayer());
    }

    @Test
    public void testNextPlayerTurn() {
        int NEW_GAME_TURN_COUNT = 1;
        int NEW_GAME_INSTRUCTIONS_COUNT = 1;

        BoardDisplayData boardDisplayData = new BoardDisplayData();
        MonopolyGameController monopolyGameController = MonopolyGameController.createInstance(boardDisplayData);

        int FIRST_PLAYER = GameSettings.FIRST_PLAYER_NUMBER;
        int NEXT_PLAYER = FIRST_PLAYER + 1;

        assertFalse(monopolyGameController.isCurrentPlayerPlaying());
        assertFalse(monopolyGameController.isReadyForNextPlayer());
        assertTrue(monopolyGameController.isReadyToStartTurn());

        assertEquals(FIRST_PLAYER, MonopolyGameEngine.getInstance().getCurrentlyPlayingPlayerNumber());

        assertEquals(NEW_GAME_INSTRUCTIONS_COUNT, boardDisplayData.getGameMessages().size());
        assertEquals(NEW_GAME_TURN_COUNT, boardDisplayData.getGamePlayTurnNumber());

        monopolyGameController.nextPlayerTurn();

        assertFalse(monopolyGameController.isCurrentPlayerPlaying());
        assertFalse(monopolyGameController.isReadyForNextPlayer());
        assertTrue(monopolyGameController.isReadyToStartTurn());
        assertEquals(NEW_GAME_INSTRUCTIONS_COUNT, boardDisplayData.getGameMessages().size());

        NEW_GAME_TURN_COUNT++;
        assertEquals(NEW_GAME_TURN_COUNT, boardDisplayData.getGamePlayTurnNumber());
        assertEquals(NEXT_PLAYER, MonopolyGameEngine.getInstance().getCurrentlyPlayingPlayerNumber());
    }

    @Test
    public void testNextPlayerTurnAfterLastPlayer() {
        int NEW_GAME_TURN_COUNT = 1;
        int NEW_GAME_INSTRUCTIONS_COUNT = 1;

        BoardDisplayData boardDisplayData = new BoardDisplayData();
        MonopolyGameController monopolyGameController = MonopolyGameController.createInstance(boardDisplayData);

        int FIRST_PLAYER = GameSettings.FIRST_PLAYER_NUMBER;
        int LAST_PLAYER = boardDisplayData.getNumberOfPlayers();
        int LAST_GAME_TURN_COUNT = boardDisplayData.getNumberOfPlayers();

        assertFalse(monopolyGameController.isCurrentPlayerPlaying());
        assertFalse(monopolyGameController.isReadyForNextPlayer());
        assertTrue(monopolyGameController.isReadyToStartTurn());

        assertEquals(FIRST_PLAYER, MonopolyGameEngine.getInstance().getCurrentlyPlayingPlayerNumber());

        // Loop through all the players BUT NOT including the last player
        for (int playerIndex = FIRST_PLAYER; playerIndex < boardDisplayData.getNumberOfPlayers(); playerIndex++) {
            monopolyGameController.nextPlayerTurn();

            assertFalse(monopolyGameController.isCurrentPlayerPlaying());
            assertFalse(monopolyGameController.isReadyForNextPlayer());
            assertTrue(monopolyGameController.isReadyToStartTurn());

            // After resetting for Next Player, the "current player" is already ahead of the
            // for-loop counter
            assertEquals(playerIndex + 1, MonopolyGameEngine.getInstance().getCurrentlyPlayingPlayerNumber());

            assertEquals(NEW_GAME_INSTRUCTIONS_COUNT, boardDisplayData.getGameMessages().size());

            NEW_GAME_TURN_COUNT++;
            assertEquals(NEW_GAME_TURN_COUNT, boardDisplayData.getGamePlayTurnNumber());

            // After resetting for Next Player, the "current player" is already ahead of the
            // for-loop counter
            assertEquals(playerIndex + 1, MonopolyGameEngine.getInstance().getCurrentlyPlayingPlayerNumber());
        }

        assertEquals(LAST_GAME_TURN_COUNT, boardDisplayData.getGamePlayTurnNumber());
        assertEquals(LAST_PLAYER, MonopolyGameEngine.getInstance().getCurrentlyPlayingPlayerNumber());

        // Go beyond the last player and see if the next player is the "first player"
        monopolyGameController.nextPlayerTurn();

        assertTrue(boardDisplayData.isReadyToStartTurn());
        assertFalse(boardDisplayData.isReadyForNextPlayer());
        // assertFalse(boardDisplayData.isCurrentPlayerPassedGo());
        assertEquals(NEW_GAME_INSTRUCTIONS_COUNT, boardDisplayData.getGameMessages().size());

        LAST_GAME_TURN_COUNT++;
        assertEquals(LAST_GAME_TURN_COUNT, boardDisplayData.getGamePlayTurnNumber());
        assertEquals(FIRST_PLAYER, MonopolyGameEngine.getInstance().getCurrentlyPlayingPlayerNumber());
    }

    @Test
    public void testPayRentWithMonopolyGameController() {
        BoardDisplayData boardDisplayData = new BoardDisplayData();
        MonopolyGameController monopolyGameController = MonopolyGameController.createInstance(boardDisplayData);

        monopolyGameController.payRent();

        assertTrue(monopolyGameController.isReadyForNextPlayer());
    }

    @Test
    public void testPerformGovernmentActionWithMonopolyGameController() {
        BoardDisplayData boardDisplayData = new BoardDisplayData();
        MonopolyGameController monopolyGameController = MonopolyGameController.createInstance(boardDisplayData);

        monopolyGameController.performGovernmentAction();

        assertTrue(monopolyGameController.isReadyForNextPlayer());
    }

    @Test
    public void testPickChanceCommunityCardWithMonopolyGameController() {
        BoardDisplayData boardDisplayData = new BoardDisplayData();
        MonopolyGameController monopolyGameController = MonopolyGameController.createInstance(boardDisplayData);

        monopolyGameController.pickChanceCommunityCard();

        assertTrue(monopolyGameController.isReadyForNextPlayer());
    }

    @Test
    public void testPurchasePropertyWithMonopolyGameController() {
        BoardDisplayData boardDisplayData = new BoardDisplayData();
        MonopolyGameController monopolyGameController = MonopolyGameController.createInstance(boardDisplayData);

        monopolyGameController.purchaseProperty();

        assertTrue(monopolyGameController.isReadyForNextPlayer());
    }

    @Test
    public void testRollDice() {
        BoardDisplayData boardDisplayData = new BoardDisplayData();
        MonopolyGameController monopolyGameController = MonopolyGameController.createInstance(boardDisplayData);

        monopolyGameController.rollDice();

        assertFalse(monopolyGameController.isReadyForNextPlayer());
        assertNotEquals(BoardDisplayData.EMPTY_DICE_ROLL, boardDisplayData.getDiceRoll_1());
        assertNotEquals(BoardDisplayData.EMPTY_DICE_ROLL, boardDisplayData.getDiceRoll_2());
    }

    @Test
    public void testStartPlayerTurn() {
        BoardDisplayData boardDisplayData = new BoardDisplayData();
        MonopolyGameController monopolyGameController = MonopolyGameController.createInstance(boardDisplayData);

        monopolyGameController.rollDice();
        monopolyGameController.startPlayerTurn();

        assertTrue(monopolyGameController.isCurrentPlayerPlaying());
        switch (boardDisplayData.getCurrentPlayerBlock().getBlockType()) {
            case DUMMY_BLOCK: {
                fail("Unexpected landing in the Dummy Block Block Type: "
                        + boardDisplayData.getCurrentPlayerBlock().getBlockTitle());
                break;
            }
            case CHANCE_BLOCK: {
                assertTrue(monopolyGameController.isCurrentPlayerAllowedToPickChanceCommunityCardOnCurentBlock());
                assertFalse(monopolyGameController.isReadyForNextPlayer());
                break;
            }
            case COMMUNITY_BLOCK: {
                assertTrue(monopolyGameController.isCurrentPlayerAllowedToPickChanceCommunityCardOnCurentBlock());
                assertFalse(monopolyGameController.isReadyForNextPlayer());
                break;
            }
            case LOTTERY_BLOCK:
            case JAIL_BLOCK:
            case GO_TO_JAIL_BLOCK:
            case CASH_PAYMENT_BLOCK:
            case GO_BLOCK: {
                assertTrue(monopolyGameController.isCurrentPlayerAllowedToPerformGovernmentActionOnCurentBlock());
                assertFalse(monopolyGameController.isReadyForNextPlayer());
                break;
            }
            case LAND_BLOCK: {
                break;
            }
            case ROAD_BLOCK: {
                break;
            }
            case UTILITY_BLOCK: {
                break;
            }
            default: {
                fail("Unexpected landing in an Unknown Block Type: "
                        + boardDisplayData.getCurrentPlayerBlock().getBlockType());
                break;
            }
        }
    }
}
