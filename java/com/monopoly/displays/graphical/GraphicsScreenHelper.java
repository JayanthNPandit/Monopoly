package com.monopoly.displays.graphical;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.JFrame;
import com.monopoly.displays.helper.DisplayHelper;

public class GraphicsScreenHelper {
    public static final int MIN_SCREEN_WIDTH_in_PIXELS = 1000;
    public static final int MIN_SCREEN_HEIGHT_in_PIXELS = 1000;

    public static final int BORDER_WIDTH_in_PIXELS = 1;
    public static final int HORIZONTAL_SPACE_in_PIXELS = 5;
    public static final int SPACING_MARGINs_in_PIXELS = 10;
    public static final int TOP_BOTTOM_MARGINs_in_PIXELS = 10;
    public static final int LEFT_RIGHT_MARGINs_in_PIXELS = 10;

    public static final int MIN_BLOCK_WIDTH_in_PIXELS = 120;
    public static final int MIN_BLOCK_HEIGHT_in_PIXELS = 78;

    public static final int GRAPH_X_AXIS_HEIGHT = 25;
    public static final int GRAPH_Y_AXIS_WIDTH = 50;
    public static final int GRAPH_X_AXIS_MARKER_MIN_HORIZONTAL_GAP = 5;
    public static final int GRAPH_X_AXIS_MARKER_HEIGHT = 5;
    public static final int GRAPH_Y_AXIS_MARKER_MIN_VERTICAL_GAP = 20;
    public static final int GRAPH_Y_AXIS_MARKER_WIDTH = 5;
    public static final int GRAPH_X_AXIS_LABEL_VERTICAL_GAP = 5;
    public static final int GRAPH_Y_AXIS_LABEL_HORIZONTAL_GAP = 10;
    public static final int GRAPH_MIN_NUMBER_OF_X_AXIS_MARKERS_TO_DISPLAY = 22;
    public static final int GRAPH_MIN_NUMBER_OF_Y_AXIS_MARKERS_TO_DISPLAY = 15;
    public static final int GRAPH_Y_AXIS_LABEL_DOLLAR_AMOUNT_INCREMENT = 500;

    private static DisplayMode[] BEST_DISPLAY_MODES = {
            new DisplayMode(1920, 1080, 32, DisplayMode.REFRESH_RATE_UNKNOWN),
            new DisplayMode(1280, 1024, 32, DisplayMode.REFRESH_RATE_UNKNOWN),
            new DisplayMode(1024, 768, 32, DisplayMode.REFRESH_RATE_UNKNOWN) };

    private static int windowWidth;
    private static int windowHeight;

    public static JFrame initializeScreen() {
        final GraphicsDevice graphicsDevice = getBestAvailableScreenDevice();

        if (graphicsDevice != null) {
            final GraphicsConfiguration graphicsConfiguration = graphicsDevice.getDefaultConfiguration();

            final JFrame mainFrame = new JFrame(graphicsConfiguration);
            // changeToFullScreenMode(graphicsDevice, mainFrame);
            setFrameSize(mainFrame);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            return mainFrame;
        } else {
            return null;
        }
    }

    /*
     * private static void changeToFullScreenMode(final GraphicsDevice
     * graphicsDevice, final JFrame mainFrame) { boolean fullScreenSupported =
     * graphicsDevice.isFullScreenSupported(); if (fullScreenSupported) {
     * mainFrame.setUndecorated(fullScreenSupported);
     * mainFrame.setResizable((fullScreenSupported == false));
     * graphicsDevice.setFullScreenWindow(mainFrame); } }
     */

    private static void setFrameSize(final JFrame mainFrame) {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final Insets insets = toolkit.getScreenInsets(mainFrame.getGraphicsConfiguration());
        // Calculate the size of the Game Window without the Menu Bar, Windows Task Bar,
        // etc.
        final Dimension maxWindowSize = new Dimension((int) screenSize.getWidth() - insets.left - insets.right,
                (int) screenSize.getHeight() - insets.top - insets.bottom);

        DisplayHelper.debugLog("toolkit.getScreenResolution() = " + toolkit.getScreenResolution());
        DisplayHelper.debugLog("screenSize = " + screenSize);
        DisplayHelper.debugLog("insets = " + insets);
        DisplayHelper.debugLog("maxWindowSize = " + maxWindowSize);

        mainFrame.setMinimumSize(maxWindowSize);
        mainFrame.setMaximumSize(maxWindowSize);
        mainFrame.setPreferredSize(maxWindowSize);
        mainFrame.setSize(maxWindowSize);
        mainFrame.setResizable(false);
        mainFrame.setState(JFrame.MAXIMIZED_BOTH);
        mainFrame.pack();

        // Must call getContentPane() after the pack() method has been called
        final Dimension contentPaneSize = mainFrame.getContentPane().getSize();
        DisplayHelper.debugLog("contentPaneSize = " + contentPaneSize);
        // Must call getContentPane() after the pack() method has been called
        windowWidth = (int) contentPaneSize.getWidth();
        windowHeight = (int) contentPaneSize.getHeight();
    }

    private static GraphicsDevice getDefaultScreenDevice() {
        final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final GraphicsDevice device = env.getDefaultScreenDevice();
        return device;
    }

    private static GraphicsDevice getBestAvailableScreenDevice() {
        final GraphicsDevice graphicsDevice = getDefaultScreenDevice();
        DisplayHelper.debugLog("Default Display Mode: " + graphicsDevice.getDisplayMode());
        if (graphicsDevice.isDisplayChangeSupported()) {
            final DisplayMode bestAvailableDisplayMode = getBestAvailableDisplayMode(graphicsDevice);
            if (bestAvailableDisplayMode != null) {
                graphicsDevice.setDisplayMode(bestAvailableDisplayMode);
                DisplayHelper.debugLog("Choosen Display Mode: " + bestAvailableDisplayMode);
            }
        } else {
            DisplayHelper.debugLog("Display Mode can not be changed!");
        }

        if (checkForMinimumDisplayRequirement(graphicsDevice)) {
            // graphicsDevice has been updated with a display mode that meets the minimum
            // requirements
            return graphicsDevice;
        } else {
            return null;
        }
    }

    private static boolean checkForMinimumDisplayRequirement(final GraphicsDevice graphicsDevice) {
        final DisplayMode defaultDisplayMode = graphicsDevice.getDisplayMode();

        if ((defaultDisplayMode.getWidth() >= MIN_SCREEN_WIDTH_in_PIXELS)
                && (defaultDisplayMode.getHeight() >= MIN_SCREEN_HEIGHT_in_PIXELS)) {
            return true;
        } else {
            return false;
        }
    }

    private static DisplayMode getBestAvailableDisplayMode(final GraphicsDevice device) {
        for (int x = 0; x < BEST_DISPLAY_MODES.length; x++) {
            final DisplayMode[] modes = device.getDisplayModes();
            for (int i = 0; i < modes.length; i++) {
                if ((modes[i].getWidth() == BEST_DISPLAY_MODES[x].getWidth())
                        && (modes[i].getHeight() == BEST_DISPLAY_MODES[x].getHeight())) {
                    return BEST_DISPLAY_MODES[x];
                }
            }
        }
        return null;
    }

    public static int getWindowWidth() {
        return windowWidth;
    }

    public static int getWindowHeight() {
        return windowHeight;
    }

    public static int getScreenLeftMargin() {
        // Take the full width of the screen as the left-most-point
        return getWindowWidth() - getWindowWidth();
    }

    public static int getScreenRightMargin() {
        // Take the full width of the screen as the right-most-point
        return getWindowWidth();
    }

    public static int getScreenTopMargin() {
        // Take the full height of the screen as the top-most-point
        return getWindowHeight() - getWindowHeight();
    }

    public static int getScreenBottomMargin() {
        // Take the full height of the screen as the bottom-most-point
        return getWindowHeight();
    }

    public static int getScreenAvailableWidth() {
        int availableWidth = getScreenRightMargin() - getScreenLeftMargin();

        return availableWidth;
    }

    public static int getScreenAvailableHeight() {
        int availableHeight = getScreenBottomMargin() - getScreenTopMargin();

        return availableHeight;
    }

    public static int getScreenHorizontalCenter() {
        return getWindowWidth() / 2;
    }

    public static int getMessageWidth(String message, Graphics graphics) {
        FontMetrics fontMetrics = graphics.getFontMetrics();
        return fontMetrics.stringWidth(message);
    }

    public static int getMessageHeight(String message, Graphics graphics) {
        FontMetrics fontMetrics = graphics.getFontMetrics();
        return fontMetrics.getHeight() + fontMetrics.getAscent();
    }

    public static String toDisplayString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("screenWidth = " + GraphicsScreenHelper.getWindowWidth() + "\n");
        stringBuilder.append("screenHeight = " + GraphicsScreenHelper.getWindowHeight() + "\n");

        stringBuilder.append("getScreenLeftMargin() = " + GraphicsScreenHelper.getScreenLeftMargin() + "\n");
        stringBuilder.append("getScreenRightMargin() = " + GraphicsScreenHelper.getScreenRightMargin() + "\n");
        stringBuilder.append("getScreenTopMargin() = " + GraphicsScreenHelper.getScreenTopMargin() + "\n");
        stringBuilder.append("getScreenBottomMargin() = " + GraphicsScreenHelper.getScreenBottomMargin() + "\n");

        stringBuilder.append("getScreenAvailableWidth() = " + GraphicsScreenHelper.getScreenAvailableWidth() + "\n");
        stringBuilder.append("getScreenAvailableHeight() = " + GraphicsScreenHelper.getScreenAvailableHeight() + "\n");
        stringBuilder
                .append("getScreenHorizontalCenter() = " + GraphicsScreenHelper.getScreenHorizontalCenter() + "\n");

        return stringBuilder.toString();
    }
}
