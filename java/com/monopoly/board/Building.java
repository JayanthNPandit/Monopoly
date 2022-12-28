package com.monopoly.board;

public class Building {
    private BuildingTypeEnum buildingType;

    public Building(BuildingTypeEnum buildingType) {
        this.buildingType = buildingType;
    }

    public static Building newHouse() {
        return new Building(BuildingTypeEnum.HOUSE);
    }

    public static Building newApartment() {
        return new Building(BuildingTypeEnum.APARTMENT);
    }

    private BuildingTypeEnum getBuildingType() {
        return buildingType;
    }

    public boolean isHouse() {
        return BuildingTypeEnum.HOUSE.equals(this.getBuildingType());
    }

    public boolean isApartment() {
        return BuildingTypeEnum.APARTMENT.equals(this.getBuildingType());
    }

    @Override
    public String toString() {
        return "Building [buildingType = " + buildingType + "]";
    }

}
