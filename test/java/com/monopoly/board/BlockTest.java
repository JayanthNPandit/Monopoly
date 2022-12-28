package com.monopoly.board;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.monopoly.player.Player;

import org.junit.Test;

public class BlockTest {
    @Test
    public void testOnePlayerOnBlock() {
        Block testBlock = new LandBlock(50);
        testBlock.setBlockName("test block");
        Player testPlayer = new Player("test", 9);

        testBlock.addPlayerToBlock(testPlayer);

        assertTrue(testBlock.isPlayerInThisBlock(testPlayer.getPlayerNumber()));
        testBlock.removePlayerFromBlock(testPlayer);
        assertFalse(testBlock.isPlayerInThisBlock(testPlayer.getPlayerNumber()));
    }

    @Test
    public void testManyPlayerOnBlock() {
        Block testBlock = new LandBlock(50);
        testBlock.setBlockName("test block");
        Player testPlayer = new Player("test", 9);
        Player dh = new Player("dh", 45);
        Player gdrf = new Player("gdrf", 19);
        Player sdg = new Player("sdg", 39);

        testBlock.addPlayerToBlock(testPlayer);
        testBlock.addPlayerToBlock(dh);
        testBlock.addPlayerToBlock(gdrf);
        testBlock.addPlayerToBlock(sdg);

        assertTrue(testBlock.isPlayerInThisBlock(testPlayer.getPlayerNumber()));
        assertTrue(testBlock.isPlayerInThisBlock(dh.getPlayerNumber()));
        assertTrue(testBlock.isPlayerInThisBlock(gdrf.getPlayerNumber()));
        assertTrue(testBlock.isPlayerInThisBlock(sdg.getPlayerNumber()));
        testBlock.removePlayerFromBlock(testPlayer);
        testBlock.removePlayerFromBlock(dh);
        testBlock.removePlayerFromBlock(gdrf);
        testBlock.removePlayerFromBlock(sdg);
        assertFalse(testBlock.isPlayerInThisBlock(testPlayer.getPlayerNumber()));
        assertFalse(testBlock.isPlayerInThisBlock(dh.getPlayerNumber()));
        assertFalse(testBlock.isPlayerInThisBlock(gdrf.getPlayerNumber()));
        assertFalse(testBlock.isPlayerInThisBlock(sdg.getPlayerNumber()));
    }

    @Test
    public void testNoPlayerOnBlock() {
        Block testBlock = new LandBlock(50);
        testBlock.setBlockName("test block");

        assertFalse(testBlock.isPlayerInThisBlock(9));
    }
}
