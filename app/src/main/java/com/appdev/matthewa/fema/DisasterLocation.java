package com.appdev.matthewa.fema;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"city", "state", "postalCode"})
public class DisasterLocation {

    @NonNull private String city;
    @NonNull private String state;
    @NonNull private long postalCode;
    private int numFoodCans;
    private int numClothing;
    private int numWaterBottles;

    public DisasterLocation(@NonNull String city, @NonNull String state, @NonNull long postalCode, int numFoodCans, int numClothing, int numWaterBottles) {
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.numFoodCans = numFoodCans;
        this.numClothing = numClothing;
        this.numWaterBottles = numWaterBottles;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    public void setCity(@NonNull String city) {
        this.city = city;
    }

    @NonNull
    public String getState() {
        return state;
    }

    public void setState(@NonNull String state) {
        this.state = state;
    }

    @NonNull
    public long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(@NonNull long postalCode) {
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
