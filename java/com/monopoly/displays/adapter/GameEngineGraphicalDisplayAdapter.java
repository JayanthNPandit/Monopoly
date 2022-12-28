package com.monopoly.displays.adapter;

import java.awt.Color;
import java.security.InvalidParameterException;

import com.monopoly.board.ColorEnum;
import com.monopoly.displays.data.BoardDisplayData;
import com.monopoly.game.MonopolyGameController;
import com.monopoly.gameplay.GameSettings;

public class GameEngineGraphicalDisplayAdapter {
    private static final Color DUMMY_COLOR = new Color(204, 204, 204);
    private static final Color BROWN = new Color(140, 102, 24);
    private static final Color LIGHT_BLUE = new Color(176, 230, 255);
    private static final Color PINK = new Color(240, 0, 230);
    private static final Color ORANGE = new Color(255, 153, 0);
    private static final Color RED = new Color(230, 0, 38);
    private static final Color YELLOW = new Color(255, 255, 0);
    private static final Color GREEN = new Color(0, 207, 55);
    private static final Color DARK_BLUE = new Color(0, 94, 255);

    // These values can not be changed without impacting game display
    public static final int NUM_ROWS_PER_BOARD = 11;
    public static final int NUM_COLUMNS_PER_ROW = 11;

    // These settings are kept here for common logical place to centeralize all Game
    // Settings that
    // affect display logic

    // These values should be kept in sync with the Game Engine; Game Engine
    // settings can be the master for these values
    public static final int GAME_START_TURN_NUMBER = 1;
    public static final int UNKNOWN_PLAYER = 0;

    // These values will impact game display but can be changed here as needed
    public static final int MAX_MESSAGE_WIDTH_BEFORE_SCROLLING = 100;
    public static final int DELAY_BETWEEN_MESSAGE_DISPLAY_ANIMATION_IN_MILLISECONDS = 100;
    public static final int NUMBER_OF_SECONDS_TO_DISPLAY_THE_FULL_MESSAGE = 5;
    public static final int DELAY_BETWEEN_DICE_ROLL_ANIMATION_IN_MILLISECONDS = 100;

    // These values should be configured in Game Settings ideally
    private static final int MAX_DIE_FACE_VALUE = 6;
    protected static final String[] DICE_FACE_IMAGES = initializeDieFaceImageFilenames();
    private static final int NUMBER_OF_TIMES_TO_ROLL_DICE = 25;

    // These attributes below are used to give access to these class for other
    // classes
    private static MonopolyGameController aMonopolyGameController = null;

    private GameEngineGraphicalDisplayAdapter() {
    }

    public static String getGameTitle() {
        return GameSettings.getGameTitle();
    }

    public static int getTotalNumberOfPlayers() {
        return GameSettings.getNumberOfPlayers();
    }

    public static int getNumberOfTimesToRollDice() {
        return NUMBER_OF_TIMES_TO_ROLL_DICE;
    }

    public static int getMaxDieFaceValue() {
        return MAX_DIE_FACE_VALUE;
    }

    public static String[] getDiceFaceImageFilenames() {
        return DICE_FACE_IMAGES;
    }

    private static String[] initializeDieFaceImageFilenames() {
        String[] diceFaceImages = new String[MAX_DIE_FACE_VALUE];

        // One image for Die Face 1
        diceFaceImages[0] = "./src/main/resources/Die_Face_01.jpg";

        // One image for Die Face 2
        diceFaceImages[1] = "./src/main/resources/Die_Face_02.jpg";

        // One image for Die Face 3
        diceFaceImages[2] = "./src/main/resources/Die_Face_03.jpg";

        // One image for Die Face 4
        diceFaceImages[3] = "./src/main/resources/Die_Face_04.jpg";

        // One image for Die Face 5
        diceFaceImages[4] = "./src/main/resources/Die_Face_05.jpg";

        // One image for Die Face 6
        diceFaceImages[5] = "./src/main/resources/Die_Face_06.jpg";

        return diceFaceImages;
    }

    public static Color convertToGraphicsColor(ColorEnum colorEnum) {
        Color color = null;
        switch (colorEnum) {
            case DUMMY_COLOR: {
                color = DUMMY_COLOR;
                break;
            }
            case BROWN: {
                color = BROWN;
                break;
            }
            case LIGHT_BLUE: {
                color = LIGHT_BLUE;
                break;
            }
            case PINK: {
                color = PINK;
                break;
            }
            case ORANGE: {
                color = ORANGE;
                break;
            }
            case RED: {
                color = RED;
                break;
            }
            case YELLOW: {
                color = YELLOW;
                break;
            }
            case GREEN: {
                color = GREEN;
                break;
            }

            case DARK_BLUE: {
                color = DARK_BLUE;
                break;
            }
            default: {
                throw new InvalidParameterException("Unknown Block Color: " + colorEnum.getColorName());
            }
        }
        return color;
    }

    public static MonopolyGameController getMonopolyGameController() {
        if (aMonopolyGameController == null) {
            aMonopolyGameController = MonopolyGameController.createInstance(new BoardDisplayData(
                    GameSettings.readMonopolyBlocksConfigFile(GameSettings.getMonopolyBlockConfigFilename())));
        } else {
            // Nothing happens
        }

        return aMonopolyGameController;
    }
}
