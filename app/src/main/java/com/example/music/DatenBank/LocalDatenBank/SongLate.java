package com.example.music.DatenBank.LocalDatenBank;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LateSong")
public class SongLate {
@PrimaryKey(autoGenerate = true)
    private int id ;
    private double dauer;
    private String path;
    private String namederSong;
    private int postion;
    private int size;

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

    public SongLate( double dauer, String path, int postion,int size,String namederSong) {

        this.dauer = dauer;
        this.path = path;
        this.postion = postion;
        this.size=size;
        this.namederSong=namederSong;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setNamederSong(String namederSong) {
        this.namederSong = namederSong;
    }

    public String getNamederSong() {
        return namederSong;
    }
}
