package com.example.music.Video;

public class VideoModel {

    private  String pathVideo;
    private String name;
    public VideoModel(String pathVideo){
        this.pathVideo=pathVideo;
    }

    public VideoModel() {

    }

    public void setPathVideo(String pathVideo) {
        this.pathVideo = pathVideo;
    }

    public String getPathVideo() {
        return pathVideo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
