package com.example.music.Video;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

public class Video extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_video);
    }
    private void catchVideo(){
        Uri path= MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String selection=MediaStore.Video.Media.
    }
}