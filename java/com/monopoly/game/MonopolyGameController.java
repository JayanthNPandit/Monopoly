package com.monopoly.game;

import com.monopoly.board.Block;
import com.monopoly.board.BlockTypeEnum;
import com.monopoly.board.LandBlock;
import com.monopoly.board.PropertyBlock;
import com.monopoly.displays.data.BlockDisplayData;
import com.monopoly.displays.data.BoardDisplayData;
import com.monopoly.displays.graphical.game_window.GameWindow;
import com.monopoly.displays.helper.DisplayHelper;
import com.monopoly.gameplay.MonopolyGameEngine;
import com.monopoly.player.Player;

public class MonopolyGameController {
    private static MonopolyGameController instance = null;
    private BoardDisplayData boardDisplayData;
    private GameWindow gameWindow;
    public boolean playSounds;

    private MonopolyGameController(BoardDisplayData aBoardDisplayData) {
        this.boardDisplayData = aBoardDisplayData;
        this.nextPlayerTurn(true);
    }

    public static MonopolyGameController getInstance() {
        if (instance == null) {
            throw new IllegalStateException("MonopolyGameController instance has not been created yet!");
        }
        return instance;
    }

    public static MonopolyGameController createInstance(BoardDisplayData aBoardDisplayData) {
        MonopolyGameController.instance = new MonopolyGameController(aBoardDisplayData);
        return MonopolyGameController.instance;
    }

    public BoardDisplayData getMonopolyBoardDisplayData() {
        return this.boardDisplayData;
    }

    private void handleUnknownTypeOfGameBlock(String methodName) {
        DisplayHelper.debugLog("Unknown Type of Game Block in " + this.getClass().getName() + "." + methodName);
    }

    public boolean isReadyToStartTurn() {
        return this.boardDisplayData.isReadyToStartTurn();
    }

    public boolean isCurrentPlayerPlaying() {
        System.out.println("What is the game turn status: " + this.boardDisplayData.getGameTurnStatus());
        return this.boardDisplayData.isCurrentPlayerPlaying();
    }

    public boolean isReadyForNextPlayer() {
        return this.boardDisplayData.isReadyForNextPlayer();
    }

    public void continueGamePlay() {
        if (isReadyForNextPlayer()) {
            // this.boardDisplayData.randomizeBlock();
        }

        this.boardDisplayData.updateGamePlayTimeDisplayData();
    }

    public void saveAndExitGame() {
        DisplayHelper.infoLog("(Will eventually) Save Game...");
        System.exit(0);
    }

    public void startDiceRolling() {
        this.boardDisplayData.startDiceRolling();
    }

    public void stopDiceRolling() {
        this.boardDisplayData.stopDiceRolling();
    }

    public boolean isDiceRolling() {
        return this.boardDisplayData.isDiceRolling();
    }

    public void rollDice() {
        this.boardDisplayData.rollDice();
    }

    public int getCurrentPlayerNumber() {
        return MonopolyGameEngine.getInstance().getCurrentlyPlayingPlayerNumber();
    }

    public boolean shouldSoundBePlayed() {
        return this.playSounds;
    }

    private void turnOnSoundPlaying() {
        this.playSounds = true;
    }

    public void soundPlayedOnce() {
        this.playSounds = false;
    }

    public void startPlayerTurn() {
        this.turnOnSoundPlaying();

        // TODO: TWO MOVES happening: 1) moveCurrentPlayerBasedOnDiceThrow and 2)
        // playerTurn
        // Player's token move based on Dice Throw
        this.boardDisplayData.moveCurrentPlayerBasedOnDiceThrow();

        refreshGameInstructions();

        // Player's Decisions will trigger its own events
        MonopolyGameEngine.getInstance().playerTurn(
                MonopolyGameEngine.getInstance().getPlayer(this.getCurrentPlayerNumber()),
                this.boardDisplayData.getDice());
    }

    public void displayTemporaryGameMessages(String message) {
        // Display Player's Information based on Dice Throw
        this.boardDisplayData.clearGameMessages(true);

        this.boardDisplayData.prependGameInstruction(message);
    }

    private void refreshGameInstructions() {
        // Display Player's Information based on Dice Throw
        this.boardDisplayData.clearGameMessages(true);

        this.boardDisplayData.prependGameInstruction(
                String.format("Player # %d threw dice roll of %d & %d", this.getCurrentPlayerNumber(),
                        this.boardDisplayData.getDiceRoll_1(), this.boardDisplayData.getDiceRoll_2()));
        // Display Player's Instructions/Options based on the Block the Player Landed On
        // Get the currently landed Property
        boolean playerMustTakeAction = addBlockSpecificGameMessages(this.boardDisplayData.getCurrentPlayerBlock(),
                this.getCurrentPlayerNumber());

        if (playerMustTakeAction) {
            // If needed wait for Player's Mandatory Actions (Pay Rent, Etc.)
            this.boardDisplayData.waitingForPlayerMandatoryActions();
        } else {
            // If no Mandatory Actions needed then still wait for Player to review Game
            // State or Player's
            // Let the Player take any Optional/Actions/Decisions (Buy Property, Mortgage
            // Property, Etc.)
            this.boardDisplayData.noMandatoryActionsFromPlayer();
            if (MonopolyGameEngine.getInstance().isGameOver()) {
                this.boardDisplayData.addGameInstruction("Exit the Game You Shall!  No more Game for you; "
                        + MonopolyGameEngine.getInstance().getGameWinner().getName()
                        + " has won the game!  Go use the Potty and come back after you wash your hands!  Play Another Game if you want.");

            } else {
                this.boardDisplayData.addGameInstruction(
                        "Press (N) for Next Player AFTER the Current Player is done with the current Turn.");

            }
        }
    }

    private boolean addBlockSpecificGameMessages(BlockDisplayData currentDisplayBlock, int currentPlayer) {
        boolean playerMustTakeAction = false;

        // Decide what to do based on the type of Block it is
        switch (currentDisplayBlock.getBlockType()) {
        case DUMMY_BLOCK: {
            DisplayHelper.debugLog(
                    "Unexpected Dummy Game Block in " + this.getClass().getName() + ".addBlockSpecificGameMessages()");
            System.exit(1);
            break;
        }
        case LAND_BLOCK: {
            // Add Property Block specific messages based on the current Owner of the
            // Property
            playerMustTakeAction = addLandRoadUtilityPropertySpecificInstructions(currentDisplayBlock, currentPlayer);
            break;
        }
        case ROAD_BLOCK: {
            // Add Property Block specific messages based on the current Owner of the
            // Property
            playerMustTakeAction = addLandRoadUtilityPropertySpecificInstructions(currentDisplayBlock, currentPlayer);
            break;
        }
        case UTILITY_BLOCK: {
            // Add Property Block specific messages based on the current Owner of the
            // Property
            playerMustTakeAction = addLandRoadUtilityPropertySpecificInstructions(currentDisplayBlock, currentPlayer);
            break;
        }
        case CHANCE_BLOCK: {
            // Add Property Block specific messages based on the current Owner of the
            // Property
            playerMustTakeAction = addChanceCommunityBlockSpecificInstructions(currentDisplayBlock, currentPlayer);
            break;
        }
        case COMMUNITY_BLOCK: {
            // Add Property Block specific messages based on the current Owner of the
            // Property
            playerMustTakeAction = addChanceCommunityBlockSpecificInstructions(currentDisplayBlock, currentPlayer);
            break;
        }
        case LOTTERY_BLOCK:
        case JAIL_BLOCK:
        case GO_TO_JAIL_BLOCK:
        case CASH_PAYMENT_BLOCK:
        case GO_BLOCK: {
            // Add Property Block specific messages based on the current Owner of the
            // Property
            playerMustTakeAction = addGovernmentBlockSpecificInstructions(currentDisplayBlock, currentPlayer);
            break;
        }
        default: {
            this.handleUnknownTypeOfGameBlock("addBlockSpecificGameMessages()");
            System.exit(1);
        }
        }

        return playerMustTakeAction;
    }

    private boolean addLandRoadUtilityPropertySpecificInstructions(BlockDisplayData currentDisplayBlock,
            int currentPlayer) {
        boolean playerMustTakeAction = false;

        if (currentDisplayBlock.isBlockCurrentlyOwned()) {
            if (currentDisplayBlock.isOwnedByPlayer(currentPlayer)) {
                playerMustTakeAction = addInstructionsForCurrentPlayerOwnedProperty(currentDisplayBlock);
            } else {
                playerMustTakeAction = addInstructionsForAnotherPlayerProperty(currentDisplayBlock);
            }
        } else {
            playerMustTakeAction = addInstructionsForUnOwnedProperty(currentDisplayBlock);
        }

        return playerMustTakeAction;
    }

    private boolean addInstructionsForCurrentPlayerOwnedProperty(BlockDisplayData currentDisplayBlock) {
        boolean playerMustTakeAction = false;

        // Check to see if the current Player can build Houses/Hotels on this Property
        if (MonopolyGameEngine.getInstance().getBoard()
                .isPropertyInMonopolyOwnership(currentDisplayBlock.getBlockNumber())) {
            if (this.boardDisplayData.isCurrentPlayerRequiredActionsDone() == false) {
                if (currentDisplayBlock.isBlockBuildable()) {
                    this.boardDisplayData.addGameInstruction(
                            "Press (B) to Build Houses/Hotels for this Block: " + currentDisplayBlock.getBlockName());
                    playerMustTakeAction = false;
                }
            }
        } else {
            // TODO: Ensure that it works; broken as of 10/18/20
            this.boardDisplayData
                    .addGameHappyMessage("Welcome Home to Your Block: " + currentDisplayBlock.getBlockName());
            playerMustTakeAction = false;
        }
        return playerMustTakeAction;
    }

    private boolean addInstructionsForAnotherPlayerProperty(BlockDisplayData currentDisplayBlock) {
        boolean playerMustTakeAction = false;

        if (this.boardDisplayData.isCurrentPlayerRequiredActionsDone() == false) {
            // Since this Property is owned by somebody else, ask the Player to Pay Rent
            this.boardDisplayData
                    .addGameInstruction("Press (R) to Pay Rent to " + currentDisplayBlock.getCurrentOwnerName()
                            + " for this Block: " + currentDisplayBlock.getBlockName());
            playerMustTakeAction = true;
        } else {
            this.boardDisplayData.addGameHappyMessage(
                    "Thank You for Paying Your Rent on Time for this Block: " + currentDisplayBlock.getBlockName());
            playerMustTakeAction = false;
        }

        return playerMustTakeAction;
    }

    private boolean addInstructionsForUnOwnedProperty(BlockDisplayData currentDisplayBlock) {
        boolean playerMustTakeAction = false;

        // Add instructions for purchasing the Property
        this.boardDisplayData
                .addGameInstruction("Press (P) to Purchase this Block: " + currentDisplayBlock.getBlockName());
        playerMustTakeAction = false;

        return playerMustTakeAction;
    }

    private boolean addChanceCommunityBlockSpecificInstructions(BlockDisplayData currentDisplayBlock,
            int currentPlayer) {
        boolean playerMustTakeAction = true;
        String gameInstructionMessage = null;

        if (this.boardDisplayData.isCurrentPlayerRequiredActionsDone() == false) {
            switch (currentDisplayBlock.getBlockType()) {
            case CHANCE_BLOCK: {
                gameInstructionMessage = "Press (C) to pick up the Chance Card.";
                break;
            }
            case COMMUNITY_BLOCK: {
                gameInstructionMessage = "Press (C) to pick up the Community Card.";
                break;
            }
            default: {
                this.handleUnknownTypeOfGameBlock("addChanceCommunityBlockSpecificInstructions()");
                System.exit(1);
            }
            }

            this.boardDisplayData.addGameInstruction(gameInstructionMessage);
            playerMustTakeAction = true;
        } else {
            playerMustTakeAction = false;
        }
        return playerMustTakeAction;
    }

    private boolean addGovernmentBlockSpecificInstructions(BlockDisplayData currentDisplayBlock, int currentPlayer) {
        boolean playerMustTakeAction = true;
        String gameInstructionMessage = null;

        if (this.boardDisplayData.isCurrentPlayerRequiredActionsDone() == false) {
            switch (currentDisplayBlock.getBlockType()) {
            case LOTTERY_BLOCK:
            case JAIL_BLOCK:
            case GO_TO_JAIL_BLOCK:
            case CASH_PAYMENT_BLOCK:
            case GO_BLOCK: {
                gameInstructionMessage = "Press (G) to act on your Government Block Action";
                break;
            }
            default: {
                DisplayHelper.debugLog("Unknown Type of Game Block in " + this.getClass().getName()
                        + ".addGovernmentBlockSpecificInstructions()");
                System.exit(1);
            }
            }

            this.boardDisplayData.addGameInstruction(gameInstructionMessage);
            playerMustTakeAction = true;
        } else {
            playerMustTakeAction = false;
        }
        return playerMustTakeAction;
    }

    public void nextPlayerTurn() {
        this.nextPlayerTurn(false);
    }

    private void nextPlayerTurn(boolean initialGameStart) {
        if (initialGameStart == false) {
            this.boardDisplayData.resetForNextPlayer();
        }

        this.boardDisplayData.clearGameMessages();
        this.boardDisplayData.addGameInstruction("Press (D) to Roll the Dice.");
    }

    public void setErrorMessage(String errorMessage) {
        this.boardDisplayData.addGameErrorMessages(errorMessage);
    }

    public boolean isCurrentPlayerAllowedToPurchaseCurentBlock() {
        return this.boardDisplayData.isCurrentPlayerAllowedToPurchaseCurentBlock();
    }

    public void purchaseProperty() {
        this.boardDisplayData.purchaseProperty();
        this.boardDisplayData.noMandatoryActionsFromPlayer();
        this.refreshGameInstructions();
    }

    public boolean isCurrentPlayerRequiredToPayRentForCurrentBlock() {
        return this.boardDisplayData.isCurrentPlayerRequiredToPayRentForCurrentBlock();
    }

    public void payRent() {
        this.boardDisplayData.payRent();
        this.boardDisplayData.noMandatoryActionsFromPlayer();
        this.refreshGameInstructions();
    }

    public boolean isCurrentPlayerAllowedToBuildHousesHotelsOnCurentBlock() {
        return this.boardDisplayData.isCurrentPlayerAllowedToBuildHousesHotelsOnCurentBlock();
    }

    public void buildHousesHotels() {
        Player currentPlayer = MonopolyGameEngine.getInstance().getPlayer(this.getCurrentPlayerNumber());
        boolean buildingHouseHotelSuccessful = false;

        if (currentPlayer.getCurrentBlock().isLandBlock()) {
            LandBlock currentBlock = (LandBlock) currentPlayer.getCurrentBlock();

            if (currentBlock.isHousePurchaseAllowed()) {
                buildingHouseHotelSuccessful = currentBlock.purchaseHouse();
            } else if (currentBlock.isApartmentPurchaseAllowed()) {
                buildingHouseHotelSuccessful = currentBlock.purchaseApartment();
            } else {
                System.out.println("Max number of houses and apartments met");
                System.out.println("\tOn Block: " + currentBlock.toString());
                System.out.println("\tFor Player: " + currentPlayer.toString());
                System.out.println("Hey, you can't buy anymore buildingy thinkamaginkibonkerses, now can you?");
            }
        }

        this.boardDisplayData.buildHousesHotels(buildingHouseHotelSuccessful);
        this.boardDisplayData.noMandatoryActionsFromPlayer();
        this.refreshGameInstructions();
    }

    public boolean isCurrentPlayerAllowedToPickChanceCommunityCardOnCurentBlock() {
        return this.boardDisplayData.isCurrentPlayerAllowedToPickChanceCommunityCardOnCurentBlock();
    }

    public void pickChanceCommunityCard() {
        MonopolyGameEngine.getInstance()
                .playerOnChanceBlock(MonopolyGameEngine.getInstance().getPlayer(this.getCurrentPlayerNumber()));
        this.boardDisplayData.pickChanceCommunityCard();
        this.boardDisplayData.noMandatoryActionsFromPlayer();
        this.refreshGameInstructions();
    }

    public boolean isCurrentPlayerAllowedToPerformGovernmentActionOnCurentBlock() {
        return this.boardDisplayData.isCurrentPlayerAllowedToPerformGovernmentActionOnCurentBlock();
    }

    public void performGovernmentAction() {
        // Player automatically moves to Jail if the Player lands on Go Directly To Jail
        Player player = MonopolyGameEngine.getInstance().getCurrentlyPlayingPlayer();
        Block currentBlock = player.getCurrentBlock();
        BlockTypeEnum currentBlockType = currentBlock.getBlockType();
        if (BlockTypeEnum.GO_TO_JAIL_BLOCK.equals(currentBlockType)) {
            MonopolyGameEngine.getInstance().playerGetsArrested(player);
            // TODO: Add the fancy message by Anumita to the Google sheets and as another
            // attribute to the Block
            // objects and replace the below call to getBlockDisplayDetails()
            this.boardDisplayData.displayGovernmentActionMessage("" + currentBlock.getBlockDisplayDetails(), false);
        } else if (BlockTypeEnum.CASH_PAYMENT_BLOCK.equals(currentBlockType)) {
            // TODO: Add the fancy message by Anumita to the Google sheets and as another
            // attribute to the Block
            // objects and replace the below call to getBlockDisplayDetails()
            MonopolyGameEngine.getInstance().playerOnCashBlock(player);
            this.boardDisplayData.displayGovernmentActionMessage("" + currentBlock.getBlockDisplayDetails(), false);
        } else if (BlockTypeEnum.JAIL_BLOCK.equals(currentBlockType)) {
            // TODO: Add the fancy message by Anumita to the Google sheets and as another
            // attribute to the Block
            // objects and replace the below call to getBlockDisplayDetails()
            this.boardDisplayData.displayGovernmentActionMessage("" + currentBlock.getBlockDisplayDetails(), false);
        } else if (BlockTypeEnum.LOTTERY_BLOCK.equals(currentBlockType)) {
            // TODO: Add the fancy message by Anumita to the Google sheets and as another
            // attribute to the Block
            // objects and replace the below call to getBlockDisplayDetails()
            String lotteryMessage = MonopolyGameEngine.getInstance().playerOnLotteryBlock(player);
            this.boardDisplayData.displayGovernmentActionMessage("" + lotteryMessage, true);
        } else if (BlockTypeEnum.GO_BLOCK.equals(currentBlockType)) {
            // TODO: Add the fancy message by Anumita to the Google sheets and as another
            // attribute to the Block
            // objects and replace the below call to getBlockDisplayDetails()
            this.boardDisplayData.displayGovernmentActionMessage("" + currentBlock.getBlockDisplayDetails(), true);
        } else {
            this.boardDisplayData.displayGovernmentActionMessage(
                    "The government doesn't really care so you don't have its attention", true);
        }
        this.boardDisplayData.noMandatoryActionsFromPlayer();
        this.refreshGameInstructions();
    }

    public String getCurrentBlockOwnerName() {
        Block currentBlock = getCurrentBlock();
        if (currentBlock.isBlockPurchasable()) {
            PropertyBlock currentPropertyBlock = (PropertyBlock) currentBlock;
            if (currentPropertyBlock.isOwned()) {
                return currentPropertyBlock.getOwner().getName();
            } else {
                return "Bank";
            }
        } else {
            return "Unownable block";
        }
    }

    private Block getCurrentBlock() {
        Block currentBlock = this.boardDisplayData.getCurrentPlayerBlock().getBlock();
        return currentBlock;
    }

    public boolean isCurrentBlockPurchasable() {
        return this.boardDisplayData.getCurrentPlayerBlock().getBlock().isBlockPurchasable();
    }

    public boolean isGameOver() {
        return MonopolyGameEngine.getInstance().isGameOver();
    }

    public int getGamePlayTurnNumber() {
        return this.getMonopolyBoardDisplayData().getGamePlayTurnNumber();
    }

    public void setGameWindow(GameWindow aGameWindow) {
        this.gameWindow = aGameWindow;
    }

    public void forceRepaint() {
        this.gameWindow.forceRepaint();
    }
}
