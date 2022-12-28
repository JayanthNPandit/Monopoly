package com.monopoly.displays;

import javax.swing.SwingUtilities;

import com.monopoly.displays.adapter.GameEngineGraphicalDisplayAdapter;
import com.monopoly.displays.graphical.game_window.GameWindow;
import com.monopoly.displays.helper.DisplayHelper;

public class MonopolyGameGraphicsDisplay implements Runnable
{
    @Override
    public void run()
    {
        DisplayHelper.infoLog("Monopoly Game Started Running...");
        // Use the random generated board data
        GameWindow gameWindow = new GameWindow(GameEngineGraphicalDisplayAdapter.getMonopolyGameController());
        gameWindow.startDisplay();
    }

    public static void main(String[] args)
    {
        MonopolyGameGraphicsDisplay monopolyGame = new MonopolyGameGraphicsDisplay();

        SwingUtilities.invokeLater(monopolyGame);
    }
}
