package com.monopoly.board;

import java.util.ArrayList;

import com.monopoly.gameplay.Dice;
import com.monopoly.gameplay.GameSettings;
import com.monopoly.player.Player;

public class Board {
    public static final int GO_BLOCK_INDEX = 1;
    public static final int JAIL_BLOCK_INDEX = 11;
    private ArrayList<Block> blocks;
    private ArrayList<SpecialCard> chanceCards;
    private ArrayList<SpecialCard> communityCards;

    public Board(ArrayList<Player> currentPlayers) {
        initializeBlocks();
        initializeSpecialCards();
        setPlayersOnGoBlock(currentPlayers);
    }

    private void initializeBlocks() {
        this.blocks = GameSettings.readMonopolyBlocksConfigFile(GameSettings.getMonopolyBlockConfigFilename());
    }

    public ArrayList<Block> getBlocks() {
        return this.blocks;
    }

    public Block getBlockByIndex(int index) {
        return this.getBlocks().get(index);
    }

    private void initializeSpecialCards() {
        ArrayList<SpecialCard> specialCards = GameSettings
                .readSpecialCardsConfigFile(GameSettings.getMonopolySpecialCardsConfigFilename());
        this.chanceCards = new ArrayList<SpecialCard>();
        this.communityCards = new ArrayList<SpecialCard>();

        for (SpecialCard specialCard : specialCards) {
            if (specialCard.getSpecialCardType().isChanceCard()) {
                this.chanceCards.add(specialCard);
            } else if (specialCard.getSpecialCardType().isCommunityCard()) {
                this.communityCards.add(specialCard);
            } else {
                System.out.println("Non chance or community card found: " + specialCard.toString());
            }
        }

        this.chanceCards = randomizeCards(this.chanceCards);
        this.communityCards = randomizeCards(this.communityCards);
    }

    private ArrayList<SpecialCard> randomizeCards(ArrayList<SpecialCard> currentCardsToShuffle) {
        ArrayList<SpecialCard> clonedCards = new ArrayList<SpecialCard>(currentCardsToShuffle);
        ArrayList<SpecialCard> shuffledCards = new ArrayList<SpecialCard>();

        while (clonedCards.size() > 0) {
            int cardToRemove = (int) (Math.floor(((Math.random()) * clonedCards.size()) + 0.0));
            shuffledCards.add(clonedCards.remove(cardToRemove));
        }

        return shuffledCards;
    }

    private void setPlayersOnGoBlock(ArrayList<Player> currentPlayers) {
        Block goBlock = this.getBlocks().get(GO_BLOCK_INDEX);

        for (Player player : currentPlayers) {
            goBlock.addPlayerToBlock(player);
            player.setCurrentBlock(goBlock);
        }
    }

    public void moveByDiceRoll(Player movingPlayer, Dice dice) {
        Block currentBlock = movingPlayer.getCurrentBlock();
        int currentBlockNumber = currentBlock.getBlockNumber();

        int newBlockPosition = currentBlockNumber + dice.getTotalDiceValue();

        newBlockPosition = adjustBlockPosition(newBlockPosition, movingPlayer);

        Block newBlock = this.getBlocks().get(newBlockPosition);

        this.moveToBlock(movingPlayer, newBlock);
    }

    public void moveToBlock(Player movingPlayer, Block moveToBlock) {
        Block currentBlock = movingPlayer.getCurrentBlock();

        currentBlock.removePlayerFromBlock(movingPlayer);
        moveToBlock.addPlayerToBlock(movingPlayer);
        movingPlayer.setCurrentBlock(moveToBlock);
    }

    public void moveBackwards(Player movingPlayer, int moveByNumberOfBlocks) {
        int currentBlockNumber = movingPlayer.getCurrentBlock().getBlockNumber();
        int newBlockNumber = currentBlockNumber - moveByNumberOfBlocks;
        newBlockNumber = this.adjustBlockPosition(newBlockNumber, movingPlayer);
        this.moveToBlock(movingPlayer, this.getBlockByIndex(newBlockNumber));
    }

    protected int adjustBlockPosition(int newBlockPosition, Player movingPlayer) {
        if (newBlockPosition < 0) {
            return GameSettings.MAX_BLOCKS_PER_BOARD + newBlockPosition;
        }
        // TODO: Wrap logic is in two different places
        else if (newBlockPosition >= GameSettings.MAX_BLOCKS_PER_BOARD) {
            movingPlayer.playerPassedGo();
            return newBlockPosition - GameSettings.MAX_BLOCKS_PER_BOARD;
        } else if (newBlockPosition == GameSettings.MAX_BLOCKS_PER_BOARD) {
            movingPlayer.playerPassedGo();
            return newBlockPosition - GameSettings.MAX_BLOCKS_PER_BOARD + 1;
        } else {
            movingPlayer.playerDidNotPassGo();
            return newBlockPosition;
        }
    }

    public Block getJailBlock() {
        for (Block block : getBlocks()) {
            if (block.isJailBlock()) {
                return block;
            }
        }

        // This should never happen
        throw new RuntimeException("Jail block dosen't exist.");
    }

    public SpecialCard getNextChanceCard() {
        SpecialCard selectedChanceCard = this.chanceCards.remove(0);
        this.chanceCards.add(selectedChanceCard);
        return selectedChanceCard;
    }

    public SpecialCard getNextCommunityCard() {
        SpecialCard selectedCommunnityCard = this.communityCards.remove(0);
        this.communityCards.add(selectedCommunnityCard);
        return selectedCommunnityCard;
    }

    public boolean isPropertyInMonopolyOwnership(int blockNumber) {
        Block currentBlock = this.getBlocks().get(blockNumber);
        if (currentBlock.isLandBlock()) {
            PropertyBlock currentPropertyBlock = (PropertyBlock) currentBlock;
            ColorEnum currentBlockColor = currentBlock.getBlockColor();
            Player currentBlockOwner = currentPropertyBlock.getOwner();

            for (Block block : this.getBlocks()) {
                if (block.isLandBlock()) {
                    PropertyBlock propertyBlock = (PropertyBlock) block;
                    if (propertyBlock.getBlockColor().equals(currentBlockColor)) {
                        if (propertyBlock.isOwned()) {
                            if (propertyBlock.getOwner().equals(currentBlockOwner)) {
                                // propertyBlock is owned by currentPlayer
                            } else {
                                // Returns false as soon as a non currentPlayer owner is found
                                return false;
                            }
                        } else {
                            // Returns false because we found a property in the same color group that is not
                            // owned at all
                            return false;
                        }
                    }
                }
            }
        } else {
            return false;
        }

        return true;
    }

    public Block findNextBlock(Block currentBlock, BlockTypeEnum blockType) {
        Block nextBlock = this.findNextBlock(currentBlock);
        System.out.println("findNextBlock for " + currentBlock.getBlockName() + " of type "
                + blockType.getBlockTypeName() + " is it: " + nextBlock.getBlockName());
        while (nextBlock.getBlockType().equals(blockType) == false) {
            nextBlock = this.findNextBlock(nextBlock);
            System.out.println("findNextBlock for " + currentBlock.getBlockName() + " of type "
                    + blockType.getBlockTypeName() + " is it: " + nextBlock.getBlockName());
        }
        return nextBlock;

    }

    // TODO: Change this thingamajig to usanize adjustBlockPosition method.
    private Block findNextBlock(Block currentBlock) {
        int nextBlockNumber = currentBlock.getBlockNumber() + 1;
        if (nextBlockNumber >= GameSettings.MAX_BLOCKS_PER_BOARD) {
            nextBlockNumber = Board.GO_BLOCK_INDEX;
        }
        System.out.println(
                "\tfindNextBlock for " + currentBlock.getBlockNumber() + " and nextBlock is " + nextBlockNumber);
        return this.getBlockByIndex(nextBlockNumber);
    }
}
