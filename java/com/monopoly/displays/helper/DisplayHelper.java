package com.monopoly.displays.helper;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DisplayHelper
{
    // Move this out to environment property set using the java -D option
    private static final boolean DEBUG_FLAG = true;
    private static final boolean INFO_FLAG = true;

    private static final int NUMBER_of_MILLISECONDS_in_SECOND = 1000;
    private static final int NUMBER_of_MILLISECONDS_in_MINUTE = NUMBER_of_MILLISECONDS_in_SECOND * 60;
    private static final int NUMBER_of_MILLISECONDS_in_HOUR = NUMBER_of_MILLISECONDS_in_MINUTE * 60;

    private static final String dateTimeFormatPattern = "MM-dd-yyyy hh:mm:ss a";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateTimeFormatPattern);
    private static final String amountFormatPattern = "###,###";
    private static final DecimalFormat decimalFormat = new DecimalFormat(amountFormatPattern);

    public static String getCurrentTime()
    {
        return simpleDateFormat.format(new Date());
    }

    public static String getFormattedAmount(int amountToDisplay)
    {
        return decimalFormat.format(amountToDisplay);
    }

    public static String getFormattedAmount(int amountToDisplay, int minCharacters)
    {
        String formattedAmount = getFormattedAmount(amountToDisplay);
        int formattedAmountLength = formattedAmount.length();

        if (formattedAmountLength < minCharacters)
        {
            formattedAmount = " ".repeat(minCharacters - formattedAmountLength) + formattedAmount;
        }

        return formattedAmount;
    }

    public static String getPlayTime(final long gameCurrentTime, final long gameStartTime)
    {
        int playHours = 0;
        int playMinutes = 0;
        int playSeconds = 0;

        long gameTimeInMilliseconds = gameCurrentTime - gameStartTime;

        if (gameTimeInMilliseconds > NUMBER_of_MILLISECONDS_in_HOUR)
        {
            playHours = (int) (gameTimeInMilliseconds / NUMBER_of_MILLISECONDS_in_HOUR);

            gameTimeInMilliseconds = gameTimeInMilliseconds - (playHours * NUMBER_of_MILLISECONDS_in_HOUR);
        }

        if (gameTimeInMilliseconds > NUMBER_of_MILLISECONDS_in_MINUTE)
        {
            playMinutes = (int) (gameTimeInMilliseconds / NUMBER_of_MILLISECONDS_in_MINUTE);

            gameTimeInMilliseconds = gameTimeInMilliseconds - (playMinutes * NUMBER_of_MILLISECONDS_in_MINUTE);
        }

        if (gameTimeInMilliseconds > NUMBER_of_MILLISECONDS_in_SECOND)
        {
            playSeconds = (int) (gameTimeInMilliseconds / NUMBER_of_MILLISECONDS_in_SECOND);

            gameTimeInMilliseconds = gameTimeInMilliseconds - (playSeconds * NUMBER_of_MILLISECONDS_in_SECOND);
        }

        return String.format("Play time is %02d:%02d:%02d", playHours, playMinutes, playSeconds);
    }

    public static void debugLog(String message)
    {
        if (DEBUG_FLAG)
        {
            System.out.println(message);
        }
    }

    public static void debugLog(String message, Throwable t)
    {
        if (DEBUG_FLAG)
        {
            System.out.println(message);
            if (t != null)
            {
                t.printStackTrace();
            }
        }
    }

    public static void infoLog(String message)
    {
        if (INFO_FLAG)
        {
            System.out.println(message);
        }
    }
}
