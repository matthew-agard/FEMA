package com.appdev.matthewa.fema;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM User WHERE userType = 'Agent' AND email = :email AND password = :password")
    User findAgentLogin(String email, String password);

    @Query("SELECT * FROM User WHERE userType = 'Driver' AND email = :email AND password = :password")
    User findDriverLogin(String email, String password);

    @Insert
    void insertUser(User... users);
}
