package com.monopoly.displays.graphical.board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import com.monopoly.displays.graphical.game_window.GameWindow;
import com.monopoly.displays.adapter.GameEngineGraphicalDisplayAdapter;

public class MessageDisplayAnimationAction {
    private int NUMBER_MILLISECONDS_in_SECONDS = 1000;
    private GameWindow gameWindow = null;
    private String messageToAnimate = null;
    private Timer msgAnimationTimer = null;
    private boolean startedFullMessageDisplay = false;

    public MessageDisplayAnimationAction(GameWindow aGameWindow) {
        this.gameWindow = aGameWindow;
    }

    private ActionListener msgAnimationTimerActionListener = new ActionListener() {
        private int startingCharacterOfMessage = 0;
        private int numberOfMessageCharactersToDisplay = 1;

        public void actionPerformed(ActionEvent actionEvent) {
            if (gameWindow.isMessageDisplayToBeAnimated()) {
                if (numberOfMessageCharactersToDisplay >= messageToAnimate.length() + 1) {
                    if (startedFullMessageDisplay == false) {
                        configureTimerForFullMessageDisplay();
                    } else {
                        resetTimerAndMessageDisplay();
                    }
                } else {
                    // This calculation allows the left-side of the message to be truncated if
                    // needed to fit within the
                    // maximum scrolling size
                    if (numberOfMessageCharactersToDisplay > GameEngineGraphicalDisplayAdapter.MAX_MESSAGE_WIDTH_BEFORE_SCROLLING) {
                        startingCharacterOfMessage = numberOfMessageCharactersToDisplay
                                - GameEngineGraphicalDisplayAdapter.MAX_MESSAGE_WIDTH_BEFORE_SCROLLING;

                        // Reset the starting position to zero if it goes to negative
                        if (startingCharacterOfMessage < 0) {
                            startingCharacterOfMessage = 0;
                        }
                    }

                    // Reset the number of characters to display if it goes before the starting
                    // character
                    if (numberOfMessageCharactersToDisplay < startingCharacterOfMessage) {
                        startingCharacterOfMessage = 0;
                        numberOfMessageCharactersToDisplay = startingCharacterOfMessage + 1;
                    }

                    String truncatedMessageToAnimate = messageToAnimate.substring(startingCharacterOfMessage,
                            numberOfMessageCharactersToDisplay);
                    gameWindow.setTruncatedMessageToAnimate(truncatedMessageToAnimate);
                    numberOfMessageCharactersToDisplay++;
                }
            } else {
                System.out.println("\t\t\tMessage Animation is trying to start when not expected!");
            }
        }

        private void configureTimerForFullMessageDisplay() {
            int fullMessageDisplayDelay = GameEngineGraphicalDisplayAdapter.NUMBER_OF_SECONDS_TO_DISPLAY_THE_FULL_MESSAGE
                    * NUMBER_MILLISECONDS_in_SECONDS;
            msgAnimationTimer.setDelay(fullMessageDisplayDelay);
            msgAnimationTimer.setInitialDelay(fullMessageDisplayDelay);
            msgAnimationTimer.restart();
            startedFullMessageDisplay = true;
        }

        private void resetTimerAndMessageDisplay() {
            msgAnimationTimer.stop();
            msgAnimationTimer = null;
            gameWindow.stopMessageDisplayAnimation();
            // Reset Message Index to start from the beginning
            numberOfMessageCharactersToDisplay = 1;
            startedFullMessageDisplay = false;
        }
    };

    public void startMessageDisplayAnimation(String aMessage) {
        this.messageToAnimate = aMessage;
        this.gameWindow.startMessageDisplayAnimation();
        this.msgAnimationTimer = new Timer(
                GameEngineGraphicalDisplayAdapter.DELAY_BETWEEN_MESSAGE_DISPLAY_ANIMATION_IN_MILLISECONDS,
                msgAnimationTimerActionListener);
        this.msgAnimationTimer.start();
    }
}
