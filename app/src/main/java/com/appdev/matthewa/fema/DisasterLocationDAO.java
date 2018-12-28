package com.appdev.matthewa.fema;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface DisasterLocationDAO {
    @Query("SELECT * FROM DisasterLocation WHERE city = :city and postalCode = :postalCode")
    DisasterLocation findLocation(String city, long postalCode);

    @Query("SELECT * FROM DisasterLocation")
    DisasterLocation[] getAllLocations();

    @Insert
    void insertLocation(DisasterLocation... disasterLocations);

    @Update
    void updateLocation(DisasterLocation... disasterLocations);

    @Delete
    void deleteLocation(DisasterLocation... disasterLocations);
}
