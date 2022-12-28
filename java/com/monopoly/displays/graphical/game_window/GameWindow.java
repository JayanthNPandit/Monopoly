package com.monopoly.displays.graphical.game_window;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.monopoly.displays.adapter.GameEngineGraphicalDisplayAdapter;
import com.monopoly.displays.data.BoardDisplayData;
import com.monopoly.displays.graphical.GraphicsScreenHelper;
import com.monopoly.displays.graphical.board.GameBoardDisplay;
import com.monopoly.displays.graphical.board.GameBoardEventHandlers;
import com.monopoly.displays.graphical.board.GameStatsDisplay;
import com.monopoly.displays.helper.DisplayHelper;
import com.monopoly.game.MonopolyGameController;

public class GameWindow extends JPanel {
    private static final long serialVersionUID = 1L;

    private JFrame gameFrame = null;

    private int calculatedLeftSideWidth;
    private int calculatedLeftSideHeight;

    private GameWindowGraphicsHelper gameWindowGraphicsHelper = null;
    private BoardDisplayData boardDisplayData = null;
    private GameBoardDisplay gameBoardDisplay = null;
    private GameStatsDisplay gameStatsDisplay = null;
    private boolean animateMessageDisplay = false;
    private String truncatedMessageToAnimate = null;

    public GameWindow(MonopolyGameController aMonopolyGameController) {
        this.boardDisplayData = aMonopolyGameController.getMonopolyBoardDisplayData();
        GameBoardEventHandlers.getInstance().addEventHandlers(this, aMonopolyGameController);
        aMonopolyGameController.setGameWindow(this);
    }

    public void startDisplay() {
        this.gameFrame = GraphicsScreenHelper.initializeScreen();
        if (gameFrame != null) {
            gameFrame.setTitle(GameEngineGraphicalDisplayAdapter.getGameTitle());
            gameFrame.add(this);
            gameFrame.pack();
            gameFrame.setVisible(true);

            DisplayHelper.debugLog(GraphicsScreenHelper.toDisplayString());
            DisplayHelper.debugLog(this.toString());

            this.gameWindowGraphicsHelper = new GameWindowGraphicsHelper(this);
            this.gameBoardDisplay = new GameBoardDisplay(this, this.boardDisplayData);
            this.gameStatsDisplay = new GameStatsDisplay(this, this.boardDisplayData);
        } else {
            DisplayHelper.infoLog(String.format(
                    "A minimum of %dx%d pixel screen display is needed for this game board!",
                    GraphicsScreenHelper.MIN_SCREEN_WIDTH_in_PIXELS, GraphicsScreenHelper.MIN_SCREEN_HEIGHT_in_PIXELS));
            System.exit(1);
        }
    }

    public void forceRepaint() {
        this.gameFrame.getContentPane().repaint();
    }

    @Override
    protected void paintComponent(Graphics aGraphics) {
        super.paintComponent(aGraphics);

        if (this.gameBoardDisplay.isBoardCoordinatesCalculated()) {
            this.displayGameWindow(aGraphics);

            this.displayGameLeftSide(aGraphics);

            this.displayGameRightSide(aGraphics);
        }
    }

    private void displayGameWindow(Graphics aGraphics) {
        this.gameWindowGraphicsHelper.displayGameBorder(aGraphics);
    }

    private void displayGameLeftSide(Graphics aGraphics) {
        this.gameBoardDisplay.displayGameBoard(aGraphics);
    }

    private void displayGameRightSide(Graphics aGraphics) {
        this.gameWindowGraphicsHelper.displayGameRightSideBorder(aGraphics);
        int allPlayersSummarySectionHeight = this.gameStatsDisplay.displayAllPlayersSummary(aGraphics);
        this.gameStatsDisplay.displayAllPlayerDetails(aGraphics, allPlayersSummarySectionHeight);
    }

    public int getGameLeftMargin() {
        return GraphicsScreenHelper.getScreenLeftMargin() + GraphicsScreenHelper.LEFT_RIGHT_MARGINs_in_PIXELS;
    }

    public int getGameTopMargin() {
        return GraphicsScreenHelper.getScreenTopMargin() + GraphicsScreenHelper.TOP_BOTTOM_MARGINs_in_PIXELS;
    }

    public int getGameWidth() {
        return GraphicsScreenHelper.getScreenAvailableWidth() - (GraphicsScreenHelper.LEFT_RIGHT_MARGINs_in_PIXELS * 2);
    }

    public int getGameHeight() {
        return GraphicsScreenHelper.getScreenAvailableHeight()
                - (GraphicsScreenHelper.TOP_BOTTOM_MARGINs_in_PIXELS * 2);
    }

    public int getGameRightSideLeftMargin() {
        return this.getGameLeftSideLeftMargin() + this.getGameLeftSideWidth()
                + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
    }

    public int getGameRightSideTopMargin() {
        return this.getGameTopMargin() + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
    }

    public int getGameRightSideWidth() {
        return getGameWidth() - getGameRightSideLeftMargin();
    }

    public int getGameRightSideHeight() {
        return getGameHeight() - getGameRightSideTopMargin();
    }

    public int getGameLeftSideLeftMargin() {
        return this.getGameLeftMargin() + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
    }

    public int getGameLeftSideTopMargin() {
        return this.getGameTopMargin() + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS
                - (GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS * 2);
    }

    public int getGameLeftSideWidth() {
        return this.calculatedLeftSideWidth;
    }

    public void setGameLeftSideWidth(int aCalculatedLeftSideWidth) {
        this.calculatedLeftSideWidth = aCalculatedLeftSideWidth;
    }

    public int getGameLeftSideHeight() {
        return this.calculatedLeftSideHeight;
    }

    public void setGameLeftSideHeight(int aCalculatedLeftSideHeight) {
        this.calculatedLeftSideHeight = aCalculatedLeftSideHeight;
    }

    public boolean isMessageDisplayToBeAnimated() {
        return this.animateMessageDisplay;
    }

    public boolean isMessageDisplayAnimationAlreadyStarted() {
        return this.animateMessageDisplay;
    }

    public void startMessageDisplayAnimation() {
        this.animateMessageDisplay = true;
    }

    public void stopMessageDisplayAnimation() {
        this.animateMessageDisplay = false;
        this.truncatedMessageToAnimate = null;
    }

    public String getTruncatedMessageToAnimate() {
        return truncatedMessageToAnimate;
    }

    public void setTruncatedMessageToAnimate(String truncatedMessageToAnimate) {
        this.truncatedMessageToAnimate = truncatedMessageToAnimate;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("getGameLeftMargin() = " + getGameLeftMargin() + "\n");
        stringBuilder.append("getGameTopMargin() = " + getGameTopMargin() + "\n");
        stringBuilder.append("getGameWidth() = " + getGameWidth() + "\n");
        stringBuilder.append("getGameHeight() = " + getGameHeight() + "\n");

        stringBuilder.append("getGameLeftSideLeftMargin() = " + getGameLeftSideLeftMargin() + "\n");
        stringBuilder.append("getGameLeftSideTopMargin() = " + getGameLeftSideTopMargin() + "\n");
        stringBuilder.append("getGameLeftSideWidth() = " + getGameLeftSideWidth() + "\n");
        stringBuilder.append("getGameLeftSideHeight() = " + getGameLeftSideHeight() + "\n");

        stringBuilder.append("getGameRightSideLeftMargin() = " + getGameRightSideLeftMargin() + "\n");
        stringBuilder.append("getGameRightSideTopMargin() = " + getGameRightSideTopMargin() + "\n");
        stringBuilder.append("getGameRightSideWidth() = " + getGameRightSideWidth() + "\n");
        stringBuilder.append("getGameRightSideHeight() = " + getGameRightSideHeight() + "\n");

        return stringBuilder.toString();
    }
}
