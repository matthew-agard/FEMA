package com.appdev.matthewa.fema;

public class DisasterLocation {

    private String city;
    private String state;
    private long postalCode;
    private int numFoodCans;
    private int numClothing;
    private int numWaterBottles;

    public DisasterLocation(String city, String state,long postalCode, int numFoodCans, int numClothing, int numWaterBottles) {
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.numFoodCans = numFoodCans;
        this.numClothing = numClothing;
        this.numWaterBottles = numWaterBottles;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(long postalCode) {
        this.postalCode = postalCode;
    }

    public int getNumFoodCans() {
        return numFoodCans;
    }

    public void setNumFoodCans(int numFoodCans) {
        this.numFoodCans = numFoodCans;
    }

    public int getNumClothing() {
        return numClothing;
    }

    public void setNumClothing(int numClothing) {
        this.numClothing = numClothing;
    }

    public int getNumWaterBottles() {
        return numWaterBottles;
    }

    public void setNumWaterBottles(int numWaterBottles) {
        this.numWaterBottles = numWaterBottles;
    }
}
