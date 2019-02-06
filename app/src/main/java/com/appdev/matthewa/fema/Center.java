package com.appdev.matthewa.fema;

public class Center {
    private String username;
    private String password;
    private String centerName;
    private String city;
    private String state;
    private long postalCode;
    private int inventoryFood;
    private int inventoryWater;
    private int inventoryClothing;

    public Center(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
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

    public int getInventoryFood() {
        return inventoryFood;
    }

    public void setInventoryFood(int inventoryFood) {
        this.inventoryFood = inventoryFood;
    }

    public int getInventoryWater() {
        return inventoryWater;
    }

    public void setInventoryWater(int inventoryWater) {
        this.inventoryWater = inventoryWater;
    }

    public int getInventoryClothing() {
        return inventoryClothing;
    }

    public void setInventoryClothing(int inventoryClothing) {
        this.inventoryClothing = inventoryClothing;
    }
}