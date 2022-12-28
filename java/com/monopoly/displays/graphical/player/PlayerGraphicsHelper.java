package com.monopoly.displays.graphical.player;

import java.awt.Color;
import java.awt.Graphics;

import com.monopoly.displays.adapter.GameEngineGraphicalDisplayAdapter;
import com.monopoly.displays.helper.DisplayHelper;
import com.monopoly.gameplay.MonopolyGameEngine;

public class PlayerGraphicsHelper {
    public static final int MAX_PLAYER_TOKEN_WIDTH_in_PIXELS = 15;
    public static final int MAX_PLAYER_TOKEN_HEIGHT_in_PIXELS = 15;

    public static void displayPlayerToken(int playerNumber, int center_on_X, int center_on_Y, Graphics graphics) {
        Color playerColor = GameEngineGraphicalDisplayAdapter
                .convertToGraphicsColor(MonopolyGameEngine.getInstance().getPlayer(playerNumber).getPlayerColor());
        switch (playerNumber) {
            case 1: {
                displayPlayer_1_Token(center_on_X, center_on_Y, graphics, playerColor);
                break;
            }
            case 2: {
                displayPlayer_2_Token(center_on_X, center_on_Y, graphics, playerColor);
                break;
            }
            case 3: {
                displayPlayer_3_Token(center_on_X, center_on_Y, graphics, playerColor);
                break;
            }
            case 4: {
                displayPlayer_4_Token(center_on_X, center_on_Y, graphics, playerColor);
                break;
            }
            default: {
                DisplayHelper.debugLog("Unknown Player Number: " + playerNumber);
                break;
            }
        }
    }

    public static void displayPlayer_1_Token(int center_on_X, int center_on_Y, Graphics graphics, Color playerColor) {
        // For now, "player 1" will be displayed as a BLUE circle
        graphics.setColor(playerColor);
        int top_left_X = center_on_X - (MAX_PLAYER_TOKEN_WIDTH_in_PIXELS / 2);
        int top_left_Y = center_on_Y - (MAX_PLAYER_TOKEN_HEIGHT_in_PIXELS / 2);
        graphics.fillOval(top_left_X, top_left_Y, MAX_PLAYER_TOKEN_WIDTH_in_PIXELS, MAX_PLAYER_TOKEN_HEIGHT_in_PIXELS);
    }

    public static void displayPlayer_2_Token(int center_on_X, int center_on_Y, Graphics graphics, Color playerColor) {
        // For now, "player 2" will be displayed as a MAGENTA square
        graphics.setColor(playerColor);
        int top_left_X = center_on_X - (MAX_PLAYER_TOKEN_WIDTH_in_PIXELS / 2);
        int top_left_Y = center_on_Y - (MAX_PLAYER_TOKEN_HEIGHT_in_PIXELS / 2);
        graphics.fill3DRect(top_left_X, top_left_Y, MAX_PLAYER_TOKEN_WIDTH_in_PIXELS, MAX_PLAYER_TOKEN_HEIGHT_in_PIXELS,
                true);
    }

    public static void displayPlayer_3_Token(int center_on_X, int center_on_Y, Graphics graphics, Color playerColor) {
        int[] three_Xs_of_Triangle = new int[3];
        int[] three_Ys_of_Triangle = new int[3];

        // For now, "player 3" will be displayed as a CYAN triangle
        graphics.setColor(playerColor);

        // Draw the first line from bottom-left to top-middle
        three_Xs_of_Triangle[0] = center_on_X - (MAX_PLAYER_TOKEN_WIDTH_in_PIXELS / 2);
        three_Ys_of_Triangle[0] = center_on_Y + (MAX_PLAYER_TOKEN_HEIGHT_in_PIXELS / 2);
        three_Xs_of_Triangle[1] = center_on_X;
        three_Ys_of_Triangle[1] = center_on_Y - (MAX_PLAYER_TOKEN_HEIGHT_in_PIXELS / 2);
        graphics.drawLine(three_Xs_of_Triangle[0], three_Ys_of_Triangle[0], three_Xs_of_Triangle[1],
                three_Ys_of_Triangle[1]);

        // Draw the second line from top-middle to bottom-right
        three_Xs_of_Triangle[1] = center_on_X;
        three_Ys_of_Triangle[1] = center_on_Y - (MAX_PLAYER_TOKEN_HEIGHT_in_PIXELS / 2);
        three_Xs_of_Triangle[2] = center_on_X + (MAX_PLAYER_TOKEN_WIDTH_in_PIXELS / 2);
        three_Ys_of_Triangle[2] = center_on_Y + (MAX_PLAYER_TOKEN_HEIGHT_in_PIXELS / 2);
        graphics.drawLine(three_Xs_of_Triangle[1], three_Ys_of_Triangle[1], three_Xs_of_Triangle[2],
                three_Ys_of_Triangle[2]);

        // Draw the third line from bottom-right to bottom-left
        three_Xs_of_Triangle[2] = center_on_X + (MAX_PLAYER_TOKEN_WIDTH_in_PIXELS / 2);
        three_Ys_of_Triangle[2] = center_on_Y + (MAX_PLAYER_TOKEN_HEIGHT_in_PIXELS / 2);
        three_Xs_of_Triangle[0] = center_on_X - (MAX_PLAYER_TOKEN_WIDTH_in_PIXELS / 2);
        three_Ys_of_Triangle[0] = center_on_Y + (MAX_PLAYER_TOKEN_HEIGHT_in_PIXELS / 2);
        graphics.drawLine(three_Xs_of_Triangle[2], three_Ys_of_Triangle[2], three_Xs_of_Triangle[0],
                three_Ys_of_Triangle[0]);

        // Fill the Triangle with the current color
        graphics.fillPolygon(three_Xs_of_Triangle, three_Ys_of_Triangle, three_Xs_of_Triangle.length);
    }

    public static void displayPlayer_4_Token(int center_on_X, int center_on_Y, Graphics graphics, Color playerColor) {
        int[] four_Xs_of_Diamond = new int[4];
        int[] four_Ys_of_Diamnd = new int[4];

        // For now, "player 4" will be displayed as a GREEN diamond
        graphics.setColor(playerColor);

        // Draw the first line from left-middle to middle-top
        four_Xs_of_Diamond[0] = center_on_X - (MAX_PLAYER_TOKEN_WIDTH_in_PIXELS / 2);
        four_Ys_of_Diamnd[0] = center_on_Y;
        four_Xs_of_Diamond[1] = center_on_X;
        four_Ys_of_Diamnd[1] = center_on_Y - (MAX_PLAYER_TOKEN_HEIGHT_in_PIXELS / 2);
        graphics.drawLine(four_Xs_of_Diamond[0], four_Ys_of_Diamnd[0], four_Xs_of_Diamond[1], four_Ys_of_Diamnd[1]);

        // Draw the second line from middle-top to right-middle
        four_Xs_of_Diamond[1] = center_on_X;
        four_Ys_of_Diamnd[1] = center_on_Y - (MAX_PLAYER_TOKEN_HEIGHT_in_PIXELS / 2);
        four_Xs_of_Diamond[2] = center_on_X + (MAX_PLAYER_TOKEN_WIDTH_in_PIXELS / 2);
        four_Ys_of_Diamnd[2] = center_on_Y;
        graphics.drawLine(four_Xs_of_Diamond[1], four_Ys_of_Diamnd[1], four_Xs_of_Diamond[2], four_Ys_of_Diamnd[2]);

        // Draw the third line from right-middle to middle-bottom
        four_Xs_of_Diamond[2] = center_on_X + (MAX_PLAYER_TOKEN_WIDTH_in_PIXELS / 2);
        four_Ys_of_Diamnd[2] = center_on_Y;
        four_Xs_of_Diamond[3] = center_on_X;
        four_Ys_of_Diamnd[3] = center_on_Y + (MAX_PLAYER_TOKEN_HEIGHT_in_PIXELS / 2);
        graphics.drawLine(four_Xs_of_Diamond[2], four_Ys_of_Diamnd[2], four_Xs_of_Diamond[3], four_Ys_of_Diamnd[3]);

        // Draw the fourth line from middle-bottom to left-middle
        four_Xs_of_Diamond[3] = center_on_X;
        four_Ys_of_Diamnd[3] = center_on_Y + (MAX_PLAYER_TOKEN_HEIGHT_in_PIXELS / 2);
        four_Xs_of_Diamond[0] = center_on_X - (MAX_PLAYER_TOKEN_WIDTH_in_PIXELS / 2);
        four_Ys_of_Diamnd[0] = center_on_Y;
        graphics.drawLine(four_Xs_of_Diamond[3], four_Ys_of_Diamnd[3], four_Xs_of_Diamond[0], four_Ys_of_Diamnd[0]);

        // Fill the Diamond with the current color
        graphics.fillPolygon(four_Xs_of_Diamond, four_Ys_of_Diamnd, four_Xs_of_Diamond.length);
    }
}
