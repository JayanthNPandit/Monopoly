package com.monopoly.board;

public enum BuildingTypeEnum
{
    HOUSE("House"), APARTMENT("Apartment");

    private String buildingTypeName;

    private BuildingTypeEnum(String buildingTypeName)
    {
        this.buildingTypeName = buildingTypeName;
    }

    protected String getBuildingTypeName()
    {
        return this.buildingTypeName;
    }
}
