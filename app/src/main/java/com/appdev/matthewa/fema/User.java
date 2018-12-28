package com.appdev.matthewa.fema;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"email"})
public class User {
    @NonNull private String email;
    @NonNull private String password;
    @NonNull private String userType;

    public User(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.password = password;
    }

//    public User(@NonNull String email, @NonNull String password, @NonNull String userType) {
//        this.email = email;
//        this.password = password;
//        this.userType = userType;
//    }

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
    public String getUserType() {
        return userType;
    }

    public void setUserType(@NonNull String userType) {
        this.userType = userType;
    }

}
