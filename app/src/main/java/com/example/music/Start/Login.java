package com.example.music.Start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.music.Firbase.Fierbase;
import com.example.music.R;

public class Login extends AppCompatActivity {
     Fierbase fierbase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fierbase=new Fierbase();
    }

    public void toRgistieren(View view) {
        Intent intent=new Intent(this,Regstieren.class);
        startActivity(intent);
    }
}