package com.monopoly.displays.graphical.board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Timer;
import com.monopoly.displays.graphical.game_window.GameWindow;
import com.monopoly.displays.helper.AudioHelper;
import com.monopoly.game.MonopolyGameController;
import com.monopoly.gameplay.GameSettings;
import com.monopoly.displays.adapter.GameEngineGraphicalDisplayAdapter;

public class DiceRollEventHandler extends AbstractAction {
    private static final long serialVersionUID = 1L;
    private GameWindow gameWindow = null;
    private MonopolyGameController monopolyGameController = null;

    public DiceRollEventHandler(GameWindow aGameWindow, MonopolyGameController aMonopolyGameController) {
        this.gameWindow = aGameWindow;
        this.monopolyGameController = aMonopolyGameController;
    }

    private ActionListener diceRoleTimerActionListener = new ActionListener() {
        private int numberOfTimesDiceRolled = 1;

        public void actionPerformed(ActionEvent actionEvent) {
            if (monopolyGameController.isDiceRolling()) {
                if (numberOfTimesDiceRolled >= GameEngineGraphicalDisplayAdapter.getNumberOfTimesToRollDice()) {
                    diceRollAnimationTimer.stop();
                    monopolyGameController.stopDiceRolling();
                    // Reset Dice Animation Count for the next Player's Dice Throw
                    numberOfTimesDiceRolled = 1;

                    // Only pick the ACTUAL Random Dice Values after the "dice stops rolling"
                    monopolyGameController.rollDice();
                    monopolyGameController.startPlayerTurn();
                } else {
                    numberOfTimesDiceRolled++;
                }
            } else {
                System.out.println("\t\t\tDice Roll Animation is trying to start before Dice Started Rolling!");
            }
        }
    };
    private Timer diceRollAnimationTimer = new Timer(
            GameEngineGraphicalDisplayAdapter.DELAY_BETWEEN_DICE_ROLL_ANIMATION_IN_MILLISECONDS,
            diceRoleTimerActionListener);

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.monopolyGameController.isReadyToStartTurn()) {
            this.monopolyGameController.startDiceRolling();
            diceRollAnimationTimer.start();
            this.monopolyGameController.displayTemporaryGameMessages("Player rolling the Dice!");
        } else {
            this.monopolyGameController.setErrorMessage("Can't Throw Dice in the middle of a Turn!");
        }
        gameWindow.repaint();
    }
}
