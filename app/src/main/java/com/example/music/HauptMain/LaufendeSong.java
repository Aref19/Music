package com.example.music.HauptMain;

import android.media.MediaPlayer;

public class LaufendeSong {
    private MediaPlayer mediaPlayer;
    private int postion;
    private String path;
    public static LaufendeSong laufendeSong;

    public LaufendeSong() {

    }


    public static LaufendeSong getContext(boolean s){
        if(laufendeSong==null || s){
            laufendeSong=new LaufendeSong();
        }
           return laufendeSong;
    }
    public void LaufendeSong(MediaPlayer mediaPlayer, int postion, String path) {
        this.mediaPlayer = mediaPlayer;
        this.postion = postion;
        this.path = path;

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
}
