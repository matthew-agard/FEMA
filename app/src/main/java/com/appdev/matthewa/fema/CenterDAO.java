package com.appdev.matthewa.fema;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface CenterDAO {
    @Query("SELECT * FROM Center")
    Center[] findAllCenters();

    @Query("SELECT * FROM Center WHERE email = :email AND password = :password")
    Center findCenterLogin(String email, String password);

    @Insert
    void insertCenter(Center... centers);
}

