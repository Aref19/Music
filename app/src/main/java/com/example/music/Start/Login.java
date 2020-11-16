package com.example.music.Start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.music.Firbase.Fierbase;
import com.example.music.R;

public class Login extends AppCompatActivity {
     Fierbase fierbase;
     EditText email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fierbase=new Fierbase();
        email=findViewById(R.id.login_gmail);
        pass=findViewById(R.id.login_password_input);
    }

    public void toRgistieren(View view) {
        Intent intent=new Intent(this,Regstieren.class);
        startActivity(intent);
    }

    public void anmelden(View view) {
        fierbase.signin(email.getText().toString(). trim(),String.valueOf(pass.getText().toString()),this);
    }
}