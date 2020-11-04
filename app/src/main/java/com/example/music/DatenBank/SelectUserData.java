package com.example.music.DatenBank;

public class SelectUserData {
    private  String colortext;
    private  String colorButton;
    private  String imgePath;


    public SelectUserData(String colortext, String colorButton, String imgePath) {
        this.colortext = colortext;
        this.colorButton = colorButton;
        this.imgePath = imgePath;
    }

    public String getColortext() {
        return colortext;
    }

    public String getColorButton() {
        return colorButton;
    }

    public String getImgePath() {
        return imgePath;
    }

    public void setColortext(String colortext) {
        this.colortext = colortext;
    }
    public void setColorButton(String colorButton) {
        this.colorButton = colorButton;
    }

    public void setImgePath(String imgePath) {
        this.imgePath = imgePath;
    }
    }
