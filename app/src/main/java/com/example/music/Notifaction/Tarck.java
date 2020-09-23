package com.example.music.Notifaction;

public class Tarck {
    String titel;
    String artist;
    int image;

    public Tarck(String titel, String artist, int image) {
        this.titel = titel;
        this.artist = artist;
        this.image = image;
    }

    public String getTitel() {
        return titel;
    }

    public String getArtist() {
        return artist;
    }

    public int getImage() {
        return image;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
