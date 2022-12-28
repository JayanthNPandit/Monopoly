package com.monopoly.displays.graphical.board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import com.monopoly.displays.graphical.game_window.GameWindow;
import com.monopoly.game.MonopolyGameController;

public class GameBoardEventHandlers {
    private static final int KEY_FOR_DICE_ROLE_COMMAND = KeyEvent.VK_D;
    private static final int KEY_FOR_PURCHASE_PROPERTY_COMMAND = KeyEvent.VK_P;
    private static final int KEY_FOR_PAY_RENT_COMMAND = KeyEvent.VK_R;
    private static final int KEY_FOR_BUILD_HOUSES_HOTELS_COMMAND = KeyEvent.VK_B;
    private static final int KEY_FOR_CHANCE_COMMUNITY_CARD_COMMAND = KeyEvent.VK_C;
    private static final int KEY_FOR_GOVERNMENT_ACTION_COMMAND = KeyEvent.VK_G;
    private static final int KEY_FOR_NEXT_PLAYER_MOVE = KeyEvent.VK_N;
    private static final int KEY_FOR_EXIT_COMMAND = KeyEvent.VK_ESCAPE;

    private static final int DISPLAY_REFRESH_FREQUENCY_IN_MILLISECONDS = 500;

    private static final GameBoardEventHandlers singleInstance = new GameBoardEventHandlers();

    public static GameBoardEventHandlers getInstance() {
        return singleInstance;
    }

    private GameBoardEventHandlers() {
    }

    public void addEventHandlers(GameWindow aGameWindow, MonopolyGameController aMonopolyGameController) {
        InputMap inputMap = aGameWindow.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = aGameWindow.getActionMap();

        addEventHandlerForDiceRoll(inputMap, actionMap, aGameWindow, aMonopolyGameController);

        addEventHandlerForPurchaseProperty(inputMap, actionMap, aGameWindow, aMonopolyGameController);
        addEventHandlerForPayRent(inputMap, actionMap, aGameWindow, aMonopolyGameController);
        addEventHandlerForBuildHousesHotels(inputMap, actionMap, aGameWindow, aMonopolyGameController);
        addEventHandlerForChanceCommunityCard(inputMap, actionMap, aGameWindow, aMonopolyGameController);
        addEventHandlerForGovernmentAction(inputMap, actionMap, aGameWindow, aMonopolyGameController);

        addEventHandlerForNextPlayer(inputMap, actionMap, aGameWindow, aMonopolyGameController);

        addEventHandlerForExit(inputMap, actionMap, aGameWindow, aMonopolyGameController);

        Timer timer = addTimeBasedEventHandler(aGameWindow, aMonopolyGameController);
        timer.start();
    }

    private void addEventHandlerForDiceRoll(InputMap inputMap, ActionMap actionMap, GameWindow aGameWindow,
            MonopolyGameController aMonopolyGameController) {
        inputMap.put(KeyStroke.getKeyStroke(KEY_FOR_DICE_ROLE_COMMAND, 0), "diceRollEventHandler");
        actionMap.put("diceRollEventHandler",
                (Action) (new DiceRollEventHandler(aGameWindow, aMonopolyGameController)));
    }

    private void addEventHandlerForPurchaseProperty(InputMap inputMap, ActionMap actionMap, GameWindow aGameWindow,
            MonopolyGameController aMonopolyGameController) {
        inputMap.put(KeyStroke.getKeyStroke(KEY_FOR_PURCHASE_PROPERTY_COMMAND, 0), "purchasePropertyEventHandler");
        actionMap.put("purchasePropertyEventHandler",
                (Action) (new PurchasePropertyEventHandler(aGameWindow, aMonopolyGameController)));
    }

    private void addEventHandlerForPayRent(InputMap inputMap, ActionMap actionMap, GameWindow aGameWindow,
            MonopolyGameController aMonopolyGameController) {
        inputMap.put(KeyStroke.getKeyStroke(KEY_FOR_PAY_RENT_COMMAND, 0), "payRentEventHandler");
        actionMap.put("payRentEventHandler", (Action) (new PayRentEventHandler(aGameWindow, aMonopolyGameController)));
    }

    private void addEventHandlerForBuildHousesHotels(InputMap inputMap, ActionMap actionMap, GameWindow aGameWindow,
            MonopolyGameController aMonopolyGameController) {
        inputMap.put(KeyStroke.getKeyStroke(KEY_FOR_BUILD_HOUSES_HOTELS_COMMAND, 0), "buildHousesHotelsEventHandler");
        actionMap.put("buildHousesHotelsEventHandler",
                (Action) (new BuildHousesHotelsEventHandler(aGameWindow, aMonopolyGameController)));
    }

    private void addEventHandlerForChanceCommunityCard(InputMap inputMap, ActionMap actionMap, GameWindow aGameWindow,
            MonopolyGameController aMonopolyGameController) {
        inputMap.put(KeyStroke.getKeyStroke(KEY_FOR_CHANCE_COMMUNITY_CARD_COMMAND, 0),
                "chanceCommunityCardEventHandler");
        actionMap.put("chanceCommunityCardEventHandler",
                (Action) (new ChanceCommunityCardEventHandler(aGameWindow, aMonopolyGameController)));
    }

    private void addEventHandlerForGovernmentAction(InputMap inputMap, ActionMap actionMap, GameWindow aGameWindow,
            MonopolyGameController aMonopolyGameController) {
        inputMap.put(KeyStroke.getKeyStroke(KEY_FOR_GOVERNMENT_ACTION_COMMAND, 0), "governmentActionEventHandler");
        actionMap.put("governmentActionEventHandler",
                (Action) (new GovernmentActionEventHandler(aGameWindow, aMonopolyGameController)));
    }

    private void addEventHandlerForNextPlayer(InputMap inputMap, ActionMap actionMap, GameWindow aGameWindow,
            MonopolyGameController aMonopolyGameController) {
        inputMap.put(KeyStroke.getKeyStroke(KEY_FOR_NEXT_PLAYER_MOVE, 0), "nextPlayerEventHandler");
        actionMap.put("nextPlayerEventHandler",
                (Action) (new NextPlayerEventHandler(aGameWindow, aMonopolyGameController)));
    }

    private Timer addTimeBasedEventHandler(GameWindow aGameWindow, MonopolyGameController aMonopolyGameController) {
        Timer timer = new Timer(DISPLAY_REFRESH_FREQUENCY_IN_MILLISECONDS,
                (ActionListener) (new GameLoopEventHandler(aGameWindow, aMonopolyGameController)));
        return timer;
    }

    private void addEventHandlerForExit(InputMap inputMap, ActionMap actionMap, GameWindow aGameWindow,
            MonopolyGameController aMonopolyGameController) {
        inputMap.put(KeyStroke.getKeyStroke(KEY_FOR_EXIT_COMMAND, 0), "exitEventHandler");
        actionMap.put("exitEventHandler", (Action) (new ExitEventHandler(aGameWindow, aMonopolyGameController)));
    }

    public class GameLoopEventHandler implements ActionListener {
        private GameWindow gameWindow = null;
        private MonopolyGameController monopolyGameController = null;

        public GameLoopEventHandler(GameWindow aGameWindow, MonopolyGameController aMonopolyGameController) {
            this.gameWindow = aGameWindow;
            this.monopolyGameController = aMonopolyGameController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.monopolyGameController.continueGamePlay();
            gameWindow.repaint();
        }
    }

    public class PurchasePropertyEventHandler extends AbstractAction {
        private static final long serialVersionUID = 1L;
        private GameWindow gameWindow = null;
        private MonopolyGameController monopolyGameController = null;

        public PurchasePropertyEventHandler(GameWindow aGameWindow, MonopolyGameController aMonopolyGameController) {
            this.gameWindow = aGameWindow;
            this.monopolyGameController = aMonopolyGameController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Is first part true: " + this.monopolyGameController.isCurrentPlayerPlaying()
                    + " how 'bout the second part: "
                    + this.monopolyGameController.isCurrentPlayerAllowedToPurchaseCurentBlock());

            if (this.monopolyGameController.isCurrentPlayerPlaying()
                    && this.monopolyGameController.isCurrentPlayerAllowedToPurchaseCurentBlock()) {
                this.monopolyGameController.purchaseProperty();
            } else {
                if (this.monopolyGameController.isCurrentBlockPurchasable()) {
                    this.monopolyGameController.setErrorMessage("Too late, it is already owned by "
                            + this.monopolyGameController.getCurrentBlockOwnerName());
                } else {
                    this.monopolyGameController.setErrorMessage("You can't Purchase this Property!");
                }

            }
            gameWindow.repaint();
        }
    }

    public class PayRentEventHandler extends AbstractAction {
        private static final long serialVersionUID = 1L;
        private GameWindow gameWindow = null;
        private MonopolyGameController monopolyGameController = null;

        public PayRentEventHandler(GameWindow aGameWindow, MonopolyGameController aMonopolyGameController) {
            this.gameWindow = aGameWindow;
            this.monopolyGameController = aMonopolyGameController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (this.monopolyGameController.isCurrentPlayerPlaying()
                    && this.monopolyGameController.isCurrentPlayerRequiredToPayRentForCurrentBlock()) {
                this.monopolyGameController.payRent();
            } else {
                this.monopolyGameController.setErrorMessage("Why do you want to Pay Rent when you don't need to?");
            }
            gameWindow.repaint();
        }
    }

    public class BuildHousesHotelsEventHandler extends AbstractAction {
        private static final long serialVersionUID = 1L;
        private GameWindow gameWindow = null;
        private MonopolyGameController monopolyGameController = null;

        public BuildHousesHotelsEventHandler(GameWindow aGameWindow, MonopolyGameController aMonopolyGameController) {
            this.gameWindow = aGameWindow;
            this.monopolyGameController = aMonopolyGameController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (this.monopolyGameController.isCurrentPlayerPlaying()
                    && this.monopolyGameController.isCurrentPlayerAllowedToBuildHousesHotelsOnCurentBlock()) {
                this.monopolyGameController.buildHousesHotels();
            } else {
                this.monopolyGameController.setErrorMessage("Can't Build Houses/Hotels right now!");
            }
            gameWindow.repaint();
        }
    }

    public class ChanceCommunityCardEventHandler extends AbstractAction {
        private static final long serialVersionUID = 1L;
        private GameWindow gameWindow = null;
        private MonopolyGameController monopolyGameController = null;

        public ChanceCommunityCardEventHandler(GameWindow aGameWindow, MonopolyGameController aMonopolyGameController) {
            this.gameWindow = aGameWindow;
            this.monopolyGameController = aMonopolyGameController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (this.monopolyGameController.isCurrentPlayerPlaying()
                    && this.monopolyGameController.isCurrentPlayerAllowedToPickChanceCommunityCardOnCurentBlock()) {
                this.monopolyGameController.pickChanceCommunityCard();
            } else {
                this.monopolyGameController.setErrorMessage("Can't pick Chance/Community Card right now!");
            }
            gameWindow.repaint();
        }
    }

    public class GovernmentActionEventHandler extends AbstractAction {
        private static final long serialVersionUID = 1L;
        private GameWindow gameWindow = null;
        private MonopolyGameController monopolyGameController = null;

        public GovernmentActionEventHandler(GameWindow aGameWindow, MonopolyGameController aMonopolyGameController) {
            this.gameWindow = aGameWindow;
            this.monopolyGameController = aMonopolyGameController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (this.monopolyGameController.isCurrentPlayerPlaying()
                    && this.monopolyGameController.isCurrentPlayerAllowedToPerformGovernmentActionOnCurentBlock()) {
                this.monopolyGameController.performGovernmentAction();
            } else {
                this.monopolyGameController.setErrorMessage("You aren't in Government Block right now!");
            }
            gameWindow.repaint();
        }
    }

    public class NextPlayerEventHandler extends AbstractAction {
        private static final long serialVersionUID = 1L;
        private GameWindow gameWindow = null;
        private MonopolyGameController monopolyGameController = null;

        public NextPlayerEventHandler(GameWindow aGameWindow, MonopolyGameController aMonopolyGameController) {
            this.gameWindow = aGameWindow;
            this.monopolyGameController = aMonopolyGameController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (this.monopolyGameController.isReadyForNextPlayer()) {
                if (this.monopolyGameController.isGameOver() == false) {
                    this.monopolyGameController.nextPlayerTurn();
                } else {
                    this.monopolyGameController.setErrorMessage(
                            "Play the game by yourself you want to?  Gone bankrupt all other players have!");
                }
            } else {
                this.monopolyGameController.setErrorMessage("Current Player still has to play!");
            }
            gameWindow.repaint();
        }
    }

    public class ExitEventHandler extends AbstractAction {
        private static final long serialVersionUID = 1L;

        private MonopolyGameController monopolyGameController = null;

        public ExitEventHandler(GameWindow aGameWindow, MonopolyGameController aMonopolyGameController) {
            this.monopolyGameController = aMonopolyGameController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.monopolyGameController.saveAndExitGame();
        }
    }
}
