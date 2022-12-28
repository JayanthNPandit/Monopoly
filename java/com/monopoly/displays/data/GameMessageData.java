package com.monopoly.displays.data;

public class GameMessageData
{
    private GameMessageTypeEnum messageType;
    private String message;

    public GameMessageData(String message, GameMessageTypeEnum messageType)
    {
        this.setMessageType(messageType);
        this.setMessage(message);
    }

    public String getMessage()
    {
        return message;
    }

    private void setMessage(String message)
    {
        this.message = message;
    }

    public GameMessageTypeEnum getMessageType()
    {
        return messageType;
    }

    private void setMessageType(GameMessageTypeEnum messageType)
    {
        this.messageType = messageType;
    }

    public boolean isMessageToBePreserved()
    {
        return this.messageType.equals(GameMessageTypeEnum.HAPPY_MESSAGE)
                || this.messageType.equals(GameMessageTypeEnum.SAD_MESSAGE);
    }
}
