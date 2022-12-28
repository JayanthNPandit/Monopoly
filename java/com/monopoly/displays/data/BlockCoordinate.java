package com.monopoly.displays.data;

public class BlockCoordinate
{
    private int rowIndex;
    private int columnIndex;

    public BlockCoordinate(int aRowIndex, int aColumnIndex)
    {
        rowIndex = aRowIndex;
        columnIndex = aColumnIndex;
    }

    public int getRowIndex()
    {
        return rowIndex;
    }

    public int getColumnIndex()
    {
        return columnIndex;
    }
}
