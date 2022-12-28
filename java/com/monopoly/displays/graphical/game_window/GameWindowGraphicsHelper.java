package com.monopoly.displays.graphical.game_window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import com.monopoly.displays.graphical.GraphicsScreenHelper;
import com.monopoly.displays.helper.DisplayHelper;

public class GameWindowGraphicsHelper
{
    private GameWindow gameWindow = null;

    public GameWindowGraphicsHelper(GameWindow aGameWindow)
    {
        this.gameWindow = aGameWindow;
        DisplayHelper.debugLog(this.toString());
    }

    public void displayScreenBorder(Graphics graphics)
    {
        int left = GraphicsScreenHelper.getScreenLeftMargin();
        int top = GraphicsScreenHelper.getScreenTopMargin();
        // Subtract pixels for the left+right border lines themselves
        int width = GraphicsScreenHelper.getScreenAvailableWidth() - (GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS * 2);
        // Subtract pixels for the top+bottom border lines themselves
        int height = GraphicsScreenHelper.getScreenAvailableHeight() - (GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS * 2);

        graphics.setColor(Color.RED);
        graphics.drawRect(left, top, width, height);
    }

    public void displayGameBorder(Graphics graphics)
    {
        // this.displayScreenBorder(graphics);

        int left = this.gameWindow.getGameLeftMargin();
        int top = this.gameWindow.getGameTopMargin();
        int width = this.gameWindow.getGameWidth();
        int height = this.gameWindow.getGameHeight();

        graphics.setColor(Color.BLUE);
        ((Graphics2D) graphics.create()).fill3DRect(left, top, width, height, true);
    }

    public void displayGameRightSideBorder(Graphics graphics)
    {
        int left = this.gameWindow.getGameRightSideLeftMargin() - GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS;
        int top = this.gameWindow.getGameRightSideTopMargin() - GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS;
        int width = this.gameWindow.getGameRightSideWidth() - (GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS * 2);
        int height = this.gameWindow.getGameRightSideHeight() - (GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS * 2);

        graphics.setColor(Color.MAGENTA);
        graphics.drawRect(left, top, width, height);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(left + GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS,
                top + GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS, width - GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS,
                height - GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS);
    }

    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();


        return stringBuilder.toString();
    }
}
