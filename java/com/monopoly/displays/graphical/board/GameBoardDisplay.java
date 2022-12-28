package com.monopoly.displays.graphical.board;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.monopoly.displays.adapter.GameEngineGraphicalDisplayAdapter;
import com.monopoly.displays.data.BlockDisplayData;
import com.monopoly.displays.data.BoardDisplayData;
import com.monopoly.displays.data.GameMessageData;
import com.monopoly.displays.graphical.GraphicsScreenHelper;
import com.monopoly.displays.graphical.board_block.BoardBlockDisplay;
import com.monopoly.displays.graphical.game_window.GameWindow;
import com.monopoly.displays.helper.DisplayHelper;
import com.monopoly.displays.helper.GeneralHelper;
import com.monopoly.gameplay.MonopolyGameEngine;

public class GameBoardDisplay {
    private static final String TITLE_FONT_NAME = "Lucida Calligraphy";
    private static final String DEFAULT_FONT_NAME = "TimesRoman";
    private static final int DEFAULT_FONT_SIZE = 12;
    private static final int TITLE_FONT_SIZE = 25;
    private static final int SPECIAL_FONT_SIZE = 18;
    private static final Color GAME_INSTRUCTIONS_COLOR = Color.BLUE;
    private static final Color GAME_ERROR_MESSAGE_COLOR = Color.RED;
    private static final Color GAME_HAPPY_MESSAGE_COLOR = Color.GREEN;
    private static final Color GAME_SAD_MESSAGE_COLOR = Color.MAGENTA;
    private static final int BOTTOM_MOST_CENTER_MESSAGE = 0;
    private static final Font TITLE_FONT = new Font(TITLE_FONT_NAME, Font.BOLD, TITLE_FONT_SIZE);
    private static final Font STATUS_FONT = new Font(DEFAULT_FONT_NAME, Font.BOLD, DEFAULT_FONT_SIZE);
    private static final Font SPECIAL_FONT = new Font(DEFAULT_FONT_NAME, Font.BOLD, SPECIAL_FONT_SIZE);

    private GameWindow gameWindow = null;
    private GameBoardGraphicsHelper gameBoardGraphicsHelper = null;
    private BoardDisplayData boardDisplayData = null;
    private boolean boardCoordinatesCalculated = false;
    private BoardBlockDisplay boardBlockDisplay = null;

    public GameBoardDisplay(GameWindow aGameWindow, BoardDisplayData aBoardDisplayData) {
        this.gameWindow = aGameWindow;
        this.boardDisplayData = aBoardDisplayData;
        this.calculateGameBlockCoordinates();
        this.boardBlockDisplay = new BoardBlockDisplay();
        this.gameBoardGraphicsHelper = new GameBoardGraphicsHelper(this.gameWindow, this, this.boardBlockDisplay);
    }

    public void forceRepaint() {
        this.gameWindow.forceRepaint();
    }

    public void calculateGameBlockCoordinates() {
        int block_X = this.gameWindow.getGameLeftSideLeftMargin();
        int block_Y = this.gameWindow.getGameLeftSideTopMargin();
        int block_Width = this.getCalculatedBlockWidth();
        int block_Height = this.getCalculatedBlockHeight();
        // DisplayHelper.debugLog("Block Border: " + block_X + "," + block_Y + " to " +
        // (block_X +
        // block_Width) + ","
        // + (block_Y + block_Height));

        for (int row = 1; row <= GameEngineGraphicalDisplayAdapter.NUM_ROWS_PER_BOARD; row++) {
            for (int column = 1; column <= GameEngineGraphicalDisplayAdapter.NUM_COLUMNS_PER_ROW; column++) {
                BlockDisplayData displayBlock = this.boardDisplayData.getDisplayBlock(row, column);

                displayBlock.setDisplayCoordinates_X(block_X);
                displayBlock.setDisplayCoordinates_Y(block_Y);
                displayBlock.setDisplayWidth(block_Width);
                displayBlock.setDisplayHeight(block_Height);

                // Move the coordinates to the next "block column" in the same row
                block_X = block_X + block_Width + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
                // Move the Border coordinates AFTER moving to "block column"
                this.gameWindow.setGameLeftSideWidth(block_X - (GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS * 2));
            }

            // Move the coordinates to the next "block row"
            block_Y = block_Y + block_Height + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
            // Move the Border coordinates AFTER moving to "block row"
            this.gameWindow.setGameLeftSideHeight(block_Y - (GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS * 2));

            // Reset the "block column" position to the "start" so that we can display the
            // next row
            block_X = this.gameWindow.getGameLeftMargin() + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
        }

        // This flag is needed to ensure that GameBoard.paintComponent() doesn't try to
        // draw anything
        // until these calculations are completed
        this.setBoardCoordinatesCalculated(true);

    }

    public void displayGameBoard(Graphics graphics) {
        this.gameBoardGraphicsHelper.displayBoardOutsideBorder(graphics);
        this.gameBoardGraphicsHelper.displayBoardInsideBorder(graphics);
        this.gameBoardGraphicsHelper.displayGameBlocks(this.boardDisplayData, graphics);
        this.gameBoardGraphicsHelper.displayGameGraph(this.boardDisplayData, graphics);

        MonopolyGameEngine.getInstance().playBlockSounds(this.boardDisplayData.getCurrentPlayerBlock().getBlock());

        this.displayGameMessages(graphics);
        this.displayCurrentTurnDetails(graphics);
    }

    private void displayGameMessages(Graphics graphics) {
        displayGameTitle(graphics);

        displayGamePlayTurns(graphics);
        displayGamePlayTime(graphics);
        displayCurrentTime(graphics);

        displayGameInstructionMessage(graphics);

        displayDiceRoll(graphics);

        displayGameExitMessage(graphics);
    }

    private void displayGameTitle(Graphics graphics) {
        graphics.setFont(TITLE_FONT);
        graphics.setColor(GAME_INSTRUCTIONS_COLOR);
        this.gameBoardGraphicsHelper.displayTopLeftText(GameEngineGraphicalDisplayAdapter.getGameTitle(), graphics);
    }

    private void displayGamePlayTurns(Graphics graphics) {
        graphics.setFont(STATUS_FONT);
        graphics.setColor(Color.BLACK);
        this.gameBoardGraphicsHelper.displayBottomCenterText(BOTTOM_MOST_CENTER_MESSAGE,
                this.boardDisplayData.getGameTurnDisplay(), graphics);
    }

    private void displayGamePlayTime(Graphics graphics) {
        graphics.setFont(STATUS_FONT);
        graphics.setColor(Color.BLACK);
        this.gameBoardGraphicsHelper.displayTopRightText(this.boardDisplayData.getGamePlayTimeDisplay(), graphics);
    }

    private void displayCurrentTime(Graphics graphics) {
        graphics.setFont(STATUS_FONT);
        graphics.setColor(Color.BLACK);
        this.gameBoardGraphicsHelper.displayBottomLeftText("Current Time: " + DisplayHelper.getCurrentTime(), graphics);
    }

    private void displayGameInstructionMessage(Graphics graphics) {
        // Message Number is used to control vertical height adjustment.
        // Special messages with bigger font requires different height adjustment
        float DEFAULT_SPECIAL_MESSAGE_VERTICAL_LOCATION = 1.5f;
        int verticalHeightFromBottomToUp = BOTTOM_MOST_CENTER_MESSAGE;
        int specialMessageCounter = 1;

        graphics.setFont(STATUS_FONT);
        graphics.setColor(Color.BLACK);

        for (GameMessageData gameMessage : this.boardDisplayData.getGameMessages()) {
            verticalHeightFromBottomToUp++;

            switch (gameMessage.getMessageType()) {
            case DUMMY_INSTRUCTIONS: {
                DisplayHelper.debugLog("Unexpected Dummy Message Type in " + this.getClass().getName()
                        + ".displayGameInstructionMessage()");
                System.exit(1);
                break;
            }
            case TURN_INSTRUCTIONS: {
                this.gameBoardGraphicsHelper.displayBottomCenterText(verticalHeightFromBottomToUp,
                        gameMessage.getMessage(), graphics, GAME_INSTRUCTIONS_COLOR, STATUS_FONT);
                break;
            }
            case TURN_ERROR_MESSAGE: {
                this.gameBoardGraphicsHelper.displayBottomCenterText(verticalHeightFromBottomToUp,
                        gameMessage.getMessage(), graphics, GAME_ERROR_MESSAGE_COLOR, STATUS_FONT);
                break;
            }
            case HAPPY_MESSAGE: {
                // Sample text for testing out the Animation
                // "This is a really long sentence to be displayed on the Monopoly Board! I hope
                // I can think of
                // really long meaningful message like this!"
                this.gameBoardGraphicsHelper.displayAnimatedBottomCenterText(
                        DEFAULT_SPECIAL_MESSAGE_VERTICAL_LOCATION * specialMessageCounter, gameMessage.getMessage(),
                        graphics, GAME_HAPPY_MESSAGE_COLOR, SPECIAL_FONT);
                // Add extra vertical spacing since the special message is displayed bigger
                verticalHeightFromBottomToUp++;
                specialMessageCounter++;
                break;
            }
            case SAD_MESSAGE: {
                this.gameBoardGraphicsHelper.displayAnimatedBottomCenterText(
                        DEFAULT_SPECIAL_MESSAGE_VERTICAL_LOCATION * specialMessageCounter, gameMessage.getMessage(),
                        graphics, GAME_SAD_MESSAGE_COLOR, SPECIAL_FONT);
                // Add extra vertical spacing since the special message is displayed bigger
                verticalHeightFromBottomToUp++;
                specialMessageCounter++;
                break;
            }
            default: {
                DisplayHelper.debugLog("Unknown Game Message Type in " + this.getClass().getName()
                        + ".displayGameInstructionMessage()");
                System.exit(1);
            }
            }
        }
    }

    public void displayDiceRoll(Graphics graphics) {
        // Display random pair of Dice Face Images while the "dice is rolling"
        if (this.boardDisplayData.isDiceRolling()) {
            // TODO: Find better Dice Roll Sound
            // new AudioHelper()
            // .playSound(GameSettings.SOUND_FILE_LOCATION_PREFIX +
            // GameSettings.DICE_THROW_SOUND_FILENAME);
            displayRandomDiceRoll(graphics);
        } else if (this.boardDisplayData.isDiceFinishedRolling()) {
            // Display correct pair of Dice Face Images based on Dice Roll #s after "dice
            // has finished rolling"
            int firstDieNumber = this.boardDisplayData.getDiceRoll_1();
            int secondDieNumber = this.boardDisplayData.getDiceRoll_2();
            displaySpecificDiceRoll(graphics, firstDieNumber, secondDieNumber);
        }
    }

    private void displayRandomDiceRoll(Graphics graphics) {
        int firstDieImageIndex = GeneralHelper.randomInt(0, GameEngineGraphicalDisplayAdapter.getMaxDieFaceValue());
        int secondDieImageIndex = GeneralHelper.randomInt(0, GameEngineGraphicalDisplayAdapter.getMaxDieFaceValue());

        int imageWidth = GameBoardGraphicsHelper.DICE_IMAGES[firstDieImageIndex].getWidth();

        displayDiceImages(graphics, firstDieImageIndex, secondDieImageIndex, imageWidth);
    }

    private void displaySpecificDiceRoll(Graphics graphics, int firstDieNumber, int secondDieNumber) {
        int firstDieImageIndex = firstDieNumber - 1;
        int secondDieImageIndex = secondDieNumber - 1;

        int imageWidth = GameBoardGraphicsHelper.DICE_IMAGES[firstDieImageIndex].getWidth();

        displayDiceImages(graphics, firstDieImageIndex, secondDieImageIndex, imageWidth);
    }

    private void displayDiceImages(Graphics graphics, int firstDieImageIndex, int secondDieImageIndex,
            int horizontalOffset) {
        // Display the first die image
        this.gameBoardGraphicsHelper.displayMidLeftImage(GameBoardGraphicsHelper.DICE_IMAGES[firstDieImageIndex], 0,
                graphics);

        // Display the second die image with the horizontal offset
        this.gameBoardGraphicsHelper.displayMidLeftImage(GameBoardGraphicsHelper.DICE_IMAGES[secondDieImageIndex],
                horizontalOffset, graphics);
    }

    private void displayGameExitMessage(Graphics graphics) {
        graphics.setFont(STATUS_FONT);
        graphics.setColor(Color.RED);
        this.gameBoardGraphicsHelper.displayBottomRightText(this.getExitKeyMessage(), graphics);
    }

    private void displayCurrentTurnDetails(Graphics graphics) {
        // TODO: Display Current Turn specific Details such as Current Block's Property
        // Card, or Chance
        // Card, etc.
    }

    private String getExitKeyMessage() {
        return "Press Escape to exit!";
    }

    public int getCalculatedBlockHeight() {
        return Math.max(
                this.gameWindow.getGameHeight() / (GameEngineGraphicalDisplayAdapter.NUM_COLUMNS_PER_ROW
                        * GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS),
                GraphicsScreenHelper.MIN_BLOCK_HEIGHT_in_PIXELS);
    }

    public int getCalculatedBlockWidth() {
        return Math.max(
                this.gameWindow.getGameWidth() / (GameEngineGraphicalDisplayAdapter.NUM_ROWS_PER_BOARD
                        * GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS),
                GraphicsScreenHelper.MIN_BLOCK_WIDTH_in_PIXELS);
    }

    public boolean isBoardCoordinatesCalculated() {
        return this.boardCoordinatesCalculated;
    }

    private void setBoardCoordinatesCalculated(boolean aBoardCoordinatesCalculated) {
        this.boardCoordinatesCalculated = aBoardCoordinatesCalculated;
    }
}
