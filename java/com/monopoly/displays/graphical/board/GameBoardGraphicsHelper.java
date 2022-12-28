package com.monopoly.displays.graphical.board;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.monopoly.displays.adapter.GameEngineGraphicalDisplayAdapter;
import com.monopoly.displays.data.BoardDisplayData;
import com.monopoly.displays.graphical.GraphicsScreenHelper;
import com.monopoly.displays.graphical.board_block.BoardBlockDisplay;
import com.monopoly.displays.graphical.game_window.GameWindow;
import com.monopoly.displays.helper.DisplayHelper;
import com.monopoly.game.MonopolyGameController;
import com.monopoly.gameplay.MonopolyGameEngine;
import com.monopoly.player.Player;

public class GameBoardGraphicsHelper {
    protected static final BufferedImage[] DICE_IMAGES = loadDiceImages(
            GameEngineGraphicalDisplayAdapter.getDiceFaceImageFilenames());
    private static final Color DEFAULT_MESSAGE_COLOR = Color.BLACK;
    private static final Font DEFAULT_MESSAGE_FONT = null;
    private GameWindow gameWindow = null;
    private GameBoardDisplay gameBoardDisplay = null;
    private BoardBlockDisplay boardBlockDisplay = null;
    private MessageDisplayAnimationAction messageDisplayAnimationAction;

    public GameBoardGraphicsHelper(GameWindow aGameWindow, GameBoardDisplay aGameBoardDisplay,
            BoardBlockDisplay aBoardBlockDisplay) {
        this.gameWindow = aGameWindow;
        this.gameBoardDisplay = aGameBoardDisplay;
        this.boardBlockDisplay = aBoardBlockDisplay;
        this.messageDisplayAnimationAction = new MessageDisplayAnimationAction(this.gameWindow);
        DisplayHelper.debugLog(this.toString());
    }

    private static BufferedImage[] loadDiceImages(String[] diceFaceImageFilenames) {
        BufferedImage[] diceImages = new BufferedImage[diceFaceImageFilenames.length];

        for (int i = 0; i < diceFaceImageFilenames.length; i++) {
            try {
                diceImages[i] = ImageIO.read(new File(diceFaceImageFilenames[i]));
                System.out.println("Read Dice image(" + i + "): " + diceFaceImageFilenames[i] + " = "
                        + diceImages[i].getWidth() + "x" + diceImages[i].getHeight());
            } catch (IOException e) {
                System.out.println("Image File (" + diceFaceImageFilenames[i] + ") could not loaded!");
                System.exit(2);
            }
        }
        return diceImages;
    }

    public void displayBoardOutsideBorder(Graphics graphics) {
        int left = this.gameWindow.getGameLeftSideLeftMargin() - (GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS * 2);
        int top = this.gameWindow.getGameLeftSideTopMargin() - (GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS * 2);
        int width = this.gameWindow.getGameLeftSideWidth() - (GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS * 4);
        int height = this.gameWindow.getGameLeftSideHeight() - (GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS * 4);
        // DisplayHelper.debugLog("Board Outside Border: " + left + "," + top + " to " +
        // (left + width) +
        // "," + (top
        // + height));

        graphics.setColor(Color.ORANGE);
        ((Graphics2D) graphics.create()).draw3DRect(left, top, width, height, true);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(left + GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS,
                top + GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS, width - GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS,
                height - GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS);
    }

    public void displayBoardInsideBorder(Graphics graphics) {
        int left = this.getGameBoardInsideLeftMargin() + (GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS * 2);
        int top = this.getGameBoardInsideTopMargin() + (GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS * 2);
        int width = this.getGameBoardInsideWidth() - (GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS * 4);
        int height = this.getGameBoardInsideHeight() - (GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS * 4);

        graphics.setColor(Color.GREEN);
        graphics.drawRect(left, top, width, height);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(left + GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS,
                top + GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS, width - GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS,
                height - GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS);
    }

    public void displayGameBlocks(BoardDisplayData boardData, Graphics graphics) {
        for (int row = 1; row <= GameEngineGraphicalDisplayAdapter.NUM_ROWS_PER_BOARD; row++) {
            for (int column = 1; column <= GameEngineGraphicalDisplayAdapter.NUM_COLUMNS_PER_ROW; column++) {
                this.boardBlockDisplay.displayGameBlock(boardData, row, column, graphics);
            }
        }
    }

    public void displayMidLeftImage(Image image, int horizontalOffset, Graphics graphics) {
        Color bgcolor = Color.RED;
        ImageObserver observer = this.gameWindow;
        int x = getGameBoardInsideLeftMargin() + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS + horizontalOffset;
        int y = getGameBoardInsideTopMargin() + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS * 5;
        graphics.drawImage(image, x, y, bgcolor, observer);
    }

    public void displayTopLeftText(String message, Graphics graphics) {
        int x = getGameBoardInsideLeftMargin() + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
        int y = getGameBoardInsideTopMargin() + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS
                + (GraphicsScreenHelper.getMessageHeight(message, graphics) / 2);
        graphics.drawString(message, x, y);
    }

    public void displayTopRightText(String message, Graphics graphics) {
        int x = getGameBoardInsideLeftMargin() + getGameBoardInsideWidth()
                - GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS
                - GraphicsScreenHelper.getMessageWidth(message, graphics);
        int y = getGameBoardInsideTopMargin() + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS
                + (GraphicsScreenHelper.getMessageHeight(message, graphics) / 2);
        graphics.drawString(message, x, y);
    }

    public void displayBottomLeftText(String message, Graphics graphics) {
        int x = getGameBoardInsideLeftMargin() + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
        int y = getGameBoardInsideHeight() + this.gameBoardDisplay.getCalculatedBlockHeight()
                + (GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS * 2);
        graphics.drawString(message, x, y);
    }

    public void displayBottomCenterText(float verticalHeightAdjustment, String message, Graphics graphics) {
        displayBottomCenterText(verticalHeightAdjustment, message, graphics, DEFAULT_MESSAGE_COLOR,
                DEFAULT_MESSAGE_FONT, false);
    }

    public void displayBottomCenterText(float verticalHeightAdjustment, String message, Graphics graphics,
            Color textColor, Font textFont) {
        displayBottomCenterText(verticalHeightAdjustment, message, graphics, textColor, textFont, false);
    }

    public void displayAnimatedBottomCenterText(float verticalHeightAdjustment, String message, Graphics graphics,
            Color textColor, Font textFont) {
        displayBottomCenterText(verticalHeightAdjustment, message, graphics, textColor, textFont, true);

    }

    private void displayBottomCenterText(float verticalHeightAdjustment, String message, Graphics graphics,
            Color textColor, Font textFont, boolean animateMessage) {
        String truncatedMessageToDisplay = null;

        // When the animateMessage option is turned on scroll text if needed by
        // incrementally truncating
        // what gets displayed
        if (animateMessage) {
            if (this.gameWindow.isMessageDisplayAnimationAlreadyStarted() == false) {
                this.messageDisplayAnimationAction.startMessageDisplayAnimation(message);
            }
            if (this.gameWindow.getTruncatedMessageToAnimate() != null) {
                truncatedMessageToDisplay = this.gameWindow.getTruncatedMessageToAnimate();
            }
        } else {
            truncatedMessageToDisplay = message;
        }

        // Multi-thread timer-based event may call this method even before the above
        // code has finished
        // setting the truncatedMessageToDisplay value; so this null check is required
        if (truncatedMessageToDisplay != null) {
            // Font size should be set before calculating the x, y coordindates
            graphics.setColor(textColor);
            graphics.setFont(textFont);
            int x = getGameBoardInsideHorizontalCenter()
                    - (GraphicsScreenHelper.getMessageWidth(truncatedMessageToDisplay, graphics) / 2);
            int y = getGameBoardInsideHeight() + this.gameBoardDisplay.getCalculatedBlockHeight()
                    + (GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS * 2) - (int) (verticalHeightAdjustment
                            * GraphicsScreenHelper.getMessageHeight(truncatedMessageToDisplay, graphics));
            graphics.drawString(truncatedMessageToDisplay, x, y);
        }
    }

    public void displayBottomRightText(String message, Graphics graphics) {
        int x = getGameBoardInsideLeftMargin() + getGameBoardInsideWidth()
                - GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS
                - GraphicsScreenHelper.getMessageWidth(message, graphics);
        int y = getGameBoardInsideHeight() + this.gameBoardDisplay.getCalculatedBlockHeight()
                + (GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS * 2);
        graphics.drawString(message, x, y);
    }

    private int getGameBoardInsideLeftMargin() {
        return this.gameWindow.getGameLeftSideLeftMargin() + GraphicsScreenHelper.MIN_BLOCK_WIDTH_in_PIXELS
                + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
    }

    private int getGameBoardInsideTopMargin() {
        return this.gameWindow.getGameLeftSideTopMargin() + GraphicsScreenHelper.MIN_BLOCK_HEIGHT_in_PIXELS
                + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
    }

    private int getGameBoardInsideWidth() {
        return this.gameWindow.getGameLeftSideWidth() - (this.gameBoardDisplay.getCalculatedBlockWidth() * 2)
                - (GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS * 3);
    }

    private int getGameBoardInsideHeight() {
        return this.gameWindow.getGameLeftSideHeight() - (this.gameBoardDisplay.getCalculatedBlockHeight() * 2)
                - (GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS * 3);
    }

    private int getGameBoardInsideHorizontalCenter() {
        return getGameBoardInsideLeftMargin() + ((getGameBoardInsideWidth()) / 2);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("getGameBoardInsideLeftMargin() = " + getGameBoardInsideLeftMargin() + "\n");
        stringBuilder.append("getGameBoardInsideTopMargin() = " + getGameBoardInsideTopMargin() + "\n");
        stringBuilder.append("getGameBoardInsideWidth() = " + getGameBoardInsideWidth() + "\n");
        stringBuilder.append("getGameBoardInsideHeight() = " + getGameBoardInsideHeight() + "\n");

        return stringBuilder.toString();
    }

    public void displayGameGraph(BoardDisplayData boardDisplayData, Graphics graphics) {
        this.displayGraphOuterBox(boardDisplayData, graphics);
        this.displayGraphInnerBox(boardDisplayData, graphics);
        this.displayGraphXAxisLines(boardDisplayData, graphics);
        this.displayGraphYAxisLines(boardDisplayData, graphics);
    }

    private void displayGraphOuterBox(BoardDisplayData boardDisplayData, Graphics graphics) {
        int left = getOuterGraphBoxLeftSide(boardDisplayData);
        int top = getOuterGraphBoxTopSide(boardDisplayData);
        int width = getOuterGraphBoxWidth(boardDisplayData);
        int height = getOuterGraphBoxHeight(boardDisplayData);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(left, top, width, height);
    }

    private int getOuterGraphBoxLeftSide(BoardDisplayData boardDisplayData) {
        return this.gameWindow.getGameLeftMargin() + (boardDisplayData.getDisplayBlock(5, 5).getDisplayWidth() * 3)
                - (GraphicsScreenHelper.GRAPH_Y_AXIS_MARKER_WIDTH * 2);
    }

    private int getOuterGraphBoxTopSide(BoardDisplayData boardDisplayData) {
        return this.gameWindow.getGameTopMargin() + (boardDisplayData.getDisplayBlock(5, 5).getDisplayHeight() * 2);
    }

    private int getOuterGraphBoxWidth(BoardDisplayData boardDisplayData) {
        return boardDisplayData.getDisplayBlock(5, 5).getDisplayWidth() * 7
                + (GraphicsScreenHelper.GRAPH_Y_AXIS_MARKER_WIDTH * 3);
    }

    private int getOuterGraphBoxHeight(BoardDisplayData boardDisplayData) {
        return boardDisplayData.getDisplayBlock(5, 5).getDisplayHeight() * 6;
    }

    private void displayGraphInnerBox(BoardDisplayData boardDisplayData, Graphics graphics) {
        int left = getInnerGraphBoxLeftSide(boardDisplayData);
        int top = getOuterGraphBoxTopSide(boardDisplayData);
        int width = getInnerGraphBoxWidth(boardDisplayData);
        int height = getInnerGraphBoxHeight(boardDisplayData);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(left, top, width, height);
    }

    private int getInnerGraphBoxLowerLeftCornerXCoordinate(BoardDisplayData boardDisplayData) {
        return getOuterGraphBoxTopSide(boardDisplayData) + getInnerGraphBoxHeight(boardDisplayData);
    }

    private int getInnerGraphBoxLeftSide(BoardDisplayData boardDisplayData) {
        return this.gameWindow.getGameLeftMargin() + (boardDisplayData.getDisplayBlock(5, 5).getDisplayWidth() * 3)
                + GraphicsScreenHelper.GRAPH_Y_AXIS_WIDTH;
    }

    private int getInnerGraphBoxWidth(BoardDisplayData boardDisplayData) {
        return getOuterGraphBoxWidth(boardDisplayData) - (GraphicsScreenHelper.GRAPH_Y_AXIS_WIDTH * 1)
                - (GraphicsScreenHelper.GRAPH_Y_AXIS_MARKER_WIDTH * 2);
    }

    private int getInnerGraphBoxHeight(BoardDisplayData boardDisplayData) {
        return getOuterGraphBoxHeight(boardDisplayData) - (GraphicsScreenHelper.GRAPH_X_AXIS_HEIGHT * 1);
    }

    private void displayGraphXAxisLines(BoardDisplayData boardDisplayData, Graphics graphics) {
        // Calculates number of rounds completed by dividing number of total turns,
        // where 4 players will have 4 turns, by the number of players to calculate the
        // number of rounds completed
        int numXAxisMarkers = ((int) Math
                .ceil(boardDisplayData.getGamePlayTurnNumber() / boardDisplayData.getNumberOfPlayers())) + 1;

        // Atleast draw MIN number of X-axis markers AFTER the zero-position on the
        // X-axis
        if (numXAxisMarkers < GraphicsScreenHelper.GRAPH_MIN_NUMBER_OF_X_AXIS_MARKERS_TO_DISPLAY) {
            numXAxisMarkers = GraphicsScreenHelper.GRAPH_MIN_NUMBER_OF_X_AXIS_MARKERS_TO_DISPLAY;
        }

        int innerBoxLeftSide = getInnerGraphBoxLeftSide(boardDisplayData);
        int innerBoxRightSide = innerBoxLeftSide + getInnerGraphBoxWidth(boardDisplayData);
        int graphAreaWidth = innerBoxRightSide - innerBoxLeftSide;

        int xAxisMarkerHorziontalGap = (int) Math.ceil(graphAreaWidth / (numXAxisMarkers - 1)) + 1;

        // The gap between each X-Axis marker should be at least certain pixels wide
        if (xAxisMarkerHorziontalGap < GraphicsScreenHelper.GRAPH_X_AXIS_MARKER_MIN_HORIZONTAL_GAP) {
            xAxisMarkerHorziontalGap = GraphicsScreenHelper.GRAPH_X_AXIS_MARKER_MIN_HORIZONTAL_GAP;
        }

        // These are the graph's (0,0) position
        int x1 = getInnerGraphBoxLeftSide(boardDisplayData);
        int y1 = getInnerGraphBoxLowerLeftCornerXCoordinate(boardDisplayData);
        int x2 = x1;
        int y2 = y1;

        // Figure out how many "X-Axis markers" can be displayed by calculating the X1
        // graphical coordinates until it goes beyond the graphAreaWidth
        // Since the X-Axis won't fit all the rounds in a clean way, this logic figures
        // out where the next "0-mark" should be such that only the 20 or so most recent
        // rounds are displayed where 20 is based on numXAxisMarkers
        int markerIndex = 0;
        for (markerIndex = 0; markerIndex < numXAxisMarkers; markerIndex++) {
            x1 += xAxisMarkerHorziontalGap;
            if (x1 > (innerBoxLeftSide + graphAreaWidth)) {
                break;
            }
        }
        x1 = getInnerGraphBoxLeftSide(boardDisplayData);
        int startingMarkerIndexBasedOnWhatFitsOnGraph = numXAxisMarkers - markerIndex - 2;

        // Draw the X-Axis markers for each X-Axis position from 1 to numXAxisMarkers
        graphics.setColor(Color.BLUE);
        for (markerIndex = startingMarkerIndexBasedOnWhatFitsOnGraph; markerIndex < numXAxisMarkers; markerIndex++) {
            String xAxisLabel = Integer.toString(markerIndex);
            graphics.drawLine(x1, y1 - GraphicsScreenHelper.GRAPH_X_AXIS_MARKER_HEIGHT, x2,
                    y2 + GraphicsScreenHelper.GRAPH_X_AXIS_MARKER_HEIGHT);

            // Don't display the 0-label and only display the label every 5 markers
            boolean xAxisLabelNeeded = false;
            if (((markerIndex > 0) && ((markerIndex % 5) == 0))) {
                xAxisLabelNeeded = true;
            }
            if ((markerIndex <= GraphicsScreenHelper.GRAPH_MIN_NUMBER_OF_X_AXIS_MARKERS_TO_DISPLAY)
                    && (numXAxisMarkers <= GraphicsScreenHelper.GRAPH_MIN_NUMBER_OF_X_AXIS_MARKERS_TO_DISPLAY)) {
                xAxisLabelNeeded = true;
            }
            if (xAxisLabelNeeded) {
                graphics.drawString(xAxisLabel, x1 - (GraphicsScreenHelper.getMessageWidth(xAxisLabel, graphics) / 2),
                        y1 + GraphicsScreenHelper.GRAPH_X_AXIS_LABEL_VERTICAL_GAP
                                + (GraphicsScreenHelper.getMessageHeight(xAxisLabel, graphics) / 2));
            } else {
                // Don't draw the X-Axis label for 0 position
            }
            // Move the X-Axis to the next Marker position
            x1 += xAxisMarkerHorziontalGap;
            x2 = x1;

            if (x1 > (innerBoxLeftSide + graphAreaWidth)) {
                break;
            }
        }
    }

    private void displayGraphYAxisLines(BoardDisplayData boardDisplayData, Graphics graphics) {
        int innerBoxHeight = getInnerGraphBoxHeight(boardDisplayData);

        int numYAxisMarkers = (int) Math
                .ceil((double) innerBoxHeight / (double) GraphicsScreenHelper.GRAPH_Y_AXIS_MARKER_MIN_VERTICAL_GAP);

        // Atleast draw MIN number of Y-axis markers AFTER the zero-position on the
        // Y-axis
        if (numYAxisMarkers < GraphicsScreenHelper.GRAPH_MIN_NUMBER_OF_Y_AXIS_MARKERS_TO_DISPLAY) {
            numYAxisMarkers = GraphicsScreenHelper.GRAPH_MIN_NUMBER_OF_Y_AXIS_MARKERS_TO_DISPLAY;
        }

        // These are the graph's (0,0) position
        int x1 = getInnerGraphBoxLeftSide(boardDisplayData);
        int y1 = getInnerGraphBoxLowerLeftCornerXCoordinate(boardDisplayData);
        int x2 = x1;
        int y2 = y1;

        int minNetWorth = Math.min(0, calculateMinNetWorth(boardDisplayData));
        int currentLabelNetWorth = minNetWorth;
        // Calculate the next Y-Axis Label value
        currentLabelNetWorth = currentLabelNetWorth + GraphicsScreenHelper.GRAPH_Y_AXIS_LABEL_DOLLAR_AMOUNT_INCREMENT;
        int maxNetWorth = Math.max(1500, calculateMaxNetWorth(boardDisplayData));

        String yAxisLabel = "$" + DisplayHelper.getFormattedAmount(currentLabelNetWorth);

        // Draw the Y-Axis markers for each Y-Axis position from 1 to numYAxisMarkers
        graphics.setColor(Color.BLUE);
        for (int markerIndex = 1; markerIndex < numYAxisMarkers; markerIndex++) {
            graphics.drawLine(x1 - GraphicsScreenHelper.GRAPH_Y_AXIS_MARKER_WIDTH, y1,
                    x2 + GraphicsScreenHelper.GRAPH_Y_AXIS_MARKER_WIDTH, y2);

            // Don't display the 0-label and only display the label every 5 markers
            boolean yAxisLabelNeeded = false;
            if (((markerIndex > 0) && ((markerIndex % 2) == 0))) {
                yAxisLabelNeeded = true;
            }

            if (yAxisLabelNeeded) {
                yAxisLabel = "$" + DisplayHelper.getFormattedAmount(currentLabelNetWorth);
                graphics.drawString(yAxisLabel,
                        x1 - GraphicsScreenHelper.GRAPH_Y_AXIS_MARKER_WIDTH
                                - (GraphicsScreenHelper.getMessageWidth(yAxisLabel, graphics)
                                        + GraphicsScreenHelper.GRAPH_Y_AXIS_LABEL_HORIZONTAL_GAP),
                        y1 - (GraphicsScreenHelper.getMessageHeight(yAxisLabel, graphics) / 2));
            } else {
                // Don't draw the Y-Axis label for 0 position
            }

            // Move the Y-Axis to the next Marker position
            y1 = y1 - GraphicsScreenHelper.GRAPH_Y_AXIS_MARKER_MIN_VERTICAL_GAP;
            y2 = y1;
            // Calculate the next Y-Axis Label value
            currentLabelNetWorth = currentLabelNetWorth
                    + GraphicsScreenHelper.GRAPH_Y_AXIS_LABEL_DOLLAR_AMOUNT_INCREMENT;
        }
    }

    private int calculateMinNetWorth(BoardDisplayData boardDisplayData) {
        int minNetWorth = Integer.MAX_VALUE;
        int totalNumberOfPlayers = boardDisplayData.getNumberOfPlayers();
        int currentRoundNumber = MonopolyGameController.getInstance().getGamePlayTurnNumber() / totalNumberOfPlayers;

        for (int playerNumber = 1; playerNumber <= totalNumberOfPlayers; playerNumber++) {
            Player currentPlayer = MonopolyGameEngine.getInstance().getPlayer(playerNumber);
            for (int turnNumber = 1; turnNumber <= currentRoundNumber; turnNumber++) {
                minNetWorth = Math.min(minNetWorth, currentPlayer.getNetWorthByRound(turnNumber));
            }
        }

        return minNetWorth;
    }

    private int calculateMaxNetWorth(BoardDisplayData boardDisplayData) {
        int maxNetWorth = Integer.MIN_VALUE;
        int totalNumberOfPlayers = boardDisplayData.getNumberOfPlayers();
        int currentRoundNumber = MonopolyGameController.getInstance().getGamePlayTurnNumber() / totalNumberOfPlayers;

        for (int playerNumber = 1; playerNumber <= totalNumberOfPlayers; playerNumber++) {
            Player currentPlayer = MonopolyGameEngine.getInstance().getPlayer(playerNumber);
            for (int turnNumber = 1; turnNumber <= currentRoundNumber; turnNumber++) {
                maxNetWorth = Math.max(maxNetWorth, currentPlayer.getNetWorthByRound(turnNumber));
            }
        }

        return maxNetWorth;
    }
}
