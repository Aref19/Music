package com.example.music.HauptMain;

import android.media.MediaPlayer;

public class LaufendeSong {
    private MediaPlayer mediaPlayer;
    private int postion;
    private String path;
    private int size;
    private Songinfo songinfo;
    public static LaufendeSong laufendeSong;


    public LaufendeSong() {

    }


    public static LaufendeSong getContext(boolean s){
        if(laufendeSong==null || s){
            laufendeSong=new LaufendeSong();
        }
           return laufendeSong;
    }
    public void LaufendeSong(MediaPlayer mediaPlayer, int postion, String path,Songinfo songinfo,int size) {
        this.mediaPlayer = mediaPlayer;
        this.postion = postion;
        this.path = path;
        this.songinfo=songinfo;
        this.size=size;

    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public int getPostion() {
        return postion;
    }

    public String getPath() {
        return path;
    }

    public static LaufendeSong getLaufendeSong() {
        return laufendeSong;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static void setLaufendeSong(LaufendeSong laufendeSong) {
        LaufendeSong.laufendeSong = laufendeSong;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public Songinfo getSonginfo() {
        return songinfo;
    }

    public void setSonginfo(Songinfo songinfo) {
        this.songinfo = songinfo;
    }
}
