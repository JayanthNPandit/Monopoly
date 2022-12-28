package com.monopoly.gameplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.monopoly.board.Block;
import com.monopoly.board.BlockTypeEnum;
import com.monopoly.board.Board;
import com.monopoly.board.ColorEnum;
import com.monopoly.board.FakeBlock;
import com.monopoly.board.PropertyBlock;
import com.monopoly.board.SpecialCard;
import com.monopoly.displays.helper.AudioHelper;
import com.monopoly.game.MonopolyGameController;
import com.monopoly.player.FakePlayer;
import com.monopoly.player.HumanPlayer;
import com.monopoly.player.Player;

public class MonopolyGameEngine {
    private static final int EMPTY_DICE_ROLL = 0;
    private static final MonopolyGameEngine gameEngine = new MonopolyGameEngine();
    private Board board = null;
    private ArrayList<Player> currentPlayers = null;
    private Player currentlyPlayingPlayer = null;
    private SpecialCard selectedSpecialCard = null;
    private Dice dice;

    public static void main(String[] args) {
        // MonopolyGameEngine.getInstance().startGame();
    }

    public static MonopolyGameEngine getInstance() {
        return gameEngine;
    }

    private MonopolyGameEngine() {
        initializePlayers(GameSettings.getNumberOfPlayers());
        this.board = new Board(currentPlayers);
    }

    private void initializePlayers(int numberOfPlayers) {
        this.currentPlayers = new ArrayList<Player>();

        this.currentPlayers.add(new FakePlayer());

        // TODO: Get from that one's Google Sheets
        ColorEnum[] playerColors = { ColorEnum.DARK_BLUE, ColorEnum.GREEN, ColorEnum.RED, ColorEnum.ORANGE };

        for (int numPlayer = 1; numPlayer <= numberOfPlayers; numPlayer++) {
            this.currentPlayers.add(new HumanPlayer("Player # " + numPlayer, numPlayer, playerColors[numPlayer - 1]));
        }

        this.currentlyPlayingPlayer = this.currentPlayers.get(GameSettings.FIRST_PLAYER_NUMBER);
    }

    public Player getPlayerFromNumber(int playerNumber) {
        return this.getCurrentPlayers().get(playerNumber);
    }

    /*
     * public void startGame() { displayBoard(); displayPlayers();
     * System.out.println(); System.out.println();
     * 
     * pause(); for (int i = 1; i <= 20; i++) { System.out.println(
     * "--------------------------------------------------------------------");
     * System.out.println("Turn: " + i); for (Player player : currentPlayers) {
     * 
     * if ((player instanceof FakePlayer) == false) { playerTurn(player); } }
     * System.out.println(
     * "--------------------------------------------------------------------");
     * System.out.println(); System.out.println(); displayBoard();
     * System.out.println(); System.out.println(); pause(); } }
     */

    public void playerTurn(Player player, Dice dice) {
        // Moving these two lines to MonopolyGameController.startPlayerTurn
        // this.setCurrentlyPlayingPlayer(player);
        // movePlayer(player, dice);
        System.out.println();
        automaticGameActions(player);
        playerDecides(player, dice);
        System.out.println();
    }

    public void setupForNextPlayer() {
        // Do this for the "currently playing player" BEFORE changing the player to the
        // "next player"
        this.getCurrentlyPlayingPlayer().playerNextTurn();

        do {
            // Change the currently playing player to the "next player"
            if (this.getCurrentlyPlayingPlayerNumber() + 1 > GameSettings.getNumberOfPlayers()) {
                this.setCurrentlyPlayingPlayer(this.getPlayer(GameSettings.FIRST_PLAYER_NUMBER));
            } else {
                this.setCurrentlyPlayingPlayer(this.getPlayer(this.getCurrentlyPlayingPlayerNumber() + 1));
            }
            System.out
                    .println("Checking to see if Next Player is Bankrupt: " + this.getCurrentlyPlayingPlayer().getName()
                            + " isBankrupt = " + this.getCurrentlyPlayingPlayer().isBankrupt() + " Cash = "
                            + this.getCurrentlyPlayingPlayer().getCurrentCash() + "; Property Value = "
                            + this.getCurrentlyPlayingPlayer().getTotalPropertyValue());
        } while (this.getCurrentlyPlayingPlayer().isBankrupt());
    }

    private void automaticGameActions(Player player) {
        // Player Earns Salary automatically when passing Go
        if (player.hasPlayerPassedGo()) {
            System.out.println("\tPlayer " + player.getPlayerNumber() + " should earn Salary now!  Before cash = "
                    + player.getCurrentCash());
            playerEarnsSalary(player);
            System.out.println("\tPlayer " + player.getPlayerNumber() + " earned Salary now!  After cash = "
                    + player.getCurrentCash());
        }
    }

    public void playBlockSounds(Block currentBlock) {
        if (MonopolyGameController.getInstance().shouldSoundBePlayed()) {
            // Play Block-specific Sounds
            playBlockSpecificSounds(currentBlock);
            playSpecialSounds(currentBlock);
            MonopolyGameController.getInstance().soundPlayedOnce();
        }
    }

    private void playBlockSpecificSounds(Block currentBlock) {
        System.out
                .println("Playing " + currentBlock.getBlockName() + " sound file: " + currentBlock.getSoundFileName());
        new AudioHelper().playSound(GameSettings.SOUND_FILE_LOCATION_PREFIX + currentBlock.getSoundFileName());
    }

    private void playSpecialSounds(Block currentBlock) {
        switch (currentBlock.getBlockType()) {
            case DUMMY_BLOCK: {
                System.out.println("Unexpected dummy block in playSpecialBlockSounds() on " + currentBlock);
                break;
            }
            case GO_BLOCK: {
                new AudioHelper()
                        .playSound(GameSettings.SOUND_FILE_LOCATION_PREFIX + GameSettings.SOUND_FOR_LANDED_ON_GO_BLOCK);
                break;
            }
            case LAND_BLOCK: {
                if (currentBlock.isAvailableForPurchase()) {
                    new AudioHelper().playSound(
                            GameSettings.SOUND_FILE_LOCATION_PREFIX + GameSettings.SOUND_FOR_PURCHASE_LAND_QUESTION);
                } else {
                    new AudioHelper().playSound(
                            GameSettings.SOUND_FILE_LOCATION_PREFIX + GameSettings.SOUND_FOR_PAY_RENT_YOU_MUST);
                }
                break;
            }
            case ROAD_BLOCK: {
                if (currentBlock.isAvailableForPurchase()) {
                    new AudioHelper().playSound(
                            GameSettings.SOUND_FILE_LOCATION_PREFIX + GameSettings.SOUND_FOR_LANDED_ON_GO_BLOCK);
                } else {
                    new AudioHelper().playSound(
                            GameSettings.SOUND_FILE_LOCATION_PREFIX + GameSettings.SOUND_FOR_PAY_RENT_YOU_MUST);
                }
                break;
            }
            case UTILITY_BLOCK: {
                if (currentBlock.isAvailableForPurchase()) {
                    new AudioHelper().playSound(
                            GameSettings.SOUND_FILE_LOCATION_PREFIX + GameSettings.SOUND_FOR_LANDED_ON_GO_BLOCK);
                } else {
                    new AudioHelper().playSound(
                            GameSettings.SOUND_FILE_LOCATION_PREFIX + GameSettings.SOUND_FOR_PAY_RENT_YOU_MUST);
                }
                break;
            }
            case LOTTERY_BLOCK: {
                // TODO: Lottery Sound played based on money or no-money situation
                break;
            }
            case JAIL_BLOCK: {
                new AudioHelper()
                        .playSound(GameSettings.SOUND_FILE_LOCATION_PREFIX + GameSettings.SOUND_FOR_VISITING_JAIL);
                break;
            }
            case GO_TO_JAIL_BLOCK: {
                new AudioHelper()
                        .playSound(GameSettings.SOUND_FILE_LOCATION_PREFIX + GameSettings.SOUND_FOR_GO_TO_JAIL);
                break;
            }
            case CHANCE_BLOCK: {
                // TODO: Play Chance Card specific Sound File
                break;
            }
            case COMMUNITY_BLOCK: {
                // TODO: Play Community Card specific Sound File
                break;
            }
            case CASH_PAYMENT_BLOCK: {
                new AudioHelper().playSound(GameSettings.SOUND_FILE_LOCATION_PREFIX + GameSettings.SOUND_FOR_PAY_MONEY);
                break;
            }
            default: {
                System.out.println("Unknown block in playSpecialSounds(): " + currentBlock);
                break;
            }
        }
    }

    private void playerDecides(Player player, Dice dice) {
        System.out.println(
                "\tPlayer, " + player.getName() + ", has to decide what to do in block: " + player.getCurrentBlock());
        switch (player.getCurrentBlock().getBlockType()) {
            case DUMMY_BLOCK: {
                System.out.println("Unexpected dummy block in playerDecides() on " + player.getCurrentBlock());
                break;
            }
            case GO_BLOCK: {
                // playerEarnsSalary(player); This is done by automaticGameActions
                System.out.println("\tPlayer " + player.getPlayerNumber() + " landed on Go!");
                break;
            }
            case LAND_BLOCK: {
                playerOnLandBlock(player, dice);
                break;
            }
            case ROAD_BLOCK: {
                playerOnRoadBlock(player, dice);
                break;
            }
            case UTILITY_BLOCK: {
                playerOnUtilityBlock(player, dice);
                break;
            }
            case LOTTERY_BLOCK: {
                // playerOnLotteryBlock(player); This is done by
                // MonopolyGameController.performGovernmentAction()
                break;
            }
            case JAIL_BLOCK: {
                playerIsAtTheJail(player, dice);
                break;
            }
            case GO_TO_JAIL_BLOCK: {
                // playerGetsArrested(player); This is done by
                // MonopolyGameController.performGovernmentAction()
                break;
            }
            case CHANCE_BLOCK: {
                // playerOnChanceBlock(player); This is done by pressing "C"
                break;
            }
            case COMMUNITY_BLOCK: {
                // playerOnChanceBlock(player); This is done by pressing "C"
                break;
            }
            case CASH_PAYMENT_BLOCK: {
                // Done on 10/4/20 by Jayanth and Anumita
                // playerOnCashBlock(player); This is now done by
                // MonopolyGameController.performGovernmentAction()
                break;
            }
            default: {
                System.out.println("Unknown block in playerDecides(): " + player.getCurrentBlock());
                break;
            }
        }
    }

    private void playerEarnsSalary(Player player) {
        player.recieveCashFrom(Bank.getInstance(), GameSettings.getGoBlockEarnings());
        new AudioHelper().playSound(GameSettings.SOUND_FILE_LOCATION_PREFIX + GameSettings.SOUND_FOR_COLLECT_SALARY);
    }

    private void playerOnLandBlock(Player player, Dice dice) {
        playerOnPropertyBlock(player, dice);
    }

    private void playerOnRoadBlock(Player player, Dice dice) {
        playerOnPropertyBlock(player, dice);
    }

    private void playerOnUtilityBlock(Player player, Dice dice) {
        playerOnPropertyBlock(player, dice);
    }

    private void playerOnPropertyBlock(Player player, Dice dice) {
        PropertyBlock currentPropertyBlock = (PropertyBlock) player.getCurrentBlock();

        System.out.println(
                "\tplayerOnPropertyBlock = Player: " + player.getName() + " on " + currentPropertyBlock.getBlockName());

        if (currentPropertyBlock.isOwned()) {
            rentPaymentProcess(player, currentPropertyBlock, dice);
        } else {
            // This scenario should be handled by the Game Board Event Handler if the player
            // chooses to purchase ths property by pressing (P)
            /*
             * if (player.ableToBuyProperty(currentPropertyBlock) == true) { if
             * (player.decideToBuyProperty(currentPropertyBlock) == true) { boolean
             * purchaseSucceeded = player.purchaseProperty(currentPropertyBlock);
             * 
             * if (purchaseSucceeded == false) { //
             * System.out.println("Error in property purchase: " + //
             * currentPropertyBlock.getBlockName()); // Error } } }
             */
        }
    }

    private void rentPaymentProcess(Player player, PropertyBlock currentPropertyBlock, Dice dice) {
        System.out.println("rentPaymentProcess for " + player.getName() + " on " + currentPropertyBlock.getBlockName());
        boolean playerHasPaidRent = false;
        int rentAmount = currentPropertyBlock.calculateRent(dice);
        Player currentBlockOwner = currentPropertyBlock.getOwner();
        while (playerHasPaidRent == false) {
            playerHasPaidRent = player.payRent(currentBlockOwner, rentAmount);

            if (playerHasPaidRent == false) {
                // If player has properties to sell to pay off rent, sell those properties in a
                // bidding war.
                if (player.hasEnoughAssetsForRent(rentAmount) == true) {
                    PropertyBlock propertyToBeSold = player.decidePropertyToSell();
                    player.sellProperty(propertyToBeSold);
                    // Try to pay rent again at start of while loop
                } else {
                    player.goBankrupt(currentBlockOwner);
                    break; // Player can't pay but game continues
                }
            }
        }
    }

    public String playerOnLotteryBlock(Player player) {
        if (GameSettings.isBankPaymentsToLottery() == true) {
            return player.playLottery();
        } else {
            new AudioHelper().playSound(GameSettings.SOUND_FILE_LOCATION_PREFIX + GameSettings.SOUND_FOR_NO_LOTTERY);
            return "On the Free Parking block, you are. Bad it is, no lottery for you.";
        }
    }

    private void playerIsAtTheJail(Player player, Dice dice) {
        if (player.isInJail()) {
            if (dice.isDoubles()) {
                player.setInJail(false);
            }
        } else {
            System.out.println("Visiting friends, are you? Then you need better friends who don't get arrested.");
        }
    }

    public void playerGetsArrested(Player player) {
        System.out.println("Got caught, did you?");
        player.setInJail(true);
        this.movePlayer(player, this.getBoard().getJailBlock());
    }

    public void playerOnChanceBlock(Player player) {
        if (player.getCurrentBlock().getBlockType().isChanceBlock()) {
            selectedSpecialCard = this.getBoard().getNextChanceCard();
        } else if (player.getCurrentBlock().getBlockType().isCommunityBlock()) {
            selectedSpecialCard = this.getBoard().getNextCommunityCard();
        } else {
            System.out.println("Found a non chance or community block: " + player.getCurrentBlock().getBlockName());
            throw new RuntimeException(
                    "Found a non chance or community block: " + player.getCurrentBlock().getBlockName());
        }
        System.out.println("playerOnChanceBlock = " + selectedSpecialCard);
        playerDoesWhatSpecialCardInstructs(player, selectedSpecialCard);
        playSpecialCardSounds(selectedSpecialCard);
    }

    private void playSpecialCardSounds(SpecialCard aSelectedSpecialCard) {
        new AudioHelper().playSound(
                GameSettings.SOUND_FILE_LOCATION_PREFIX + aSelectedSpecialCard.getSpecialCardSoundFileName());
    }

    private void playerDoesWhatSpecialCardInstructs(Player player, SpecialCard selectedCard) {
        // TODO: Add debugging statements to each of the cases
        System.out.println("playerDoesWhatSpecialCardInstrucuts = " + selectedCard.getSpecialCardDescription());
        switch (selectedCard.getSpecialCardRule()) {
            case ADVANCE_TO_BLOCK: {
                System.out.println("selectedCard rule: " + selectedCard.getSpecialCardRule());
                System.out.println("selectedCard block to go to: "
                        + this.getBoard().getBlockByIndex(selectedCard.getSpecialCardGoToBlockNumber()).getBlockName());
                // TODO: Deal with GO Block payment/bypass logic
                this.movePlayer(player, selectedCard.getSpecialCardGoToBlockNumber());
                break;
            }
            case MOVE_FORWARD_TO_ROAD: {
                System.out.println("selectedCard rule: " + selectedCard.getSpecialCardRule());
                System.out.println("selectedCard current location: " + player.getCurrentBlock().getBlockName());
                // TODO: Find next road and move to there
                // TODO: Deal with GO Block payment/bypass logic
                this.movePlayer(player,
                        this.getBoard().findNextBlock(player.getCurrentBlock(), BlockTypeEnum.ROAD_BLOCK));
                break;
            }
            case PLAYER_GETS_CASH_FROM_BANK: {
                System.out.println("selectedCard rule: " + selectedCard.getSpecialCardRule());
                System.out.println("selectedCard cash to recieve: " + selectedCard.getSpecialCardFixedCashAmount());
                if (selectedCard.isSpecialCardParallelUniverse()) {
                    // Does the opposite of what case states
                    player.payChanceCardFines(Bank.getInstance(), selectedCard.getSpecialCardFixedCashAmount());
                } else {
                    player.recieveCashFrom(Bank.getInstance(), selectedCard.getSpecialCardFixedCashAmount());
                }
                break;
            }
            case PLAYER_PAYS_CASH_TO_BANK: {
                System.out.println("selectedCard rule: " + selectedCard.getSpecialCardRule());
                System.out.println("selectedCard cash to pay: " + selectedCard.getSpecialCardFixedCashAmount());
                if (selectedCard.isSpecialCardParallelUniverse()) {
                    // Does the opposite of what case states
                    player.recieveCashFrom(Bank.getInstance(), selectedCard.getSpecialCardFixedCashAmount());
                } else {
                    player.payChanceCardFines(Bank.getInstance(), selectedCard.getSpecialCardFixedCashAmount());
                }
                break;
            }
            case PLAYER_GETS_CASH_FROM_ALL_OTHER_PLAYERS: {
                System.out.println("selectedCard rule: " + selectedCard.getSpecialCardRule());
                System.out.println("selectedCard cash to get: " + selectedCard.getSpecialCardFixedCashAmount());
                if (selectedCard.isSpecialCardParallelUniverse()) {
                    // Does the opposite of what case states
                    for (Player playerToPayTo : this.getCurrentPlayers()) {
                        if (playerToPayTo.equals(player) == false) {
                            player.giveCashTo(playerToPayTo, selectedCard.getSpecialCardFixedCashAmount());
                        }
                    }
                } else {
                    for (Player playerToGetCashFrom : this.getCurrentPlayers()) {
                        if (playerToGetCashFrom.equals(player) == false) {
                            playerToGetCashFrom.giveCashTo(player, selectedCard.getSpecialCardFixedCashAmount());
                        }
                    }
                }
                break;
            }
            case PLAYER_PAYS_CASH_TO_NEXT_PLAYER: {
                System.out.println("selectedCard rule: " + selectedCard.getSpecialCardRule());
                System.out.println("selectedCard cash to pay: " + selectedCard.getSpecialCardFixedCashAmount());
                Player nextPlayer = this.getNextPlayer(player);
                System.out.println(
                        "selectedCard player to give to: " + nextPlayer.getName() + " $" + nextPlayer.getCurrentCash());
                if (selectedCard.isSpecialCardParallelUniverse()) {
                    // Does the opposite of what case states
                    nextPlayer.giveCashTo(player, selectedCard.getSpecialCardFixedCashAmount());
                } else {
                    player.giveCashTo(nextPlayer, selectedCard.getSpecialCardFixedCashAmount());
                }
                break;
            }
            case PLAYER_PAYS_CASH_TO_FREE_PARKING: {
                System.out.println("selectedCard rule: " + selectedCard.getSpecialCardRule());
                System.out.println("selectedCard cash to pay: " + selectedCard.getSpecialCardFixedCashAmount());
                if (selectedCard.isSpecialCardParallelUniverse()) {
                    Bank.getInstance().payToPlayerFromLottery(player, selectedCard.getSpecialCardFixedCashAmount());
                } else {
                    player.payDirectlyToLotteryPot(selectedCard.getSpecialCardFixedCashAmount());
                }
                break;
            }
            case GO_TO_GO: {
                System.out.println("selectedCard rule: " + selectedCard.getSpecialCardRule());
                System.out.println("selectedCard player previous position: " + player.getCurrentBlock().getBlockName());
                // TODO: Deal with GO Block payment/bypass logic

                if (selectedCard.isSpecialCardParallelUniverse()) {
                    // Does the opposite of what case indicates
                    // In parallel universe, you pay the GO Block salary
                    this.movePlayer(player, Board.GO_BLOCK_INDEX);
                } else {
                    this.movePlayer(player, Board.GO_BLOCK_INDEX);
                }
                System.out.println("selectedCard player current position: " + player.getCurrentBlock().getBlockName());
                break;
            }
            case GO_BACKWARDS_TO_GO: {
                System.out.println("selectedCard rule: " + selectedCard.getSpecialCardRule());
                System.out.println("selectedCard player previous position: " + player.getCurrentBlock().getBlockName());
                // TODO: Deal with GO Block payment/bypass logic

                if (selectedCard.isSpecialCardParallelUniverse()) {
                    // Does the opposite of what case indicates
                    // In parallel universe, you pay the GO Block salary
                    this.movePlayer(player, Board.GO_BLOCK_INDEX);
                } else {
                    this.movePlayer(player, Board.GO_BLOCK_INDEX);
                }
                System.out.println("selectedCard player current position: " + player.getCurrentBlock().getBlockName());
                break;
            }
            case MOVE_FORWARD: {
                System.out.println("selectedCard rule: " + selectedCard.getSpecialCardRule());
                System.out.println("selectedCard spaces to move by: " + selectedCard.getSpecialCardNumberOfBlocks());
                System.out.println("selectedCard player previous position: " + player.getCurrentBlock().getBlockName());
                // TODO: Deal with GO Block payment/bypass logic
                Dice tempDice = new Dice(selectedCard.getSpecialCardNumberOfBlocks(), 0);
                this.movePlayer(player, tempDice);
                System.out.println("selectedCard player current position: " + player.getCurrentBlock().getBlockName());
                break;
            }
            case MOVE_BACKWARD: {
                System.out.println("selectedCard rule: " + selectedCard.getSpecialCardRule());
                System.out.println("selectedCard spaces to move by: " + selectedCard.getSpecialCardNumberOfBlocks());
                System.out.println("selectedCard player previous position: " + player.getCurrentBlock().getBlockName());
                // TODO: Deal with GO Block payment/bypass logic
                this.movePlayerBackwards(player, selectedCard.getSpecialCardNumberOfBlocks());
                System.out.println("selectedCard player current position: " + player.getCurrentBlock().getBlockName());
                break;
            }
            case GO_DIRECTLY_TO_JAIL: {
                System.out.println("selectedCard rule: " + selectedCard.getSpecialCardRule());
                System.out.println("selectedCard player previous state (In jail): " + player.isInJail());
                System.out.println("selectedCard player previous position: " + player.getCurrentBlock().getBlockName());
                // TODO: Deal with GO Block payment/bypass logic
                this.movePlayer(player, Board.JAIL_BLOCK_INDEX);
                player.setInJail(true);
                System.out.println("selectedCard player current state (In jail): " + player.isInJail());
                System.out.println("selectedCard player current position: " + player.getCurrentBlock().getBlockName());
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown SpecialCardRuleEnum: " + selectedCard.getSpecialCardRule());
            }
        }
    }

    private Player getNextPlayer(Player player) {
        int nextPlayerNumber = player.getPlayerNumber() + 1;

        // Resetting nextPlayerNumber to 0 in case the current player is the last player
        if (nextPlayerNumber >= GameSettings.getNumberOfPlayers()) {
            nextPlayerNumber = 0;
        }

        return this.getCurrentPlayers().get(nextPlayerNumber);
    }

    public void playerOnCashBlock(Player player) {
        player.payCashBlockFines(Bank.getInstance(), player.getCurrentBlock().getBlockCashAmounts());
    }

    public void movePlayer(Player player, Dice dice) {
        this.getBoard().moveByDiceRoll(player, dice);

        System.out.println("\n\nmovePlayer: " + player.toString());
    }

    private void movePlayer(Player player, Block newBlock) {
        // TODO: Direct calls to moveToBlock is bypassing the "pass-go" logic of
        // moveByDiceRoll
        this.getBoard().moveToBlock(player, newBlock);

        System.out.println("\n\nmovePlayer: " + player.toString());
    }

    private void movePlayer(Player player, int blockNumber) {
        movePlayer(player, this.getBoard().getBlocks().get(blockNumber));
    }

    private void movePlayerBackwards(Player player, int blocksToMoveBy) {
        this.getBoard().moveBackwards(player, blocksToMoveBy);
    }

    public void displayBoard() {
        clearScreen();
        System.out.println("Current Board: ");
        for (Block block : this.getBoard().getBlocks()) {
            if ((block instanceof FakeBlock) == false) {
                System.out.println(block.toString());
            }
        }
    }

    public static void clearScreen() {
        try {
            Runtime.getRuntime().exec("cls");
        } catch (IOException e) {
            // Ignore
        }
    }

    public void pause() {
        System.out.println("Press \"ENTER\" to continue");
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
    }

    public void displayPlayers() {
        System.out.println("Current Players: ");

        for (Player player : this.getCurrentPlayers()) {
            if ((player instanceof FakePlayer) == false) {
                System.out.println(player.toString());
            }
        }
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<Player> getCurrentPlayers() {
        return currentPlayers;
    }

    public Player getPlayer(int playerIndex) {
        return this.getCurrentPlayers().get(playerIndex);
    }

    public SpecialCard getSelectedSpecialCard() {
        return selectedSpecialCard;
    }

    public Player getCurrentlyPlayingPlayer() {
        return currentlyPlayingPlayer;
    }

    public int getCurrentlyPlayingPlayerNumber() {
        return this.getCurrentlyPlayingPlayer().getPlayerNumber();
    }

    public void setCurrentlyPlayingPlayer(Player currentlyPlayingPlayer) {
        this.currentlyPlayingPlayer = currentlyPlayingPlayer;
    }

    public void rollDice(int aDiceRoll_1, int aDiceRoll_2) {
        this.dice = new Dice(aDiceRoll_1, aDiceRoll_2);
    }

    public void rollDice() {
        this.dice = new Dice();
        this.dice.rollDice();
    }

    public void resetDice() {
        this.rollDice(MonopolyGameEngine.EMPTY_DICE_ROLL, MonopolyGameEngine.EMPTY_DICE_ROLL);
    }

    public Dice getDice() {
        return this.dice;
    }

    public Player getGameWinner() {
        Player playerWithHighestNetWorth = null;

        for (Player player : this.currentPlayers) {
            if (playerWithHighestNetWorth == null) {
                playerWithHighestNetWorth = player;
            }

            if (player.getTotalNetWorth() > playerWithHighestNetWorth.getTotalNetWorth()) {
                playerWithHighestNetWorth = player;
            }
        }

        return playerWithHighestNetWorth;
    }

    public boolean isGameOver() {
        boolean gameOver = true;
        int numberOfNonBankruptPlayers = 0;

        if (MonopolyGameController.getInstance().getGamePlayTurnNumber() >= GameSettings.getMaxMoves()) {
            return gameOver;
        } else {

            for (Player player : this.currentPlayers) {
                if (player.isBankrupt() == false) {
                    numberOfNonBankruptPlayers++;
                }

                if (numberOfNonBankruptPlayers >= 2) {
                    gameOver = false;
                    break;
                }
            }
        }

        return gameOver;
    }
}
