package com.monopoly.board;

import java.util.ArrayList;

import com.monopoly.gameplay.Dice;
import com.monopoly.gameplay.GameSettings;

public class LandBlock extends PropertyBlock {
    private ArrayList<Building> buildings = new ArrayList<Building>();

    public LandBlock(int aBlockNumber) {
        super(aBlockNumber);
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public boolean purchaseHouse() {
        if (isHousePurchaseAllowed()) {
            Building newHouse = this.addHouse();
            return newHouse != null;
        } else {
            return false;
        }
    }

    public boolean isHousePurchaseAllowed() {
        return this.getNumberOfHouses() < GameSettings.getMaxHousesPerBlock();
    }

    public boolean sellHouse() {
        if (this.getNumberOfHouses() > 0) {
            Building soldHouse = this.removeHouse();
            return soldHouse != null;
        } else {
            return false;
        }
    }

    public int getNumberOfHouses() {
        int numberOfHouses = 0;

        for (Building building : this.getBuildings()) {
            if (building.isHouse()) {
                numberOfHouses++;
            }
        }

        return numberOfHouses;
    }

    public int calculateTotalHouseValue() {
        return this.getNumberOfHouses() * super.getBlockHousePurchaseCost();
    }

    private Building addHouse() {
        Building newHouse = Building.newHouse();

        this.getBuildings().add(newHouse);

        return newHouse;
    }

    private Building removeHouse() {
        for (Building building : this.getBuildings()) {
            if (building.isHouse()) {
                this.getBuildings().remove(building);
                return building;
            }
        }

        return null;
    }

    public boolean purchaseApartment() {
        if (isApartmentPurchaseAllowed()) {
            Building newApartment = this.addApartment();
            return newApartment != null;
        } else {
            return false;
        }
    }

    public boolean isApartmentPurchaseAllowed() {
        return this.getNumberOfApartments() < GameSettings.getMaxApartmentsPerBlock();
    }

    public boolean sellApartment() {
        if (this.getNumberOfApartments() > 0) {
            Building soldApartment = this.removeApartment();
            return soldApartment != null;
        } else {
            return false;
        }
    }

    public int getNumberOfApartments() {
        int numberOfApartments = 0;

        for (Building building : this.getBuildings()) {
            if (building.isApartment()) {
                numberOfApartments++;
            }
        }

        return numberOfApartments;
    }

    public int calculateTotalApartmentValue() {
        return this.getNumberOfApartments() * super.getBlockApartmentPurchaseCost();
    }

    private Building addApartment() {
        Building newApartment = Building.newApartment();

        this.getBuildings().add(newApartment);

        return newApartment;
    }

    private Building removeApartment() {
        for (Building building : this.getBuildings()) {
            if (building.isApartment()) {
                this.getBuildings().remove(building);
                return building;
            }
        }

        return null;
    }

    @Override
    public int calculateRent(Dice dice) {
        int numHouses = this.getNumberOfHouses();
        int numApartments = this.getNumberOfApartments();
        int landRent = super.getBlockLandRent();
        int houseRent = super.getBlockHouseRent();
        int apartmentRent = super.getBlockApartmentRent();

        int totalRent = 0;
        if ((numHouses == 0) && (numApartments == 0)) {
            totalRent = landRent;
        } else {
            totalRent = landRent + (numHouses * houseRent) + (numApartments * apartmentRent);
        }

        return totalRent;
    }
}