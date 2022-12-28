package com.monopoly.gameplay;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;

import com.monopoly.board.Block;
import com.monopoly.board.BlockTypeEnum;
import com.monopoly.board.CashPaymentBlock;
import com.monopoly.board.ChanceBlock;
import com.monopoly.board.ColorEnum;
import com.monopoly.board.FakeBlock;
import com.monopoly.board.GoBlock;
import com.monopoly.board.GoToJailBlock;
import com.monopoly.board.JailBlock;
import com.monopoly.board.LandBlock;
import com.monopoly.board.LotteryBlock;
import com.monopoly.board.RoadBlock;
import com.monopoly.board.SpecialCard;
import com.monopoly.board.SpecialCardRuleEnum;
import com.monopoly.board.SpecialCardTypeEnum;
import com.monopoly.board.UtilityBlock;

public class GameSettings {
    private static final String NA_STRING = "N/A";
    private static final String GOOD_FLAG_VALUE = "Good";
    private static final String BAD_FLAG_VALUE = "Bad";

    // Block column indexes
    private static final int BLOCKS_CONFIG_FILE_BLOCK_NUMBER_COLUMN = 0;
    private static final int BLOCKS_CONFIG_FILE_BLOCK_TYPE_COLUMN = 1;
    private static final int BLOCKS_CONFIG_FILE_BLOCK_NAME_COLUMN = 2;
    private static final int BLOCKS_CONFIG_FILE_BLOCK_DISPLAY_DETAILS_COLUMN = 3;
    private static final int BLOCKS_CONFIG_FILE_BLOCK_COLOR_COLUMN = 4;
    private static final int BLOCKS_CONFIG_FILE_BLOCK_PURCHASE_COST_COLUMN = 5;
    private static final int BLOCKS_CONFIG_FILE_HOUSE_PURCHASE_COST_COLUMN = 6;
    private static final int BLOCKS_CONFIG_FILE_APARTMENT_PURCHASE_COST_COLUMN = 7;
    private static final int BLOCKS_CONFIG_FILE_LAND_RENT_COLUMN = 8;
    private static final int BLOCKS_CONFIG_FILE_HOUSE_RENT_COLUMN = 9;
    private static final int BLOCKS_CONFIG_FILE_APARTMENT_RENT_COLUMN = 10;
    private static final int BLOCKS_CONFIG_FILE_MAX_HOUSES_COLUMN = 11;
    private static final int BLOCKS_CONFIG_FILE_MAX_APARTMENTS_COLUMN = 12;
    private static final int BLOCKS_CONFIG_FILE_MORTGAGE_VALUE_COLUMN = 13;
    private static final int BLOCKS_CONFIG_FILE_SINGLE_UTILITY_DICE_RENT_COLUMN = 14;
    private static final int BLOCKS_CONFIG_FILE_MULTI_UTILITY_DICE_RENT_COLUMN = 15;
    private static final int BLOCKS_CONFIG_FILE_SINGLE_ROAD_BLOCK_RENT_COLUMN = 16;
    private static final int BLOCKS_CONFIG_FILE_MULTI_ROAD_BLOCK_RENT_COLUMN = 17;
    private static final int BLOCKS_CONFIG_FILE_BLOCK_CASH_AMOUNTS = 18;
    private static final int BLOCK_CONFIG_FILE_BLOCK_SOUNDFILE_NAME = 19;

    // Special Card column indexes
    private static final int CARDS_CONFIG_FILE_CARD_TYPE_COLUMN = 0;
    private static final int CARDS_CONFIG_FILE_CARD_TITLE_COLUMN = 1;
    private static final int CARDS_CONFIG_FILE_CARD_DESCRIPTION_COLUMN = 2;
    private static final int CARDS_CONFIG_FILE_GOOD_OR_BAD_COLUMN = 3;
    private static final int CARDS_CONFIG_FILE_CARD_RULE_COLUMN = 4;
    private static final int CARDS_CONFIG_FILE_CARD_FIXED_CASH_AMOUNT_COLUMN = 5;
    private static final int CARDS_CONFIG_FILE_CARD_DICE_CASH_MULTIPLIER_COLUMN = 6;
    private static final int CARDS_CONFIG_FILE_CARD_LAND_CASH_MULTIPLIER_COLUMN = 7;
    private static final int CARDS_CONFIG_FILE_CARD_HOUSE_CASH_MULTIPLIER_COLUMN = 8;
    private static final int CARDS_CONFIG_FILE_CARD_APARTMENT_CASH_MULTIPLIER_COLUMN = 9;
    private static final int CARDS_CONFIG_FILE_CARD_NUMBER_OF_BLOCKS_COLUMN = 10;
    private static final int CARDS_CONFIG_FILE_CARD_PARALLEL_UNIVERSE_COLUMN = 11;
    private static final int CARDS_CONFIG_FILE_CARD_GO_TO_BLOCK_NUMBER_COLUMN = 12;
    private static final int CARDS_CONFIG_FILE_CARD_BYPASS_GO_BLOCK_COLUMN = 13;
    private static final int CARDS_CONFIG_FILE_CARD_SOUNDFILE_NAME = 14;

    // Sound file locations
    public static final String SOUND_FILE_LOCATION_PREFIX = "./src/main/resources/SoundFiles/";
    public static final String SOUND_FOR_DICE_THROW = "dice_throw.wav";
    public static final String SOUND_FOR_COLLECT_SALARY = "passedGoAndCollectSalary.wav";
    public static final String SOUND_FOR_EARN_MONEY = "earn_money.wav";
    public static final String SOUND_FOR_WINNING_LOTTERY = "lottery_winner.wav";
    public static final String SOUND_FOR_PAY_MONEY = "pay_money.wav";
    public static final String SOUND_FOR_NO_LOTTERY = "lottery_no_money.wav";
    public static final String SOUND_FOR_GO_TO_JAIL = "go_to_jail.wav";
    public static final String SOUND_FOR_VISITING_JAIL = "visiting_jail.wav";
    public static final String SOUND_FOR_LANDED_ON_GO_BLOCK = "landed_on_go_block.wav";
    public static final String SOUND_FOR_PURCHASE_LAND_QUESTION = "purchase_land_question.wav";
    public static final String SOUND_FOR_PAY_RENT_YOU_MUST = "pay_rent_you_must.wav";

    private static final String GAME_TITLE = "Monopoly Game - by Jayanth, Anumita, Deepak & Roopa";
    // Game rules
    private static final int MAX_PLAYERS = 4;
    public static final int FIRST_PLAYER_NUMBER = 1;
    public static final int MAX_BLOCKS_PER_BOARD = 40;
    private static final String MONOPOLY_BLOCKS_CONFIG_FILENAME = "./src/main/resources/TestGame_Monopoly_Blocks.csv";
    private static final String MONOPOLY_SPECIAL_CARDS_CONFIG_FILENAME = "./src/main/resources/TestGame_Monopoly_SpecialCards.csv";

    private static int maxHousesPerBlock = 4;
    private static int maxApartmentsPerBlock = 1;
    private static GameTypeEnum gameType = GameTypeEnum.PLAY_UNTIL_MAX_MOVES;
    private static int maxMoves = 200;
    private static int numberOfPlayers = MAX_PLAYERS;
    private static int numHumanPlayers = MAX_PLAYERS;
    private static int numComputerPlayers = 0;
    private static int startingPlayerCash = 1500;
    private static int startingBankBalance = 50000;
    private static boolean bankPaymentsToLottery = true;
    private static boolean lotteryGamePlayed = true;
    private static boolean playerDealsAllowed = false;
    private static MortgagedRentCollectionRuleEnum mortagedRentCollectionRule = MortgagedRentCollectionRuleEnum.PAY_TO_BANK;
    private static int goBlockEarnings = 200;
    private static boolean mustOwnFullColorGroupBeforeBuilding = true;
    private static boolean mustBuildAllHousesBeforeApartments = false;

    public static String getGameTitle() {
        return GAME_TITLE;
    }

    public static int getMaxHousesPerBlock() {
        return maxHousesPerBlock;
    }

    public static void setMaxHousesPerBlock(int maxHousesPerBlock) {
        GameSettings.maxHousesPerBlock = maxHousesPerBlock;
    }

    public static int getMaxApartmentsPerBlock() {
        return maxApartmentsPerBlock;
    }

    public static void setMaxApartmentsPerBlock(int maxApartmentsPerBlock) {
        GameSettings.maxApartmentsPerBlock = maxApartmentsPerBlock;
    }

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public static void setNumberOfPlayers(int numberOfPlayers) {
        GameSettings.numberOfPlayers = numberOfPlayers;
    }

    public static ArrayList<Block> readMonopolyBlocksConfigFile(String inputFilename) {
        ArrayList<String> inputLines = readTextFile(inputFilename);

        ArrayList<Block> blocks = new ArrayList<Block>();
        blocks.add(new FakeBlock());

        for (String line : inputLines) {
            Block block = createBlockFromConfigLine(line);
            blocks.add(block);
        }

        return blocks;
    }

    private static Block createBlockFromConfigLine(String line) {
        String[] lineItems = line.split(",");

        removeDoubleQuotes(lineItems);

        int blockNumber = convertToInt(lineItems[BLOCKS_CONFIG_FILE_BLOCK_NUMBER_COLUMN]);
        BlockTypeEnum blockTypeEnum = BlockTypeEnum.valueFromName(lineItems[BLOCKS_CONFIG_FILE_BLOCK_TYPE_COLUMN]);
        if (blockTypeEnum == null) {
            System.out.println("blockTypeEnum is null for " + blockNumber);
        }
        Block block = null;

        switch (blockTypeEnum) {
        case DUMMY_BLOCK: {
            System.out.println("Unexpected dummy block in createBlockFromConfigLine() on " + blockTypeEnum);
            break;
        }
        case GO_BLOCK: {
            block = new GoBlock(blockNumber);
            break;
        }
        case LAND_BLOCK: {
            block = new LandBlock(blockNumber);
            break;
        }
        case ROAD_BLOCK: {
            block = new RoadBlock(blockNumber);
            break;
        }
        case UTILITY_BLOCK: {
            block = new UtilityBlock(blockNumber);
            break;
        }
        case LOTTERY_BLOCK: {
            block = new LotteryBlock(blockNumber);
            break;
        }
        case JAIL_BLOCK: {
            block = new JailBlock(blockNumber);
            break;
        }
        case GO_TO_JAIL_BLOCK: {
            block = new GoToJailBlock(blockNumber);
            break;
        }
        case CHANCE_BLOCK: {
            block = new ChanceBlock(blockNumber);
            break;
        }
        case COMMUNITY_BLOCK: {
            block = new ChanceBlock(blockNumber);
            break;
        }
        case CASH_PAYMENT_BLOCK: {
            block = new CashPaymentBlock(blockNumber);
            break;
        }
        default: {
            System.out.println("Unknown block in createBlockFromConfigLine(): " + blockTypeEnum);
            break;
        }
        }

        block.setBlockType(blockTypeEnum);
        block.setBlockName(lineItems[BLOCKS_CONFIG_FILE_BLOCK_NAME_COLUMN]);
        block.setBlockDisplayDetails(lineItems[BLOCKS_CONFIG_FILE_BLOCK_DISPLAY_DETAILS_COLUMN]);
        block.setBlockColor(ColorEnum.valueFromName(lineItems[BLOCKS_CONFIG_FILE_BLOCK_COLOR_COLUMN]));
        block.setBlockPurchaseCost(convertToInt(lineItems[BLOCKS_CONFIG_FILE_BLOCK_PURCHASE_COST_COLUMN]));
        block.setBlockHousePurchaseCost(convertToInt(lineItems[BLOCKS_CONFIG_FILE_HOUSE_PURCHASE_COST_COLUMN]));
        block.setBlockApartmentPurchaseCost(convertToInt(lineItems[BLOCKS_CONFIG_FILE_APARTMENT_PURCHASE_COST_COLUMN]));
        block.setBlockLandRent(convertToInt(lineItems[BLOCKS_CONFIG_FILE_LAND_RENT_COLUMN]));
        block.setBlockHouseRent(convertToInt(lineItems[BLOCKS_CONFIG_FILE_HOUSE_RENT_COLUMN]));
        block.setBlockApartmentRent(convertToInt(lineItems[BLOCKS_CONFIG_FILE_APARTMENT_RENT_COLUMN]));
        block.setBlockMaxHouses(convertToInt(lineItems[BLOCKS_CONFIG_FILE_MAX_HOUSES_COLUMN]));
        block.setBlockMaxApartments(convertToInt(lineItems[BLOCKS_CONFIG_FILE_MAX_APARTMENTS_COLUMN]));
        block.setBlockMortgageValue(convertToInt(lineItems[BLOCKS_CONFIG_FILE_MORTGAGE_VALUE_COLUMN]));
        block.setBlockSingleUtilityDiceRent(
                convertToInt(lineItems[BLOCKS_CONFIG_FILE_SINGLE_UTILITY_DICE_RENT_COLUMN]));
        block.setBlockMultipleUtilityDiceRent(
                convertToInt(lineItems[BLOCKS_CONFIG_FILE_MULTI_UTILITY_DICE_RENT_COLUMN]));
        block.setBlockSingleRoadBlockRent(convertToInt(lineItems[BLOCKS_CONFIG_FILE_SINGLE_ROAD_BLOCK_RENT_COLUMN]));
        block.setBlockMultipleRoadBlockRent(convertToInt(lineItems[BLOCKS_CONFIG_FILE_MULTI_ROAD_BLOCK_RENT_COLUMN]));
        block.setBlockCashAmounts(convertToInt(lineItems[BLOCKS_CONFIG_FILE_BLOCK_CASH_AMOUNTS]));
        block.setSoundFileName(lineItems[BLOCK_CONFIG_FILE_BLOCK_SOUNDFILE_NAME]);

        return block;
    }

    public static ArrayList<SpecialCard> readSpecialCardsConfigFile(String inputFilename) {
        ArrayList<String> inputLines = readTextFile(inputFilename);

        ArrayList<SpecialCard> cards = new ArrayList<SpecialCard>();

        for (String line : inputLines) {
            cards.add(createSpecialCardFromConfigLine(line));
        }

        return cards;
    }

    private static SpecialCard createSpecialCardFromConfigLine(String line) {
        String[] lineItems = line.split(",");

        removeDoubleQuotes(lineItems);

        SpecialCard card = new SpecialCard();

        card.setSpecialCardType(SpecialCardTypeEnum.valueFromName(lineItems[CARDS_CONFIG_FILE_CARD_TYPE_COLUMN]));
        card.setSpecialCardTitle(lineItems[CARDS_CONFIG_FILE_CARD_TITLE_COLUMN]);
        card.setSpecialCardDescription(lineItems[CARDS_CONFIG_FILE_CARD_DESCRIPTION_COLUMN]);
        card.setGoodSpeicalCard(convertToGoodOrBadFlag(lineItems[CARDS_CONFIG_FILE_GOOD_OR_BAD_COLUMN]));
        card.setSpecialCardRule(SpecialCardRuleEnum.valueFromName(lineItems[CARDS_CONFIG_FILE_CARD_RULE_COLUMN]));
        card.setSpecialCardFixedCashAmount(convertToInt(lineItems[CARDS_CONFIG_FILE_CARD_FIXED_CASH_AMOUNT_COLUMN]));
        card.setSpecialCardDiceCashMultiplier(
                convertToInt(lineItems[CARDS_CONFIG_FILE_CARD_DICE_CASH_MULTIPLIER_COLUMN]));
        card.setSpecialCardLandCashMultiplier(
                convertToInt(lineItems[CARDS_CONFIG_FILE_CARD_LAND_CASH_MULTIPLIER_COLUMN]));
        card.setSpecialCardHouseCashMultiplier(
                convertToInt(lineItems[CARDS_CONFIG_FILE_CARD_HOUSE_CASH_MULTIPLIER_COLUMN]));
        card.setSpecialCardApartmentCashMultiplier(
                convertToInt(lineItems[CARDS_CONFIG_FILE_CARD_APARTMENT_CASH_MULTIPLIER_COLUMN]));
        card.setSpecialCardNumberOfBlocks(convertToInt(lineItems[CARDS_CONFIG_FILE_CARD_NUMBER_OF_BLOCKS_COLUMN]));
        card.setSpecialCardParallelUniverse(
                parseBooleanFromString(lineItems[CARDS_CONFIG_FILE_CARD_PARALLEL_UNIVERSE_COLUMN]));
        card.setSpecialCardGoToBlockNumber(convertToInt(lineItems[CARDS_CONFIG_FILE_CARD_GO_TO_BLOCK_NUMBER_COLUMN]));
        card.setSpecialCardBypassGoBlock(
                parseBooleanFromString(lineItems[CARDS_CONFIG_FILE_CARD_BYPASS_GO_BLOCK_COLUMN]));
        card.setSpecialCardSoundFileName(lineItems[CARDS_CONFIG_FILE_CARD_SOUNDFILE_NAME]);

        return card;
    }

    private static boolean convertToGoodOrBadFlag(String goodOrBadFlag) {
        boolean goodOrBadBooleanFlag = false;

        if (GOOD_FLAG_VALUE.equals(goodOrBadBooleanFlag)) {
            goodOrBadBooleanFlag = true;
        } else if (BAD_FLAG_VALUE.equals(goodOrBadBooleanFlag)) {
            goodOrBadBooleanFlag = false;
        } else {
            new InvalidParameterException("Good-or-Bad-Flag is not as expected: " + goodOrBadFlag);
        }

        return goodOrBadBooleanFlag;
    }

    private static void printArray(String[] lineItems) {
        int index = 0;
        for (String item : lineItems) {
            System.out.println("" + index + ": " + item);
            index++;
        }
    }

    private static void removeDoubleQuotes(String[] lineItems) {
        for (int i = 0; i < lineItems.length; i++) {
            lineItems[i] = lineItems[i].replace("\"", "");
        }
    }

    private static boolean parseBooleanFromString(String value) {
        if (value != null) {
            if (value.toUpperCase().equals("YES")) {
                return true;
            } else if (value.toUpperCase().equals("NO")) {
                return false;
            } else if (value.toUpperCase().equals("N/A")) {
                return false;
            } else {
                System.out.println("Unexpected String value: " + value);
                throw new RuntimeException("Unexpected String value: " + value);
            }
        } else {
            System.out.println("Unexpected null value: " + value);
            throw new RuntimeException("Unexpected null value: " + value);
        }
    }

    private static int convertToInt(String value) {
        try {
            if ((value == null) || (value.isEmpty()) || (NA_STRING.equals(value))) {
                return 0;
            } else {
                return Integer.parseInt(value);
            }
        } catch (NumberFormatException e) {
            System.out.println("Expected Integer but found invalid value: [" + value + "]");
            return 0;
        }
    }

    private static ArrayList<String> readTextFile(String inputFilename) {
        ArrayList<String> inputLines = new ArrayList<String>();

        Scanner inputFileScanner;
        try {
            inputFileScanner = new Scanner(new File(inputFilename));
            inputFileScanner.nextLine();
            while (inputFileScanner.hasNext()) {
                inputLines.add(inputFileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error Reading File: " + inputFilename);
            e.printStackTrace();
            System.exit(1);
        }

        return inputLines;
    }

    public static String getMonopolyBlockConfigFilename() {
        return MONOPOLY_BLOCKS_CONFIG_FILENAME;
    }

    public static String getMonopolySpecialCardsConfigFilename() {
        return MONOPOLY_SPECIAL_CARDS_CONFIG_FILENAME;
    }

    public static GameTypeEnum getGameType() {
        return gameType;
    }

    public static int getMaxMoves() {
        return maxMoves;
    }

    public static int getNumHumanPlayers() {
        return numHumanPlayers;
    }

    public static int getNumComputerPlayers() {
        return numComputerPlayers;
    }

    public static int getStartingPlayerCash() {
        return startingPlayerCash;
    }

    public static int getStartingBankBalance() {
        return startingBankBalance;
    }

    public static boolean isBankPaymentsToLottery() {
        return bankPaymentsToLottery;
    }

    public static boolean isLotteryGamePlayed() {
        return lotteryGamePlayed;
    }

    public static boolean isPlayerDealsAllowed() {
        return playerDealsAllowed;
    }

    public static MortgagedRentCollectionRuleEnum getMortagedRentCollectionRule() {
        return mortagedRentCollectionRule;
    }

    public static int getGoBlockEarnings() {
        return goBlockEarnings;
    }

    public static boolean isMustOwnFullColorGroupBeforeBuilding() {
        return mustOwnFullColorGroupBeforeBuilding;
    }

    public static boolean isMustBuildAllHousesBeforeApartments() {
        return mustBuildAllHousesBeforeApartments;
    }
}
