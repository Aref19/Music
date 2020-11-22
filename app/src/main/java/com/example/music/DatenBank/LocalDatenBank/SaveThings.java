package com.example.music.DatenBank.LocalDatenBank;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Userselect")
public class SaveThings {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte [] image;
    private String namesong;

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }

    public String getNamesong() {
        return namesong;
    }

    public void setNamesong(String namesong) {
        this.namesong = namesong;
    }
}
