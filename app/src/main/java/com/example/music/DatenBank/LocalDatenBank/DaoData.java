package com.example.music.DatenBank.LocalDatenBank;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DaoData {
    @Insert
    public void image(SaveThings saveThings);
    @Query("select image from Userselect")
    byte [] image();
    @Query("delete from Userselect")
    void deltetable();

}
