package com.monopoly.displays.graphical.board;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.monopoly.displays.adapter.GameEngineGraphicalDisplayAdapter;
import com.monopoly.displays.data.BlockDisplayData;
import com.monopoly.displays.data.BoardDisplayData;
import com.monopoly.displays.graphical.GraphicsScreenHelper;
import com.monopoly.displays.graphical.game_window.GameWindow;
import com.monopoly.displays.graphical.player.PlayerGraphicsHelper;
import com.monopoly.displays.helper.DisplayHelper;
import com.monopoly.gameplay.MonopolyGameEngine;

public class GameStatsDisplay {
    private static final String STATS_TITLE_FONT_NAME = "TimesRoman";
    private static final String STATS_TEXT_FONT_NAME = "TimesRoman";
    private static final int STATS_TITLE_FONT_SIZE = 20;
    private static final int STATS_TEXT_FONT_SIZE = 15;
    private static final int STATS_SMALL_TEXT_FONT_SIZE = 12;
    private static final int STATS_SPECIAL_FONT_SIZE = 10;
    private static final Font STATS_TITLE_FONT = new Font(STATS_TITLE_FONT_NAME, Font.BOLD, STATS_TITLE_FONT_SIZE);
    private static final Font STATS_TEXT_FONT = new Font(STATS_TEXT_FONT_NAME, Font.BOLD, STATS_TEXT_FONT_SIZE);
    private static final Font STATS_SMALL_TEXT_FONT = new Font(STATS_TEXT_FONT_NAME, Font.BOLD,
            STATS_SMALL_TEXT_FONT_SIZE);
    private static final Font STATS_SPECIAL_FONT = new Font(STATS_TEXT_FONT_NAME, Font.BOLD, STATS_SPECIAL_FONT_SIZE);
    private static final int ALL_PLAYERS_NETWORTH_CHART_HEIGHT = 100;

    private GameWindow gameWindow = null;
    private BoardDisplayData boardDisplayData = null;

    public GameStatsDisplay(GameWindow aGameWindow, BoardDisplayData aBoardDisplayData) {
        this.gameWindow = aGameWindow;
        this.boardDisplayData = aBoardDisplayData;
    }

    public int displayAllPlayersSummary(Graphics aGraphics) {
        // TODO: Implement Player Ranking Stats
        int allPlayersRankingBorderHeight = this.displayAllPlayersRankingBorder(aGraphics);
        this.displayAllPlayersRanking(aGraphics);

        int allPlayersNetWorthBorderHeight = this.displayAllPlayersNetWorthChartBorder(aGraphics,
                allPlayersRankingBorderHeight);
        this.displayAllPlayersNetWorthChart(aGraphics, allPlayersRankingBorderHeight);

        return allPlayersRankingBorderHeight + allPlayersNetWorthBorderHeight;
    }

    private int displayAllPlayersRankingBorder(Graphics aGraphics) {
        int left = this.gameWindow.getGameRightSideLeftMargin() + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
        int top = this.gameWindow.getGameRightSideTopMargin() + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
        int width = this.gameWindow.getGameRightSideWidth() - (GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS * 2);
        int height = (GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS * 4) + calculateHeightOfStatsTitle(aGraphics)
                + calculateHeightOfPlayerRankingText(aGraphics);

        aGraphics.setColor(Color.BLACK);
        aGraphics.drawRect(left, top, width, height);

        return height;
    }

    private int calculateHeightOfStatsTitle(Graphics aGraphics) {
        aGraphics.setColor(Color.BLACK);
        aGraphics.setFont(GameStatsDisplay.STATS_TITLE_FONT);

        return GraphicsScreenHelper.getMessageHeight("Player Rankings Title", aGraphics);
    }

    private int calculateHeightOfPlayerRankingText(Graphics aGraphics) {
        aGraphics.setColor(Color.BLACK);
        aGraphics.setFont(GameStatsDisplay.STATS_TEXT_FONT);

        return ((GraphicsScreenHelper.getMessageHeight("Player Ranking Detail Text", aGraphics) / 2)
                * (this.boardDisplayData.getNumberOfPlayers()));
    }

    private void displayAllPlayersRanking(Graphics aGraphics) {
        String message = "All Player Stats";

        int left = this.gameWindow.getGameRightSideLeftMargin() + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;

        int x = left + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
        int y = this.gameWindow.getGameRightSideTopMargin() + (GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS * 2)
                + (GraphicsScreenHelper.getMessageHeight(message, aGraphics) / 2);

        aGraphics.setColor(Color.BLACK);
        aGraphics.setFont(GameStatsDisplay.STATS_TITLE_FONT);
        aGraphics.drawString(message, x, y);

        aGraphics.setColor(Color.BLACK);
        aGraphics.setFont(GameStatsDisplay.STATS_TEXT_FONT);

        Map<Double, String> sortedPlayersByNetworth = new TreeMap<>(Collections.reverseOrder());
        for (int playerIndex = 1; playerIndex <= this.boardDisplayData.getNumberOfPlayers(); playerIndex++) {
            int networthAmount = this.boardDisplayData.getPlayerNetWorth(playerIndex);
            int cashAmount = this.boardDisplayData.getPlayerCash(playerIndex);
            String cashFormattedAmount = "$" + DisplayHelper.getFormattedAmount(cashAmount, 7);
            int playerPropertyCount = this.boardDisplayData.getPlayerPropertyCount(playerIndex);
            message = this.boardDisplayData.getPlayerName(playerIndex) + ": " + cashFormattedAmount + " with "
                    + playerPropertyCount + " Properties";

            message = message + " after " + this.boardDisplayData.getPlayerTurn(playerIndex) + " turns";

            // Small decimal added to make each key unique, needed when multiple players
            // have same networth
            double netWorthAsDecimal = (double) (networthAmount
                    + ((double) playerIndex / (double) this.boardDisplayData.getNumberOfPlayers()));
            sortedPlayersByNetworth.put(netWorthAsDecimal, message);
        }

        for (String sortedMessage : sortedPlayersByNetworth.values()) {
            y = y + (GraphicsScreenHelper.getMessageHeight(sortedMessage, aGraphics) / 2)
                    + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;

            aGraphics.drawString(sortedMessage, x, y);
        }
    }

    private int displayAllPlayersNetWorthChartBorder(Graphics aGraphics, int borderOffset) {
        int left = this.gameWindow.getGameRightSideLeftMargin() + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
        int top = this.gameWindow.getGameRightSideTopMargin() + borderOffset
                + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
        int width = this.gameWindow.getGameRightSideWidth() - (GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS * 2);
        int height = GameStatsDisplay.ALL_PLAYERS_NETWORTH_CHART_HEIGHT
                + (GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS * 2);

        aGraphics.setColor(Color.BLACK);
        aGraphics.drawRect(left, top, width, height);

        return height;
    }

    private void displayAllPlayersNetWorthChart(Graphics aGraphics, int borderOffset) {
        String message = "All Player's Net Worth";
        int messageHeight = GraphicsScreenHelper.getMessageHeight(message, aGraphics);

        int left = this.gameWindow.getGameRightSideLeftMargin() + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
        int chartWidth = this.gameWindow.getGameRightSideWidth() - (GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS * 2);
        int chartHeight = GameStatsDisplay.ALL_PLAYERS_NETWORTH_CHART_HEIGHT;

        int x = left + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
        int y = borderOffset + this.gameWindow.getGameRightSideTopMargin() + messageHeight;

        aGraphics.setColor(Color.BLACK);
        aGraphics.setFont(GameStatsDisplay.STATS_TITLE_FONT);

        aGraphics.drawString(message, x, y);

        Map<Integer, Integer> playerNetWorths = getPlayerNetWorth();
        int totalNetWorth = getTotalNetWorth(playerNetWorths);

        if (totalNetWorth > 0) {
            int barWidth = (chartWidth / this.boardDisplayData.getNumberOfPlayers())
                    - ((GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS / 3)
                            * this.boardDisplayData.getNumberOfPlayers());

            aGraphics.setColor(Color.BLACK);
            aGraphics.setFont(GameStatsDisplay.STATS_SPECIAL_FONT);

            for (int playerIndex = 1; playerIndex <= this.boardDisplayData.getNumberOfPlayers(); playerIndex++) {
                float percentOfChartHeight = playerNetWorths.get(playerIndex).floatValue() / ((float) totalNetWorth);

                // TODO: Draw a LINE GRAPH with x-axis as "Turn #" and y-axis as "Net Worth for
                // each Player"
                // Line Graph might be better picture than Bar Graph in this tiny space
                drawNetWorthBarForPlayer(aGraphics, messageHeight, chartHeight, x, y, barWidth, percentOfChartHeight,
                        playerIndex);

                // Move x-axis to the next player's position
                x = x + barWidth + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
            }
        }
    }

    private void drawNetWorthBarForPlayer(Graphics aGraphics, int messageHeight, int chartHeight, int x, int y,
            int barWidth, float percentOfChartHeight, int playerIndex) {
        String playerName = this.boardDisplayData.getPlayerName(playerIndex);
        int playerNameHeight = GraphicsScreenHelper.getMessageHeight(playerName, aGraphics);

        int maxBarHeight = chartHeight - playerNameHeight - messageHeight
                + (GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS * 2);
        int barHeight = (int) (maxBarHeight * percentOfChartHeight);

        int barMessageY = y + chartHeight - GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
        int barY = barMessageY - playerNameHeight + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;

        aGraphics.setColor(Color.BLACK);
        aGraphics.drawString(playerName, x, barMessageY);
        aGraphics.setColor(GameEngineGraphicalDisplayAdapter
                .convertToGraphicsColor(MonopolyGameEngine.getInstance().getPlayer(playerIndex).getPlayerColor()));
        aGraphics.fill3DRect(x, barY - barHeight, barWidth, barHeight, true);
    }

    private Map<Integer, Integer> getPlayerNetWorth() {
        Map<Integer, Integer> playerNetworths = new HashMap<>();

        int totalNetworth = 0;

        for (int playerIndex = 1; playerIndex <= this.boardDisplayData.getNumberOfPlayers(); playerIndex++) {
            playerNetworths.put(playerIndex, this.boardDisplayData.getPlayerNetWorth(playerIndex));
            totalNetworth = totalNetworth + playerNetworths.get(playerIndex);
        }

        return playerNetworths;
    }

    private int getTotalNetWorth(Map<Integer, Integer> playerNetworths) {
        int totalNetworth = 0;

        for (int playerIndex = 1; playerIndex <= this.boardDisplayData.getNumberOfPlayers(); playerIndex++) {
            totalNetworth = totalNetworth + playerNetworths.get(playerIndex);
        }

        return totalNetworth;
    }

    public void displayAllPlayerDetails(Graphics aGraphics, int borderOffset) {
        int playerDetailsBorderHeight = this.displayPlayerDetailsBorder(aGraphics, borderOffset);
        for (int playerIndex = 1; playerIndex <= this.boardDisplayData.getNumberOfPlayers(); playerIndex++) {
            int playerStatsHeight = this.displayPlayerStats(aGraphics, borderOffset, playerIndex);
            int playerCashHeight = this.displayPlayerCash(aGraphics, playerStatsHeight, playerIndex);
            int playerPropertiesHeight = this.displayPlayerProperties(aGraphics, playerCashHeight, playerIndex);
            borderOffset = playerPropertiesHeight;
        }
        this.displayPlayerNetWorthChart(aGraphics, borderOffset);
    }

    private int displayPlayerDetailsBorder(Graphics aGraphics, int borderOffset) {
        int left = this.gameWindow.getGameRightSideLeftMargin() + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
        int top = this.gameWindow.getGameRightSideTopMargin() + borderOffset
                + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
        int width = this.gameWindow.getGameRightSideWidth() - (GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS * 2);
        int height = this.gameWindow.getGameRightSideHeight() - borderOffset
                - (GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS * 2);

        aGraphics.setColor(Color.BLACK);
        aGraphics.drawRect(left, top, width, height);

        return height;
    }

    private int displayPlayerStats(Graphics aGraphics, int borderOffset, int playerNumber) {
        aGraphics.setColor(Color.BLACK);
        aGraphics.setFont(GameStatsDisplay.STATS_TITLE_FONT);

        String message = this.boardDisplayData.getPlayerName(playerNumber) + " Summary";
        int messageHeight = GraphicsScreenHelper.getMessageHeight(message, aGraphics);

        int left = this.gameWindow.getGameRightSideLeftMargin() + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;

        int x = left + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
        int y = borderOffset + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS + messageHeight;

        if (playerNumber == this.boardDisplayData.getCurrentPlayerNumber()) {
            PlayerGraphicsHelper.displayPlayerToken(playerNumber, x, y, aGraphics);
        }
        aGraphics.drawString(message, x + PlayerGraphicsHelper.MAX_PLAYER_TOKEN_WIDTH_in_PIXELS, y);

        y = y + (GraphicsScreenHelper.getMessageHeight(message, aGraphics) / 2)
                + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;

        /*
         * message = "Currently has $" + DisplayHelper.getFormattedAmount(
         * this.boardDisplayData.getPlayerCash(this.boardDisplayData.
         * getCurrentPlayerNumber()));
         */

        // TODO: Debate this unarguable topic later
        message = "Currently has net worth of $"
                + DisplayHelper.getFormattedAmount(this.boardDisplayData.getPlayerNetWorth(playerNumber));

        /*
         * List<BlockDisplayData> currentPlayerOwnedBlocks = this.boardDisplayData
         * .getPlayerOwnedBlocks(this.boardDisplayData.getCurrentPlayerNumber()); if
         * (currentPlayerOwnedBlocks.isEmpty() == false) { message = message + " and " +
         * currentPlayerOwnedBlocks.size() + " properties..."; } else { message =
         * message + " and no properties!"; }
         */

        aGraphics.setColor(Color.BLACK);
        aGraphics.setFont(GameStatsDisplay.STATS_TEXT_FONT);

        aGraphics.drawString(message, x, y);

        return y;
    }

    private int displayPlayerCash(Graphics aGraphics, int playerStatsHeight, int playerIndex) {
        aGraphics.setColor(Color.BLACK);
        aGraphics.setFont(GameStatsDisplay.STATS_SMALL_TEXT_FONT);

        String message = this.boardDisplayData.getPlayerName(playerIndex) + "'s cash is $"
                + DisplayHelper.getFormattedAmount(this.boardDisplayData.getPlayerCash(playerIndex));
        int messageHeight = GraphicsScreenHelper.getMessageHeight(message, aGraphics);

        int left = this.gameWindow.getGameRightSideLeftMargin() + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;

        int x = left + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;
        int y = playerStatsHeight + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS + messageHeight;

        aGraphics.drawString(message, x + PlayerGraphicsHelper.MAX_PLAYER_TOKEN_WIDTH_in_PIXELS, y);

        // y = y + (GraphicsScreenHelper.getMessageHeight(message, aGraphics) / 2)
        // + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;

        return y;
    }

    private int displayPlayerProperties(Graphics aGraphics, int borderOffset, int playerNumber) {
        aGraphics.setColor(Color.BLACK);
        aGraphics.setFont(GameStatsDisplay.STATS_SMALL_TEXT_FONT);

        int left = this.gameWindow.getGameRightSideLeftMargin() + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;

        int x = left + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS
                + (GraphicsScreenHelper.HORIZONTAL_SPACE_in_PIXELS * 4);
        int y = borderOffset + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;

        List<BlockDisplayData> currentPlayerOwnedBlocks = this.boardDisplayData.getPlayerOwnedBlocks(playerNumber);
        for (BlockDisplayData blockDisplayData : currentPlayerOwnedBlocks) {
            String message = getFormattedBlockDisplayName(blockDisplayData);

            y = y + (GraphicsScreenHelper.getMessageHeight(message, aGraphics) / 2)
                    + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS / 2;

            aGraphics.drawString(message, x, y);
        }

        return y;
    }

    private String getFormattedBlockDisplayName(BlockDisplayData blockDisplayData) {
        String blockTypeDisplay = null;

        switch (blockDisplayData.getBlockType()) {
            case LAND_BLOCK:
                blockTypeDisplay = blockDisplayData.getBlockColor().getColorName();
                break;
            case ROAD_BLOCK:
                blockTypeDisplay = blockDisplayData.getBlockType().getBlockTypeName();
                break;
            case UTILITY_BLOCK:
                blockTypeDisplay = blockDisplayData.getBlockType().getBlockTypeName();
                break;
            case CASH_PAYMENT_BLOCK:
                throw new IllegalStateException(
                        "This block type can not be owned; trying to display this in the Current Player's owned property list!");
            case CHANCE_BLOCK:
                throw new IllegalStateException(blockDisplayData.getBlockName() + ": "
                        + "This block type can not be owned; trying to display this in the Current Player's owned property list!");
            case COMMUNITY_BLOCK:
                throw new IllegalStateException(blockDisplayData.getBlockName() + ": "
                        + "This block type can not be owned; trying to display this in the Current Player's owned property list!");
            case DUMMY_BLOCK:
                throw new IllegalStateException(blockDisplayData.getBlockName() + ": "
                        + "This block type can not be owned; trying to display this in the Current Player's owned property list!");
            case GO_BLOCK:
                throw new IllegalStateException(blockDisplayData.getBlockName() + ": "
                        + "This block type can not be owned; trying to display this in the Current Player's owned property list!");
            case GO_TO_JAIL_BLOCK:
                throw new IllegalStateException(blockDisplayData.getBlockName() + ": "
                        + "This block type can not be owned; trying to display this in the Current Player's owned property list!");
            case JAIL_BLOCK:
                throw new IllegalStateException(blockDisplayData.getBlockName() + ": "
                        + "This block type can not be owned; trying to display this in the Current Player's owned property list!");
            case LOTTERY_BLOCK:
                throw new IllegalStateException(blockDisplayData.getBlockName() + ": "
                        + "This block type can not be owned; trying to display this in the Current Player's owned property list!");
            default:
                throw new IllegalStateException(blockDisplayData.getBlockName() + ": "
                        + "Unknown Block Type; trying to display this in the Current Player's owned property list!");
        }
        return blockDisplayData.getBlockTitle() + " (" + blockTypeDisplay + ")";
    }

    private void displayPlayerNetWorthChart(Graphics aGraphics, int borderOffset) {
        // TODO: To be implemented as a statistics display after the game because nobody
        // remebers how much money they had 20 turns back unless they were incredibly
        // rich then Anumita poor
    }
}
