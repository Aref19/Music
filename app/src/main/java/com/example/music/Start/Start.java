package com.example.music.Start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.music.DatenBank.SaveInfoUserselect;
import com.example.music.HauptMain.Main;
import com.example.music.HauptMain.Music;
import com.example.music.R;

public class Start extends AppCompatActivity {
    SaveInfoUserselect saveInfoUserselect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        saveInfoUserselect = SaveInfoUserselect.getContext(this);
        therd();
    }

    private void therd() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                    wichActivity();
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }

    protected void wichActivity() {

        saveInfoUserselect = SaveInfoUserselect.getContext(this);
        Intent intent;
        if (saveInfoUserselect.getUseremail(SaveInfoUserselect.User_email).isEmpty()) {

            intent = new Intent(getApplication(), Login.class);

        } else {
            intent = new Intent(this, Main.class);

        }
        startActivity(intent);
    }
}