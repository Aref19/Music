package com.example.music.DatenBank.LocalDatenBank;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LateSong")
public class SongLate {
@PrimaryKey(autoGenerate = true)
    private int id ;
    private double dauer;
    private String path;
    private int postion;

    public int getId() {
        return id;
    }

    public double getDauer() {
        return dauer;
    }

    public String getPath() {
        return path;
    }

    public int getPostion() {
        return postion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SongLate( double dauer, String path, int postion) {

        this.dauer = dauer;
        this.path = path;
        this.postion = postion;
    }

    public void setDauer(double dauer) {
        this.dauer = dauer;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }
}
