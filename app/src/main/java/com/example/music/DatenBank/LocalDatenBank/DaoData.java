package com.example.music.DatenBank.LocalDatenBank;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoData {
    @Insert
  //  public void insert(SaveThings saveThings);
    public void insertSong(SongLate songLate);
    @Query("select image from Userselect")
    byte [] image();
    @Query("delete from Userselect")
    void deltetable();
    @Query("select * from Userselect")
    List<SaveThings> getlist();
    @Query("select * from LateSong")
    SongLate getSongList();
    @Query("delete from LateSong")
    void deltetableSong();

}
