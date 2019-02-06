package com.appdev.matthewa.fema;

public class Donation {
    private String centerName;
    private String disasterCity;
    private String disasterState;
    private long disasterPostal;
    private int cansDonated;
    private int clothingDonated;
    private int waterBottlesDonated;

    public Donation(String centerName, String disasterCity, String disasterState, long disasterPostal, int cansDonated, int clothingDonated, int waterBottlesDonated) {
        this.centerName = centerName;
        this.disasterCity = disasterCity;
        this.disasterState = disasterState;
        this.disasterPostal = disasterPostal;
        this.cansDonated = cansDonated;
        this.clothingDonated = clothingDonated;
        this.waterBottlesDonated = waterBottlesDonated;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getDisasterCity() {
        return disasterCity;
    }

    public void setDisasterCity(String disasterCity) {
        this.disasterCity = disasterCity;
    }

    public String getDisasterState() {
        return disasterState;
    }

    public void setDisasterState(String disasterState) {
        this.disasterState = disasterState;
    }

    public long getDisasterPostal() {
        return disasterPostal;
    }

    public void setDisasterPostal(long disasterPostal) {
        this.disasterPostal = disasterPostal;
    }

    public int getCansDonated() {
        return cansDonated;
    }

    public void setCansDonated(int cansDonated) {
        this.cansDonated = cansDonated;
    }

    public int getClothingDonated() {
        return clothingDonated;
    }

    public void setClothingDonated(int clothingDonated) {
        this.clothingDonated = clothingDonated;
    }

    public int getWaterBottlesDonated() {
        return waterBottlesDonated;
    }

    public void setWaterBottlesDonated(int waterBottlesDonated) {
        this.waterBottlesDonated = waterBottlesDonated;
    }
}
