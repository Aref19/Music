package com.example.music.Start;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.music.Firbase.Fierbase;
import com.example.music.R;

public class Regstieren extends AppCompatActivity {
    Fierbase fierbase;
    EditText pass, email, passwieder;
    EditText[] editTexts;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regstieren);
        fierbase = new Fierbase();
        pass = findViewById(R.id.login_password_input);
        passwieder = findViewById(R.id.login_passwordwider_input);
        email = findViewById(R.id.login_gmail);
        editTexts = new EditText[]{passwieder, pass, email};

    }

    public void rgistieren(View view) {
        if (textinhalt(pass, passwieder)) {
            fierbase.signin(email.getText().toString(), String.valueOf(pass.getText().toString()), this);
        } else {
            Toast.makeText(this,"pass mus be same",Toast.LENGTH_LONG).show();
        }
    }

    private boolean textinhalt(EditText editTexts, EditText editText1) {
        boolean status = false;

        if (String.valueOf(editTexts.getText()).equals(String.valueOf(editText1.getText()))) {

            status = true;
        } else {
            status = false;
        }
        return status;
    }
}
