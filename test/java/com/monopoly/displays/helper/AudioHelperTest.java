package com.monopoly.displays.helper;

import org.junit.Test;

public class AudioHelperTest {
    @Test
    public void testPlaySound() {
        new AudioHelper().playSound("./src/main/resources/coin.wav");
        new AudioHelper().playSound("./src/main/resources/jump.wav");
        new AudioHelper().playSound("./src/main/resources/hit.wav");
        new AudioHelper().playSound("./src/main/resources/music.wav");
    }
}
