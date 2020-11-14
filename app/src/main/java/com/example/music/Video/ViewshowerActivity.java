package com.example.music.Video;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.music.R;
public class ViewshowerActivity extends AppCompatActivity {
     VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewshower);
        videoView = findViewById(R.id.videoshower);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Log.i("passs", "onCreate: " + bundle.get("path"));
        videoView.setVideoURI(Uri.parse(bundle.get("path").toString()));
        videoView.start();
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        MyVideoView myVideoView = new MyVideoView(this);
        myVideoView.setVideoSize(videoView.getWidth(),videoView.getHeight());
        //  myVideoView.setVideoURI(Uri.parse(bundle.get("path").toString()));
        // myVideoView.onMeasure(videoView.getWidth(),videoView.getHeight());
    }



}