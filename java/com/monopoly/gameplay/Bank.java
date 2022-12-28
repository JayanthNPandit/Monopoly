package com.monopoly.gameplay;

import java.util.ArrayList;

import com.monopoly.board.PropertyBlock;
import com.monopoly.player.Player;

public class Bank {
    private static final Bank singleInstance = new Bank();
    private int bankBalance;
    // TODO: See if you can make lottery its own class with all of the lottery
    // related logic moved to that class
    private int lotteryBalance = 1000;
    private ArrayList<PropertyBlock> allPropertyBlocks;

    public static Bank getInstance() {
        return singleInstance;
    }

    private Bank() {
        bankBalance = GameSettings.getStartingBankBalance();
        allPropertyBlocks = new ArrayList<PropertyBlock>();
    }

    public int getBankBalance() {
        return bankBalance;
    }

    public int getLotteryBalance() {
        return lotteryBalance;
    }

    public void depositPayments(int cash) {
        bankBalance += cash;
    }

    public void depositCashBlockFines(int cash) {
        if (GameSettings.isBankPaymentsToLottery() == true) {
            addToLotteryPot(cash);
        } else {
            depositPayments(cash);
        }
    }

    public void depositChanceCardFines(int cash) {
        // TODO: Do later
    }

    public void addToLotteryPot(int lotteryAmountToAdd) {
        this.lotteryBalance += lotteryAmountToAdd;
    }

    public boolean withdraw(int cash) {
        if (bankBalance >= cash) {
            bankBalance -= cash;
            return true;
        } else {
            return false;
        }
    }

    public int withdrawPercentageOfLottery(float percentageToWithdraw) {
        int lotteryWinnings = (int) (getLotteryBalance() * percentageToWithdraw);
        this.lotteryBalance -= lotteryWinnings;
        return lotteryWinnings;
    }

    public void payToPlayerFromLottery(Player player, int amountToPay) {
        int actualAmountToPay = amountToPay;

        if (this.getLotteryBalance() >= actualAmountToPay) {
            this.lotteryBalance -= actualAmountToPay;
        } else {
            this.lotteryBalance = 0;
            actualAmountToPay = this.lotteryBalance;
        }
        player.addCash(actualAmountToPay);
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();

        ret.append("Bank Balance = " + bankBalance + "\n");

        ret.append("List of all cards");
        for (PropertyBlock block : allPropertyBlocks) {
            ret.append(block.toString());
            ret.append("\n");
        }

        return ret.toString();
    }

    public ArrayList<PropertyBlock> getAllPropertyBlocks() {
        return allPropertyBlocks;
    }
}
