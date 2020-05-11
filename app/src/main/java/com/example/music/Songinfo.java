package com.example.music;

public class Songinfo {
    private String path;
    private String song_name;
    private String album_name;
    private String altist;
    public  Songinfo(String path,String song_name,String album_name,String altist){
        this.path=path;
        this.song_name=song_name;
        this.song_name=album_name;
        this.altist=altist;

    }

    public String getAlbum_name() {
        return album_name;
    }

    public String getAltist() {
        return altist;
    }

    public String getPath() {
        return path;
    }

    public String getSong_name() {
        return song_name;
    }

}
