package com.monopoly.displays.helper;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;

public class AudioHelper implements LineListener, Runnable {
    private String soundFilename;
    // This flag indicates whether the playback completes or not.
    private boolean playCompleted;
    private Clip audioClip;

    @Override
    public void run() {
        this.playCurrentSound();
    }

    public void playSound(String filename) {
        this.soundFilename = filename;
        SwingUtilities.invokeLater(this);
    }

    private void playCurrentSound() {
        File audioFile = new File(soundFilename);

        System.out.println("About to play: " + soundFilename);

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, format);

            audioClip = (Clip) AudioSystem.getLine(info);

            audioClip.addLineListener(this);

            audioClip.open(audioStream);

            audioClip.start();

            while (!playCompleted) {
                // wait for the playback completes
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported: " + this.soundFilename);
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file: " + this.soundFilename);
            ex.printStackTrace();
        }
    }

    /**
     * Listens to the START and STOP events of the audio line.
     */
    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();

        if (type == LineEvent.Type.START) {
            System.out.println("\t" + DisplayHelper.getCurrentTime() + ": Playback started.");

        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            audioClip.close();
            System.out.println("\t" + DisplayHelper.getCurrentTime() + ": Playback completed.");
        }

    }
}
