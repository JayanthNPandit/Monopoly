package com.monopoly.displays.data;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import com.monopoly.board.Block;
import com.monopoly.board.PropertyBlock;
import com.monopoly.displays.adapter.GameEngineGraphicalDisplayAdapter;
import com.monopoly.displays.helper.DisplayHelper;
import com.monopoly.gameplay.Dice;
import com.monopoly.gameplay.GameSettings;
import com.monopoly.gameplay.MonopolyGameEngine;
import com.monopoly.player.Player;

public class BoardDisplayData {
    public static final int BLOCK_NUMBER_FOR_GO_BLOCK = 1;
    public static final int EMPTY_DICE_ROLL = 0;
    protected static final int MIN_DICE_VALUE = 1;
    protected static final int MAX_DICE_VALUE = 6;

    // Not using 0 position in the arrays
    private static final BlockCoordinate[] BLOCK_TO_BOARD_MAPPING = new BlockCoordinate[GameSettings.MAX_BLOCKS_PER_BOARD
            + 1];
    // Not using 0 position in the arrays
    private static final int[][] BOARD_TO_CARD_MAPPING = new int[GameEngineGraphicalDisplayAdapter.NUM_ROWS_PER_BOARD
            + 1][GameEngineGraphicalDisplayAdapter.NUM_COLUMNS_PER_ROW + 1];

    private final long startTime = System.currentTimeMillis();

    private String gamePlayTimeDisplay = "";

    private List<BlockDisplayData> displayBlocks = null;
    private List<GameMessageData> gameMessages = new ArrayList<>();

    private int numberOfPlayers = GameEngineGraphicalDisplayAdapter.getTotalNumberOfPlayers();
    // TODO: Replace this with monopoly game engine version of current player number
    private int currentPlayerNumber = GameEngineGraphicalDisplayAdapter.UNKNOWN_PLAYER;
    // Not using 0 position in the arrays
    // private int[] playerCurrentBlockNumber = new int[numberOfPlayers + 1];

    private int gamePlayTurnNumber = GameEngineGraphicalDisplayAdapter.GAME_START_TURN_NUMBER;
    private GameTurnStatusEnum gameTurnStatus = GameTurnStatusEnum.DUMMY_STATUS;
    private boolean currentPlayerTookRequiredAction = false;
    private DiceStatusEnum diceRolling = DiceStatusEnum.NOT_ROLLING;

    /*
     * This method is only used for JUnit testing purposes
     */
    public BoardDisplayData() {
        this(null);
    }

    public BoardDisplayData(List<Block> monopolyBlocks) {
        BoardDisplayData.initializeBoardToCardMapping();
        if (monopolyBlocks != null) {
            this.initializeBlockDisplayData(monopolyBlocks);
        } else {
            /*
             * This method is only used for JUnit testing purposes
             */
            this.initializeBlockDisplayData();
        }
        this.initializePlayerDisplayData();

        this.setGameTurnStatus(GameTurnStatusEnum.READY_TO_START);
    }

    private static void initializeBoardToCardMapping() {
        /**
         * All "board coordindates" are mapped to "card indices."
         * 
         * Board Coordindates flows top-to-bottom and left-to-right.
         * 
         * Board Coordindates starts from [1][1] (Top-Left Corner == "Free Parking"
         * Block) and flows to the right-end of the Board at [1][11] (Top-Right Corner
         * == "Go Directly to Jail" Block). Board Coordindates then comes down one row
         * which only has two Blocks: [2][1] (left-side 2nd row) and [2][11] (right-side
         * 2nd row). Board Coordindates keeps flowing down, one row at a time, to the
         * last row which goes from [11][1] (bottom-left-corner == "Jail" Block) to
         * [11][11] (bottom-right-corner == "Go" Block).
         * 
         * 2D Array is structured as follows:
         * boardToCardMapping[ROW_INDEX][COLUMN_INDEX] = CARD_INDEX
         */

        // Row 1 of the Board == Top Row
        BLOCK_TO_BOARD_MAPPING[21] = new BlockCoordinate(1, 1);
        BOARD_TO_CARD_MAPPING[1][1] = 21; // "Free Parking" Block

        BLOCK_TO_BOARD_MAPPING[22] = new BlockCoordinate(1, 2);
        BOARD_TO_CARD_MAPPING[1][2] = 22;

        BLOCK_TO_BOARD_MAPPING[23] = new BlockCoordinate(1, 3);
        BOARD_TO_CARD_MAPPING[1][3] = 23;

        BLOCK_TO_BOARD_MAPPING[24] = new BlockCoordinate(1, 4);
        BOARD_TO_CARD_MAPPING[1][4] = 24;

        BLOCK_TO_BOARD_MAPPING[25] = new BlockCoordinate(1, 5);
        BOARD_TO_CARD_MAPPING[1][5] = 25;

        BLOCK_TO_BOARD_MAPPING[26] = new BlockCoordinate(1, 6);
        BOARD_TO_CARD_MAPPING[1][6] = 26;

        BLOCK_TO_BOARD_MAPPING[27] = new BlockCoordinate(1, 7);
        BOARD_TO_CARD_MAPPING[1][7] = 27;

        BLOCK_TO_BOARD_MAPPING[28] = new BlockCoordinate(1, 8);
        BOARD_TO_CARD_MAPPING[1][8] = 28;

        BLOCK_TO_BOARD_MAPPING[29] = new BlockCoordinate(1, 9);
        BOARD_TO_CARD_MAPPING[1][9] = 29;

        BLOCK_TO_BOARD_MAPPING[30] = new BlockCoordinate(1, 10);
        BOARD_TO_CARD_MAPPING[1][10] = 30;

        BLOCK_TO_BOARD_MAPPING[31] = new BlockCoordinate(1, 11);
        BOARD_TO_CARD_MAPPING[1][11] = 31; // "Go Directly to Jail" Block

        // Row 2 of the Board
        BLOCK_TO_BOARD_MAPPING[20] = new BlockCoordinate(2, 1);
        BOARD_TO_CARD_MAPPING[2][1] = 20;

        BLOCK_TO_BOARD_MAPPING[32] = new BlockCoordinate(2, 11);
        BOARD_TO_CARD_MAPPING[2][11] = 32;

        // Row 3 of the Board
        BLOCK_TO_BOARD_MAPPING[19] = new BlockCoordinate(3, 1);
        BOARD_TO_CARD_MAPPING[3][1] = 19;

        BLOCK_TO_BOARD_MAPPING[33] = new BlockCoordinate(3, 11);
        BOARD_TO_CARD_MAPPING[3][11] = 33;

        // Row 4 of the Board
        BLOCK_TO_BOARD_MAPPING[18] = new BlockCoordinate(4, 1);
        BOARD_TO_CARD_MAPPING[4][1] = 18;

        BLOCK_TO_BOARD_MAPPING[34] = new BlockCoordinate(4, 11);
        BOARD_TO_CARD_MAPPING[4][11] = 34;

        // Row 5 of the Board
        BLOCK_TO_BOARD_MAPPING[17] = new BlockCoordinate(5, 1);
        BOARD_TO_CARD_MAPPING[5][1] = 17;

        BLOCK_TO_BOARD_MAPPING[35] = new BlockCoordinate(5, 11);
        BOARD_TO_CARD_MAPPING[5][11] = 35;

        // Row 6 of the Board
        BLOCK_TO_BOARD_MAPPING[16] = new BlockCoordinate(6, 1);
        BOARD_TO_CARD_MAPPING[6][1] = 16;

        BLOCK_TO_BOARD_MAPPING[36] = new BlockCoordinate(6, 11);
        BOARD_TO_CARD_MAPPING[6][11] = 36;

        // Row 7 of the Board
        BLOCK_TO_BOARD_MAPPING[15] = new BlockCoordinate(7, 1);
        BOARD_TO_CARD_MAPPING[7][1] = 15;

        BLOCK_TO_BOARD_MAPPING[37] = new BlockCoordinate(7, 11);
        BOARD_TO_CARD_MAPPING[7][11] = 37;

        // Row 8 of the Board
        BLOCK_TO_BOARD_MAPPING[14] = new BlockCoordinate(8, 1);
        BOARD_TO_CARD_MAPPING[8][1] = 14;

        BLOCK_TO_BOARD_MAPPING[38] = new BlockCoordinate(8, 11);
        BOARD_TO_CARD_MAPPING[8][11] = 38;

        // Row 9 of the Board
        BLOCK_TO_BOARD_MAPPING[13] = new BlockCoordinate(9, 1);
        BOARD_TO_CARD_MAPPING[9][1] = 13;

        BLOCK_TO_BOARD_MAPPING[39] = new BlockCoordinate(9, 11);
        BOARD_TO_CARD_MAPPING[9][11] = 39;

        // Row 10 of the Board
        BLOCK_TO_BOARD_MAPPING[12] = new BlockCoordinate(10, 1);
        BOARD_TO_CARD_MAPPING[10][1] = 12;

        BLOCK_TO_BOARD_MAPPING[40] = new BlockCoordinate(10, 11);
        BOARD_TO_CARD_MAPPING[10][11] = 40;

        // Row 11 of the Board == Bottom Row
        BLOCK_TO_BOARD_MAPPING[11] = new BlockCoordinate(11, 1);
        BOARD_TO_CARD_MAPPING[11][1] = 11; // "Jail" Block

        BLOCK_TO_BOARD_MAPPING[10] = new BlockCoordinate(11, 2);
        BOARD_TO_CARD_MAPPING[11][2] = 10;

        BLOCK_TO_BOARD_MAPPING[9] = new BlockCoordinate(11, 3);
        BOARD_TO_CARD_MAPPING[11][3] = 9;

        BLOCK_TO_BOARD_MAPPING[8] = new BlockCoordinate(11, 4);
        BOARD_TO_CARD_MAPPING[11][4] = 8;

        BLOCK_TO_BOARD_MAPPING[7] = new BlockCoordinate(11, 5);
        BOARD_TO_CARD_MAPPING[11][5] = 7;

        BLOCK_TO_BOARD_MAPPING[6] = new BlockCoordinate(11, 6);
        BOARD_TO_CARD_MAPPING[11][6] = 6;

        BLOCK_TO_BOARD_MAPPING[5] = new BlockCoordinate(11, 7);
        BOARD_TO_CARD_MAPPING[11][7] = 5;

        BLOCK_TO_BOARD_MAPPING[4] = new BlockCoordinate(11, 8);
        BOARD_TO_CARD_MAPPING[11][8] = 4;

        BLOCK_TO_BOARD_MAPPING[3] = new BlockCoordinate(11, 9);
        BOARD_TO_CARD_MAPPING[11][9] = 3;

        BLOCK_TO_BOARD_MAPPING[2] = new BlockCoordinate(11, 10);
        BOARD_TO_CARD_MAPPING[11][10] = 2;

        BLOCK_TO_BOARD_MAPPING[1] = new BlockCoordinate(11, 11);
        BOARD_TO_CARD_MAPPING[11][11] = 1; // "Go" Block
    }

    private void initializeBlockDisplayData(List<Block> monopolyBlocks) {
        this.displayBlocks = new ArrayList<BlockDisplayData>();
        // Initialize the "position 0" of the array with a Dummy Block that never gets
        // used from "position 0"

        // Removing Dummy Block in the Display code because now the main code base has
        // the FakeBlock
        // Block unusedBlock = Block.getDummyBlock();
        // this.displayBlocks.add(new BlockDisplayData(unusedBlock, numberOfPlayers));

        for (Block block : monopolyBlocks) {
            this.displayBlocks.add(new BlockDisplayData(block, numberOfPlayers));
        }
    }

    /*
     * This method is only used for JUnit testing purposes
     */
    private void initializeBlockDisplayData() {
        this.displayBlocks = new ArrayList<BlockDisplayData>();

        // Dummy Block used to initialize the Board Display Blocks
        Block unusedBlock = Block.getDummyBlock();

        // Initialize the "position 0" of the array with a Dummy Block that never gets
        // used from "position 0"
        this.displayBlocks.add(new BlockDisplayData(unusedBlock, numberOfPlayers));

        // Initialize the array with Dummy Blocks into all other positions
        // This is needed because ArrayList.put() can not be used unless a pre-existing
        // object has been
        // placed into that index
        for (int x = 1; x <= GameEngineGraphicalDisplayAdapter.NUM_ROWS_PER_BOARD; x++) {
            for (int y = 1; y <= GameEngineGraphicalDisplayAdapter.NUM_COLUMNS_PER_ROW; y++) {
                BlockDisplayData blockDisplayData = new BlockDisplayData(unusedBlock, numberOfPlayers);
                this.displayBlocks.add(blockDisplayData);

            }
        }

        // Block Number calculated based on the BOARD_TO_CARD_MAPPING is the true block
        // sequence from Game
        // Flow point of view; Block Number does not represent the "display sequence" of
        // rows/columns on the
        // display grid
        // Now set the "real Block" objects into their "real positions" in the ArrayList
        for (int x = 1; x <= GameEngineGraphicalDisplayAdapter.NUM_ROWS_PER_BOARD; x++) {
            for (int y = 1; y <= GameEngineGraphicalDisplayAdapter.NUM_COLUMNS_PER_ROW; y++) {
                int blockNumber = BOARD_TO_CARD_MAPPING[x][y];
                if (blockNumber > 0) {
                    Block placeholderBlock = Block.getDummyBlock();
                    BlockDisplayData blockDisplayData = new BlockDisplayData(placeholderBlock, numberOfPlayers);
                    this.displayBlocks.set(blockNumber, blockDisplayData);
                }
            }
        }
    }

    private void initializePlayerDisplayData() {
        // Set all players to start at the "block named GO"
        /*
         * for (int playerIndex = 1; playerIndex <= numberOfPlayers; playerIndex++) {
         * this.playerCurrentBlockNumber[playerIndex] = BLOCK_NUMBER_FOR_GO_BLOCK;
         * (this.displayBlocks.get(BLOCK_NUMBER_FOR_GO_BLOCK)).playerLandedOnBlock(
         * playerIndex); }
         */

        this.currentPlayerNumber = GameSettings.FIRST_PLAYER_NUMBER;
    }

    public boolean isCurrentPlayerRequiredActionsDone() {
        return this.currentPlayerTookRequiredAction;
    }

    protected void currentPlayerTookRequiredAction(boolean playerTookRequiredAction) {
        this.currentPlayerTookRequiredAction = playerTookRequiredAction;
    }

    public GameTurnStatusEnum getGameTurnStatus() {
        return gameTurnStatus;
    }

    protected void setGameTurnStatus(GameTurnStatusEnum gameTurnStatus) {
        this.gameTurnStatus = gameTurnStatus;
    }

    public int getCurrentPlayerNumber() {
        return MonopolyGameEngine.getInstance().getCurrentlyPlayingPlayerNumber();
    }

    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }

    public BlockDisplayData getDisplayBlock(int row, int column) {
        return this.displayBlocks.get(BOARD_TO_CARD_MAPPING[row][column]);
    }

    public int getGamePlayTurnNumber() {
        return this.gamePlayTurnNumber;
    }

    public String getGameTurnDisplay() {
        return String.format("Game Play Turn # %03d; Current Player # %d", this.getGamePlayTurnNumber(),
                this.getCurrentPlayerNumber());
    }

    public String getGamePlayTimeDisplay() {
        return this.gamePlayTimeDisplay;
    }

    public void updateGamePlayTimeDisplayData() {
        this.gamePlayTimeDisplay = DisplayHelper.getPlayTime(System.currentTimeMillis(), this.startTime);
    }

    public List<BlockDisplayData> getDisplayBlocks() {
        return this.displayBlocks;
    }

    public BlockDisplayData getDisplayBlock(int blockNumber) {
        return this.displayBlocks.get(blockNumber);
    }

    public List<GameMessageData> getGameMessages() {
        return gameMessages;
    }

    public void clearGameMessages() {
        clearGameMessages(false);
    }

    public void clearGameMessages(boolean preserveHappySadMessages) {
        List<GameMessageData> preservedMessages = new ArrayList<>();

        if (preserveHappySadMessages) {
            for (GameMessageData gameMessage : this.gameMessages) {
                if (gameMessage.isMessageToBePreserved()) {
                    preservedMessages.add(gameMessage);
                }
            }
        }

        this.gameMessages.clear();
        this.gameMessages.addAll(preservedMessages);
    }

    public void prependGameInstruction(String gameInstructionMessage) {
        List<GameMessageData> preservedMessages = new ArrayList<>();
        preservedMessages.addAll(this.gameMessages);

        this.gameMessages.clear();

        addGameInstruction(gameInstructionMessage);
        this.gameMessages.addAll(preservedMessages);
    }

    public void addGameInstruction(String gameInstructionMessage) {
        addGameMessage(gameInstructionMessage, GameMessageTypeEnum.TURN_INSTRUCTIONS);
    }

    public void addGameErrorMessages(String gameErrorMessage) {
        addGameMessage(gameErrorMessage, GameMessageTypeEnum.TURN_ERROR_MESSAGE);
    }

    public void addGameHappyMessage(String gameHappyMessage) {
        addGameMessage(gameHappyMessage, GameMessageTypeEnum.HAPPY_MESSAGE);
    }

    public void addGameSadMessage(String gameSadMessage) {
        addGameMessage(gameSadMessage, GameMessageTypeEnum.SAD_MESSAGE);
    }

    private void addGameMessage(String gameMessage, GameMessageTypeEnum mesageType) {
        this.gameMessages.add(new GameMessageData(gameMessage, mesageType));
    }

    public void startDiceRolling() {
        this.diceRolling = DiceStatusEnum.DICE_ROLLING_NOW;
    }

    public void stopDiceRolling() {
        this.diceRolling = DiceStatusEnum.DICE_FINISHED_ROLLING;
    }

    public boolean isDiceRolling() {
        return diceRolling.isDiceRolling();
    }

    public boolean isDiceFinishedRolling() {
        return diceRolling.isDiceFinishedRolling();
    }

    private void resetDice() {
        this.diceRolling = DiceStatusEnum.NOT_ROLLING;
        MonopolyGameEngine.getInstance().resetDice();
    }

    public void rollDice() {
        // TODO: Change this class (BoardDisplayData) to fully use the Dice.java class
        // rather than having its own private int variables for dice_1 and dice_2
        MonopolyGameEngine.getInstance().rollDice();
        this.setGameTurnStatus(GameTurnStatusEnum.PENDING_MANDATORY_STEPS);
    }

    public int getDiceRoll_1() {
        return MonopolyGameEngine.getInstance().getDice().getDiceOneValue();
    }

    public int getDiceRoll_2() {
        return MonopolyGameEngine.getInstance().getDice().getDiceTwoValue();
    }

    protected int getDiceTotal() {
        return MonopolyGameEngine.getInstance().getDice().getTotalDiceValue();
    }

    public Dice getDice() {
        return MonopolyGameEngine.getInstance().getDice();
    }

    private boolean isDiceThrownAlready() {
        return ((MonopolyGameEngine.getInstance().getDice().getDiceOneValue() != BoardDisplayData.EMPTY_DICE_ROLL)
                && (MonopolyGameEngine.getInstance().getDice().getDiceTwoValue() != BoardDisplayData.EMPTY_DICE_ROLL));
    }

    private Player getPlayer(int playerNumber) {
        return MonopolyGameEngine.getInstance().getPlayer(playerNumber);
    }

    private int getPlayerBlockNumber(int playerNumber) {
        return getPlayer(playerNumber).getCurrentBlock().getBlockNumber();
    }

    public String getPlayerName(int playerNumber) {
        return getPlayer(playerNumber).getName();
    }

    public int getPlayerTurn(int playerNumber) {
        return getPlayer(playerNumber).getNumberOfTurns();
    }

    public int getNumberOfRoundTheBoardTrips(int playerNumber) {
        return getPlayer(playerNumber).getNumberOfRoundTheBoardTrips();
    }

    public int getPlayerNetWorth(int playerNumber) {
        return getPlayer(playerNumber).getTotalNetWorth();
    }

    public int getPlayerCash(int playerNumber) {
        return getPlayer(playerNumber).getCurrentCash();
    }

    public int getPlayerPropertyCount(int playerNumber) {
        return getPlayer(playerNumber).getOwnedProperties().size();
    }

    public List<BlockDisplayData> getPlayerOwnedBlocks(int playerNumber) {
        List<PropertyBlock> ownedBlocks = getPlayer(playerNumber).getOwnedProperties();
        List<BlockDisplayData> ownedDisplayBlocks = new ArrayList<>();

        for (PropertyBlock ownedBlock : ownedBlocks) {
            ownedDisplayBlocks.add(new BlockDisplayData(ownedBlock, GameSettings.getNumberOfPlayers()));
        }

        return ownedDisplayBlocks;
    }

    public BlockDisplayData getPlayerBlock(int playerNumber) {
        return this.getDisplayBlock(this.getPlayerBlockNumber(playerNumber));
    }

    public BlockDisplayData getCurrentPlayerBlock() {
        return this.getPlayerBlock(this.getCurrentPlayerNumber());
    }

    private void setPlayerBlock(int playerNumber, int blockNumber) {
        /*
         * // Move the player away from the previous block
         * this.getDisplayBlock(this.getPlayerBlockNumber(playerNumber)).
         * playerMovedFromBlock(playerNumber);
         * 
         * // Set the player on the new block
         * this.getDisplayBlock(blockNumber).playerLandedOnBlock(playerNumber);
         */
        getPlayer(playerNumber)
                .setCurrentBlock(MonopolyGameEngine.getInstance().getBoard().getBlockByIndex(blockNumber));
    }

    public void moveCurrentPlayerBasedOnDiceThrow() {
        Player currentPlayer = MonopolyGameEngine.getInstance().getCurrentlyPlayingPlayer();
        MonopolyGameEngine.getInstance().movePlayer(currentPlayer, MonopolyGameEngine.getInstance().getDice());

        int playerNewBlockNumber = currentPlayer.getCurrentBlock().getBlockNumber();

        if (currentPlayer.hasPlayerPassedGo()) {
            this.addGameHappyMessage("Congratulations on surviving one more round without getting Bankrupt!");
        }

        if (playerNewBlockNumber == BoardDisplayData.BLOCK_NUMBER_FOR_GO_BLOCK) {
            this.addGameHappyMessage(
                    "Great you landed on Go! Congratulations on surviving one more round without getting Bankrupt!");
        }
    }

    public void resetForNextPlayer() {
        this.playerDoneWithTurn();

        MonopolyGameEngine.getInstance().setupForNextPlayer();

        // Currently counting "game turn" after each Player's Turn
        // Later maybe set this only after all Players have played their turn
        // Move this inside setNextPlayerNumber method's if logic
        this.gamePlayTurnNumber++;

        this.setGameTurnStatus(GameTurnStatusEnum.READY_TO_START);
    }

    private void playerDoneWithTurn() {
        this.currentPlayerTookRequiredAction = false;
        this.resetDice();
        this.setGameTurnStatus(GameTurnStatusEnum.READY_FOR_NEXT_PLAYER);
    }

    public void waitingForPlayerMandatoryActions() {
        this.setGameTurnStatus(GameTurnStatusEnum.PENDING_MANDATORY_STEPS);
    }

    public void noMandatoryActionsFromPlayer() {
        if (this.isCurrentPlayerRequiredActionsDone()) {
            this.setGameTurnStatus(GameTurnStatusEnum.READY_FOR_NEXT_PLAYER);
        } else {
            this.setGameTurnStatus(GameTurnStatusEnum.PENDING_OPTIONAL_STEPS);
        }
    }

    public boolean isCurrentPlayerPlaying() {
        return this.getGameTurnStatus().isCurrentPlayerPlaying() && this.isDiceThrownAlready();
    }

    public boolean isReadyForNextPlayer() {
        return this.getGameTurnStatus().isReadyForNextPlayer();
    }

    public boolean isReadyToStartTurn() {
        return this.getGameTurnStatus().isReadyToStartTurn();
    }

    private boolean isBlockOwnedByCurrentPlayer(BlockDisplayData currentBlock) {
        if (currentBlock.getBlock().isBlockPurchasable()) {
            return ((PropertyBlock) currentBlock.getBlock()).isOwnedByPlayer(this.getCurrentPlayerNumber());
        } else {
            return false;
        }
    }

    public boolean isCurrentPlayerAllowedToPurchaseCurentBlock() {
        if (this.isCurrentPlayerPlaying()) {
            BlockDisplayData currentBlock = this.getCurrentPlayerBlock();

            return currentBlock.getBlock().isAvailableForPurchase();
        } else {
            // If current Player's turn is over or the Dice hasn't been rolled (so the turn
            // hasn't started) then
            // the current Player can't take any actions
            return false;
        }
    }

    public void purchaseProperty() {
        if (this.getCurrentPlayerBlock().getBlock().isBlockPurchasable()) {
            Player currentPlayer = MonopolyGameEngine.getInstance().getPlayerFromNumber(this.getCurrentPlayerNumber());
            PropertyBlock propertyBlockToBuy = (PropertyBlock) this.getCurrentPlayerBlock().getBlock();
            currentPlayer.purchaseProperty(propertyBlockToBuy);
        }
        this.currentPlayerTookRequiredAction(true);
    }

    public boolean isCurrentPlayerRequiredToPayRentForCurrentBlock() {
        if (this.isCurrentPlayerPlaying()) {
            BlockDisplayData currentBlock = this.getCurrentPlayerBlock();

            if (currentBlock.getBlock().isBlockPurchasable()) {
                return (currentBlock.getBlock().isBlockRentable())
                        && (((PropertyBlock) currentBlock.getBlock()).isOwned())
                        && (this.isBlockOwnedByCurrentPlayer(currentBlock) == false);
            } else {
                return false;
            }
        } else {
            // If current Player's turn is over or the Dice hasn't been rolled (so the turn
            // hasn't started) then
            // the current Player can't take any actions
            return false;
        }
    }

    public void payRent() {
        if (this.getCurrentPlayerBlock().getBlock().isBlockRentable()) {
            Player currentPlayer = MonopolyGameEngine.getInstance().getPlayerFromNumber(this.getCurrentPlayerNumber());
            PropertyBlock propertyBlockToRent = (PropertyBlock) this.getCurrentPlayerBlock().getBlock();
            currentPlayer.payRent(propertyBlockToRent);
        }
        this.currentPlayerTookRequiredAction(true);
    }

    public boolean isCurrentPlayerAllowedToBuildHousesHotelsOnCurentBlock() {
        if (this.isCurrentPlayerPlaying()) {
            BlockDisplayData currentBlock = this.getCurrentPlayerBlock();

            return (this.isBlockOwnedByCurrentPlayer(currentBlock)) && (MonopolyGameEngine.getInstance().getBoard()
                    .isPropertyInMonopolyOwnership(currentBlock.getBlockNumber()));
        } else {
            // If current Player's turn is over or the Dice hasn't been rolled (so the turn
            // hasn't started) then
            // the current Player can't take any actions
            return false;
        }
    }

    public void buildHousesHotels(boolean buildingHouseHotelSuccessful) {
        if (buildingHouseHotelSuccessful == true) {
            this.addGameHappyMessage("Da da da daaaaan, welcome to ur brand new home!");
        } else if (buildingHouseHotelSuccessful == false) {
            this.addGameSadMessage("Hey, you can't buy anymore buildingy thinkamajinkibonkerses, now can you?");
        }
        this.currentPlayerTookRequiredAction(true);
    }

    public boolean isCurrentPlayerAllowedToPickChanceCommunityCardOnCurentBlock() {
        if (this.isCurrentPlayerPlaying()) {
            BlockDisplayData currentBlock = this.getCurrentPlayerBlock();

            return currentBlock.isChanceOrCommunityBlock();
        } else {
            // If current Player's turn is over or the Dice hasn't been rolled (so the turn
            // hasn't started) then
            // the current Player can't take any actions
            return false;
        }
    }

    public void pickChanceCommunityCard() {
        // TODO: This has stopped working as of 10/18
        // all messages failing
        // TODO: fix above
        String specialCardMessage = MonopolyGameEngine.getInstance().getSelectedSpecialCard()
                .getSpecialCardDescription();
        // TODO: Fix wrong messaging in the display
        // TODO: Fix chance card not being displayed on screen Clue: It started doing
        // that error after the message went off screen as programmed
        if (MonopolyGameEngine.getInstance().getSelectedSpecialCard().isGoodSpecialCard() == true) {
            this.addGameHappyMessage(specialCardMessage);
        } else if (MonopolyGameEngine.getInstance().getSelectedSpecialCard().isGoodSpecialCard() == false) {
            this.addGameSadMessage(specialCardMessage);
        } else {
            new InvalidParameterException("Reached an impossible state have we?");
        }
    }

    public boolean isCurrentPlayerAllowedToPerformGovernmentActionOnCurentBlock() {
        if (this.isCurrentPlayerPlaying()) {
            BlockDisplayData currentBlock = this.getCurrentPlayerBlock();

            return (currentBlock.getBlockType().isGovernmentBlock());
        } else {
            // If current Player's turn is over or the Dice hasn't been rolled (so the turn
            // hasn't started) then
            // the current Player can't take any actions
            return false;
        }
    }

    public void displayGovernmentActionMessage(String governmentActionMessage, boolean happyMessage) {
        if (happyMessage) {
            this.addGameHappyMessage(governmentActionMessage);
        } else {
            this.addGameSadMessage(governmentActionMessage);
        }
        this.currentPlayerTookRequiredAction(true);
    }
}
