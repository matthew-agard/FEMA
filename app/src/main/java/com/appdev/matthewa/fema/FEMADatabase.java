package com.appdev.matthewa.fema;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {User.class, Center.class, DisasterLocation.class, Donation.class}, version = 1)
public abstract class FEMADatabase extends RoomDatabase {

    private static FEMADatabase INSTANCE;
    public abstract UserDAO userDAO();
    public abstract CenterDAO centerDAO();
    public abstract DisasterLocationDAO disasterLocationsDAO();
    public abstract DonationDAO donationDAO();

    static FEMADatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (FEMADatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FEMADatabase.class,
                            "FEMA-Database").fallbackToDestructiveMigration().build();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public void clearAllTables() {
        // Not utilized
    }
}
