package com.monopoly.displays.graphical.board_block;

import java.awt.Graphics;
import com.monopoly.displays.data.BlockDisplayData;
import com.monopoly.displays.data.BoardDisplayData;
import com.monopoly.player.Player;

public class BoardBlockDisplay
{
    private BoardBlockGraphicsHelper boardBlockGraphicsHelper = null;

    public BoardBlockDisplay()
    {
        this.boardBlockGraphicsHelper = new BoardBlockGraphicsHelper();
    }

    public void displayGameBlock(BoardDisplayData boardData, int row, int column, Graphics graphics)
    {
        this.boardBlockGraphicsHelper.displayGameBlock(boardData, boardData.getDisplayBlock(row, column), graphics);
        this.displayPlayerTokens(boardData, row, column, graphics);
    }

    private void displayPlayerTokens(BoardDisplayData boardData, int row, int column, Graphics graphics)
    {
        for (int playerIndex = 1; playerIndex <= boardData.getNumberOfPlayers(); playerIndex++)
        {
            BlockDisplayData playerBlock = boardData.getPlayerBlock(playerIndex);

            // System.out.println("playerIndex = " + playerIndex + " on playerBlock = " +
            // playerBlock.getBlockName());

            // System.out.println("playersOnBlock size: " +
            // playerBlock.getBlock().playersOnBlock.size());
            for (Player player : playerBlock.getBlock().playersOnBlock)
            {
                System.out.println("Player " + player.getName() + " is on block " + player.getCurrentBlock().getBlockName());
            }
            if (true)
            {// (playerBlock.isPlayerInThisBlock(playerIndex)) {
             // System.out.println("Current player block: " + playerBlock.getBlockName() + "at " + row + ", " +
             // column);
                this.boardBlockGraphicsHelper.displayPlayerToken(playerIndex, playerBlock, graphics);
            }
        }
        // System.out.println();
    }
}
