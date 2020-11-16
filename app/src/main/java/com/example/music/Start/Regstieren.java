package com.example.music.Start;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.music.Firbase.Fierbase;
import com.example.music.R;

public class Regstieren extends AppCompatActivity {
    Fierbase fierbase;
    EditText pass,email,passwieder;
    EditText [] editTexts;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regstieren);
        fierbase=new Fierbase();
        pass=findViewById(R.id.login_password_input);
        passwieder=findViewById(R.id.login_passwordwider_input);
        email=findViewById(R.id.login_gmail);
        editTexts= new EditText[]{passwieder, pass, email};

    }

    public void rgistieren(View view) {
     //  if(textinhalt(editTexts)){
          fierbase.signin(email.getText().toString(),String.valueOf(pass.getText().toString()),this);
      // }
    }
    private boolean textinhalt(EditText [] editTexts){
        boolean status=false;
        for (int i=0 ;i<editTexts.length;i++){

            if(!editTexts[i].getText().equals("")){
                editTexts[i].setText(String.valueOf("Ops *_*"));
                editTexts[i].setTextColor(Color.parseColor("#FF0000"));
                status=false;
            }else {
                status=true;
            }
        }

        return status;
    }
}