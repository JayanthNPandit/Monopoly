package com.monopoly.displays.graphical.board_block;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import com.monopoly.displays.adapter.GameEngineGraphicalDisplayAdapter;
import com.monopoly.displays.data.BlockDisplayData;
import com.monopoly.displays.data.BoardDisplayData;
import com.monopoly.displays.graphical.GraphicsScreenHelper;
import com.monopoly.displays.graphical.player.PlayerGraphicsHelper;
import com.monopoly.displays.helper.DisplayHelper;

public class BoardBlockGraphicsHelper {
    private static final float LAND_COLOR_HEIGHT_IN_PERCENT = 0.2f; // 20%
    private PlayerGraphicsHelper playerGraphicsHelper = null;

    public BoardBlockGraphicsHelper() {
        this.playerGraphicsHelper = new PlayerGraphicsHelper();
    }

    public void displayGameBlock(BoardDisplayData boardData, BlockDisplayData displayBlock, Graphics graphics) {
        int block_X = displayBlock.getDisplayCoordinates_X();
        int block_Y = displayBlock.getDisplayCoordinates_Y();
        int block_Width = displayBlock.getDisplayWidth();
        int block_Height = displayBlock.getDisplayHeight();

        switch (displayBlock.getBlockType()) {
        case DUMMY_BLOCK: {
            // Ignore this type of Block
            break;
        }
        case LAND_BLOCK: {
            displayLandBlock(boardData, displayBlock, graphics, block_X, block_Y, block_Width, block_Height);
            break;
        }
        case ROAD_BLOCK: {
            displayRoadUtilityBlock(boardData, displayBlock, graphics, block_X, block_Y, block_Width, block_Height);
            break;
        }
        case UTILITY_BLOCK: {
            displayRoadUtilityBlock(boardData, displayBlock, graphics, block_X, block_Y, block_Width, block_Height);
            break;
        }
        case CHANCE_BLOCK: {
            displayChanceCommunityBlock(boardData, displayBlock, graphics, block_X, block_Y, block_Width, block_Height);
            break;
        }
        case COMMUNITY_BLOCK: {
            displayChanceCommunityBlock(boardData, displayBlock, graphics, block_X, block_Y, block_Width, block_Height);
            break;
        }
        case LOTTERY_BLOCK:
        case JAIL_BLOCK:
        case GO_TO_JAIL_BLOCK:
        case CASH_PAYMENT_BLOCK:
        case GO_BLOCK: {
            displayGovernmentBlock(boardData, displayBlock, graphics, block_X, block_Y, block_Width, block_Height);
            break;
        }
        default: {
            DisplayHelper.debugLog("Unknown Type of Game Block in " + this.getClass().getName());
            System.exit(1);
        }
        }
    }

    private void displayLandBlock(BoardDisplayData boardData, BlockDisplayData displayBlock, Graphics graphics,
            int block_X, int block_Y, int block_Width, int block_Height) {
        displayBlockBorder(graphics, block_X, block_Y, block_Width, block_Height);

        String blockTitle = displayBlock.getBlockTitle();
        if ((displayBlock.getNumberOfHouses() > 0) || (displayBlock.getNumberOfApartments() > 0)) {
            blockTitle += " (" + displayBlock.getNumberOfHouses() + ", " + displayBlock.getNumberOfApartments() + ")";
        }

        displayBlockColor(graphics, block_X, block_Y, block_Width, block_Height,
                GameEngineGraphicalDisplayAdapter.convertToGraphicsColor(displayBlock.getBlockColor()), blockTitle);
        displayBlockTitle(graphics, block_X, block_Y, block_Width, block_Height, blockTitle);

        displayBlockDetails(graphics, block_X, block_Y, block_Width, block_Height,
                displayBlock.getBlockDetailDisplay());
    }

    private void displayRoadUtilityBlock(BoardDisplayData boardData, BlockDisplayData displayBlock, Graphics graphics,
            int block_X, int block_Y, int block_Width, int block_Height) {
        displayBlockBorder(graphics, block_X, block_Y, block_Width, block_Height);

        String blockTitle = displayBlock.getBlockTitle();
        switch (displayBlock.getBlockType()) {
        case ROAD_BLOCK: {
            blockTitle = blockTitle + "(R)";
            break;
        }
        case UTILITY_BLOCK: {
            blockTitle = blockTitle + "(U)";
            break;
        }
        default: {
            DisplayHelper.debugLog(
                    "Unhandled Type of Game Block in " + this.getClass().getName() + ".displayRoadUtilityBlock()");
        }
        }
        displayBlockColor(graphics, block_X, block_Y, block_Width, block_Height,
                GameEngineGraphicalDisplayAdapter.convertToGraphicsColor(displayBlock.getBlockColor()), blockTitle);
        displayBlockTitle(graphics, block_X, block_Y, block_Width, block_Height, blockTitle);

        displayBlockDetails(graphics, block_X, block_Y, block_Width, block_Height,
                displayBlock.getBlockDetailDisplay());
    }

    private void displayChanceCommunityBlock(BoardDisplayData boardData, BlockDisplayData displayBlock,
            Graphics graphics, int block_X, int block_Y, int block_Width, int block_Height) {
        displayBlockBorder(graphics, block_X, block_Y, block_Width, block_Height);

        String blockTitle = displayBlock.getBlockTitle();
        switch (displayBlock.getBlockType()) {
        case CHANCE_BLOCK: {
            blockTitle = blockTitle + "(C)";
            break;
        }
        case COMMUNITY_BLOCK: {
            blockTitle = blockTitle + "(M)";
            break;
        }
        default: {
            DisplayHelper.debugLog(
                    "Unhandled Type of Game Block in " + this.getClass().getName() + ".displayChanceCommunityBlock()");
        }
        }
        displayBlockColor(graphics, block_X, block_Y, block_Width, block_Height,
                GameEngineGraphicalDisplayAdapter.convertToGraphicsColor(displayBlock.getBlockColor()), blockTitle);
        displayBlockTitle(graphics, block_X, block_Y, block_Width, block_Height, blockTitle);

        displayBlockDetails(graphics, block_X, block_Y, block_Width, block_Height,
                displayBlock.getBlockDetailDisplay());
    }

    private void displayGovernmentBlock(BoardDisplayData boardData, BlockDisplayData displayBlock, Graphics graphics,
            int block_X, int block_Y, int block_Width, int block_Height) {
        displayBlockBorder(graphics, block_X, block_Y, block_Width, block_Height);

        String blockTitle = displayBlock.getBlockTitle();

        displayBlockColor(graphics, block_X, block_Y, block_Width, block_Height,
                GameEngineGraphicalDisplayAdapter.convertToGraphicsColor(displayBlock.getBlockColor()), blockTitle);
        displayBlockTitle(graphics, block_X, block_Y, block_Width, block_Height, blockTitle);

        displayBlockDetails(graphics, block_X, block_Y, block_Width, block_Height,
                displayBlock.getBlockDetailDisplay());
    }

    private void displayBlockBorder(Graphics graphics, int block_X, int block_Y, int block_Width, int block_Height) {
        graphics.setColor(Color.BLACK);
        graphics.drawRect(block_X, block_Y, block_Width, block_Height);

        int inner_X = block_X + GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS;
        int inner_Y = block_Y + GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS;
        int inner_Width = block_Width - (GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS * 2);
        int inner_Height = block_Height - (GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS * 2);

        graphics.setColor(Color.WHITE);
        graphics.fillRect(inner_X, inner_Y, inner_Width, inner_Height);
    }

    private void displayBlockTitle(Graphics graphics, int block_X, int block_Y, int block_Width, int block_Height,
            String blockTitle) {
        graphics.setColor(Color.BLACK);
        Font currentFont = graphics.getFont();
        graphics.setFont(new Font(currentFont.getFontName(), Font.BOLD, currentFont.getSize()));

        int blockTitleWidth = GraphicsScreenHelper.getMessageWidth(blockTitle, graphics);

        int current_X = block_X + ((block_Width - blockTitleWidth) / 2);
        int current_Y = block_Y + (GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS * 2);

        graphics.drawString(blockTitle, current_X, current_Y);
    }

    private void displayBlockColor(Graphics graphics, int block_X, int block_Y, int block_Width, int block_Height,
            Color blockColor, String blockTitle) {
        int blockTitleHeight = GraphicsScreenHelper.getMessageHeight(blockTitle, graphics);
        int current_X = block_X + GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS;
        int current_Y = block_Y + GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS;
        int current_Width = block_Width - (GraphicsScreenHelper.BORDER_WIDTH_in_PIXELS * 2);
        int current_Height = Math.max((int) (block_Height * LAND_COLOR_HEIGHT_IN_PERCENT), blockTitleHeight);

        graphics.setColor(blockColor);
        graphics.fillRect(current_X, current_Y, current_Width, current_Height);
    }

    private void displayBlockDetails(Graphics graphics, int block_X, int block_Y, int block_Width, int block_Height,
            String blockDetails) {
        int blockMessageWidth = GraphicsScreenHelper.getMessageWidth(blockDetails, graphics);
        int current_X = block_X + ((block_Width - blockMessageWidth) / 2);
        int current_Y = block_Y + block_Height - GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;

        graphics.setColor(Color.BLACK);
        graphics.drawString(blockDetails, current_X, current_Y);
    }

    public void displayPlayerToken(int playerNumber, BlockDisplayData playerBlock, Graphics graphics) {
        int block_Height = playerBlock.getDisplayHeight();
        int block_X = playerBlock.getDisplayCoordinates_X();
        int block_center_Y = playerBlock.getDisplayCoordinates_Y() + (block_Height / 2);
        int token_center_X = block_X;
        int token_center_Y = block_center_Y;
        int space_for_token = PlayerGraphicsHelper.MAX_PLAYER_TOKEN_WIDTH_in_PIXELS
                + GraphicsScreenHelper.SPACING_MARGINs_in_PIXELS;

        switch (playerNumber) {
        case 1: {
            // For "player 1" move the token to the left-most position in the block
            token_center_X = block_X + (playerNumber * space_for_token);
            token_center_Y = block_center_Y;
            break;
        }
        case 2: {
            // For "player 2" move the token to the 2nd left-most position in the block
            token_center_X = block_X + (playerNumber * space_for_token);
            token_center_Y = block_center_Y;
            break;
        }
        case 3: {
            // For "player 3" move the token to the last-but-one position in the block
            token_center_X = block_X + (playerNumber * space_for_token);
            token_center_Y = block_center_Y;
            break;
        }
        case 4: {
            // For "player 4" move the token to the right-most position in the block
            token_center_X = block_X + (playerNumber * space_for_token);
            token_center_Y = block_center_Y;
            break;
        }
        default: {
            DisplayHelper.debugLog("Unknown Player Number: " + playerNumber);
            break;
        }
        }

        this.playerGraphicsHelper.displayPlayerToken(playerNumber, token_center_X, token_center_Y, graphics);
    }
}
