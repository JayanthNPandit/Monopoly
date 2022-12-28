package com.monopoly.displays.helper;

import java.security.InvalidParameterException;

public class GeneralHelper
{
    private static final int Num_MiliSeconds_in_One_Second = 1000;

    public static void pause(float numSeconds)
    {
        try
        {
            Thread.sleep((int) (numSeconds * Num_MiliSeconds_in_One_Second));
        }
        catch ( InterruptedException e )
        {
            // Ignore exception - worst case scenario the "pause" didn't happen for the full time requested
        }
    }

    public static int randomInt(int minValue, int maxValue)
    {
        int randomValue = (int) (Math.random() * maxValue);

        if (randomValue < minValue)
        {
            return minValue;
        }
        else if (randomValue > maxValue)
        {
            return maxValue;
        }
        else
        {
            return randomValue;
        }
    }

    public static boolean randomTrueOrFalse()
    {
        // 50% chance of "true" and 50% chance of "false"
        if (Math.random() > 0.5f)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean randomTrueOrFalse(float percentageOfTrue)
    {
        if (percentageOfTrue > 1)
        {
            return true;
        }
        else
        {
            // Chance of "true" is based on percentageOfTrue
            if (Math.random() > (1 - percentageOfTrue))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public static String nCopyOfString(String message, int numberOfTimesToCopy)
    {
        String copiedString = "";

        for (int x = 1; x <= numberOfTimesToCopy; x++)
        {
            copiedString = copiedString + message;
        }

        return copiedString;
    }


    /**
     * This method will return a String that is exactly matching the requiredTextLength. If the original
     * text is less than this length then it will be LEFT-padded with empty spaces. For example, if
     * required length is 5 and the original String is "123" then the returned String will be " 123".
     * 
     * If the originalText is greater than the requiredTextLength then an InvalidParameterException is
     * thrown
     */
    public static String leftPadText(String originalText, int requiredTextLength)
    {
        String formattedText = "";
        int originalTextLength = originalText.length();

        if (originalTextLength == requiredTextLength)
        {
            formattedText = originalText;
        }
        else if (originalTextLength < requiredTextLength)
        {
            int fillWithNumberOfSpaces = requiredTextLength - originalTextLength;
            for (int spaceCounter = 1; spaceCounter <= fillWithNumberOfSpaces; spaceCounter++)
            {
                formattedText = formattedText + " ";
            }
            formattedText = formattedText + originalText;
        }
        else if (originalTextLength > requiredTextLength)
        {
            throw new InvalidParameterException("Required Text Length is " + requiredTextLength + " but the length of '"
                    + originalText + "' is longer than that!");
        }

        return formattedText;
    }

    /**
     * This method will return a String that is exactly matching the requiredTextLength. If the original
     * text is less than this length then it will be RIGHT-padded with empty spaces. For example, if
     * required length is 5 and the original String is "123" then the returned String will be "123 ".
     * 
     * If the originalText is greater than the requiredTextLength then an InvalidParameterException is
     * thrown
     */
    public static String rightPadText(String originalText, int requiredTextLength)
    {
        String formattedText = "";
        int originalTextLength = originalText.length();

        if (originalTextLength == requiredTextLength)
        {
            formattedText = originalText;
        }
        else if (originalTextLength < requiredTextLength)
        {
            int fillWithNumberOfSpaces = requiredTextLength - originalTextLength;
            for (int spaceCounter = 1; spaceCounter <= fillWithNumberOfSpaces; spaceCounter++)
            {
                formattedText = formattedText + " ";
            }
            formattedText = originalText + formattedText;
        }
        else if (originalTextLength > requiredTextLength)
        {
            throw new InvalidParameterException("Required Text Length is " + requiredTextLength + " but the length of '"
                    + originalText + "' is longer than that!");
        }

        return formattedText;
    }

    public static String trimToMaxLength(String originalText, int maxTextLengthAllowed)
    {
        String trimmedText = "";

        if ((originalText != null) && (originalText.length() > maxTextLengthAllowed))
        {
            trimmedText = originalText.substring(0, maxTextLengthAllowed);
        }
        else
        {
            trimmedText = originalText;
        }

        return trimmedText;
    }
}
