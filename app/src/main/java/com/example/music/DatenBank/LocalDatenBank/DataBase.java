package com.example.music.DatenBank.LocalDatenBank;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {SaveThings.class,SongLate.class},version = 3)
public abstract class DataBase extends RoomDatabase {
    private static DataBase INSTANCE;
    public abstract DaoData daoData();

    //avoids creating a second $INSTANCE
    public static synchronized DataBase getInstance(Context contex) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(contex.getApplicationContext(), DataBase.class, "Userselect")
                    .fallbackToDestructiveMigration() //Strg + Q
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }


}
