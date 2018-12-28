package com.appdev.matthewa.fema;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"centerName", "disasterCity", "disasterState"})
public class Donation {
    @NonNull private String centerName;
    @NonNull private String disasterCity;
    @NonNull private String disasterState;
    @NonNull private long disasterPostal;
    @NonNull private int cansDonated;
    @NonNull private int clothingDonated;
    @NonNull private int waterBottlesDonated;

    public Donation(@NonNull String centerName, @NonNull String disasterCity, @NonNull String disasterState, @NonNull long disasterPostal, @NonNull int cansDonated, @NonNull int clothingDonated, @NonNull int waterBottlesDonated) {
        this.centerName = centerName;
        this.disasterCity = disasterCity;
        this.disasterState = disasterState;
        this.disasterPostal = disasterPostal;
        this.cansDonated = cansDonated;
        this.clothingDonated = clothingDonated;
        this.waterBottlesDonated = waterBottlesDonated;
    }

    @NonNull
    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(@NonNull String centerName) {
        this.centerName = centerName;
    }

    @NonNull
    public String getDisasterCity() {
        return disasterCity;
    }

    public void setDisasterCity(@NonNull String disasterCity) {
        this.disasterCity = disasterCity;
    }

    @NonNull
    public String getDisasterState() {
        return disasterState;
    }

    public void setDisasterState(@NonNull String disasterState) {
        this.disasterState = disasterState;
    }

    @NonNull
    public long getDisasterPostal() {
        return disasterPostal;
    }

    public void setDisasterPostal(@NonNull long disasterPostal) {
        this.disasterPostal = disasterPostal;
    }

    @NonNull
    public int getCansDonated() {
        return cansDonated;
    }

    public void setCansDonated(@NonNull int cansDonated) {
        this.cansDonated = cansDonated;
    }

    @NonNull
    public int getClothingDonated() {
        return clothingDonated;
    }

    public void setClothingDonated(@NonNull int clothingDonated) {
        this.clothingDonated = clothingDonated;
    }

    @NonNull
    public int getWaterBottlesDonated() {
        return waterBottlesDonated;
    }

    public void setWaterBottlesDonated(@NonNull int waterBottlesDonated) {
        this.waterBottlesDonated = waterBottlesDonated;
    }
}
