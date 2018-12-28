package com.appdev.matthewa.fema;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"name"})
public class Center {
    @NonNull private String email;
    @NonNull private String password;
    @NonNull private String name;
    @NonNull private String city;
    @NonNull private String state;
    @NonNull private long postalCode;

    public Center(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.password = password;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
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
}

