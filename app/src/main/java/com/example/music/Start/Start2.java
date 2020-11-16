package com.example.music.Start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.example.music.DatenBank.SaveInfoUserselect;
import com.example.music.HauptMain.Music;

public class Start2 extends AppCompatActivity {
    SaveInfoUserselect saveInfoUserselect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        saveInfoUserselect=SaveInfoUserselect.getContext(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        saveInfoUserselect=SaveInfoUserselect.getContext(this);
        Intent intent;
        if(!saveInfoUserselect.saveUseremail(SaveInfoUserselect.User_email).isEmpty()){
            intent=new Intent(this,Music.class);
            startActivity(intent);
        }else {

        }
    }
    private void userExctitet(){

    }
}