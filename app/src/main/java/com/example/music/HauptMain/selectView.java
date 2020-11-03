package com.example.music.HauptMain;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.music.R;

import java.util.ArrayList;

public class selectView extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ListView listView;
    RadioButton iamg1, imag2;
    TextView textColor, buttonCollor;
    Spinner selectbuttonColor, selecttextColor;
    ArrayAdapter<CharSequence> adapter;
    ArrayList<String> songs = new ArrayList<>();
    ArrayAdapter<String> stringArrayAdapter;
    ImageView button1,button2,button3;

    @SuppressLint({"ResourceAsColor", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectview);
        selectbuttonColor = findViewById(R.id.spinner);
        selecttextColor = findViewById(R.id.spinner2);
        listView = findViewById(R.id.userlist);
        spinners(selectbuttonColor);
        spinners(selecttextColor);
        textColor = findViewById(R.id.textcolor);
        selecttextColor.setOnItemSelectedListener(this);
        selectbuttonColor.setOnItemSelectedListener(this);
        button1=findViewById(R.id.stop);
        button2=findViewById(R.id.pause);
        button3=findViewById(R.id.start);
        songs.add("song1");
        songs.add("song2");
        songs.add("song3");


    }

    private void spinners(Spinner spinner) {
        if (spinner.getId() == R.id.spinner) {
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.colorsarra, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            selecttextColor.setAdapter(adapter);
        } else {
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.colorsarrabutton, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            selectbuttonColor.setAdapter(adapter);
        }


    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] colorsname = getResources().getStringArray(R.array.colorsarra);
        String[] colorsColor = {"#FFFFFFFF", "#00FF00", "#FF0000", "#0000FF", "#000000"};

        String colorname = "";
        if (parent.getId() == R.id.spinner) {
            Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[position] + position);

            if (parent.getItemAtPosition(position).equals(colorsname[0])) {
                textColor.setTextColor(Color.parseColor(colorsColor[0]));
                Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[0]);
                stringArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, songs);
                button1.setBackgroundColor(Color.parseColor(colorsname[1]));
            } else if (parent.getItemAtPosition(position).equals(colorsname[1])) {
                textColor.setTextColor(Color.parseColor(colorsColor[1]));
                stringArrayAdapter = new ArrayAdapter<>(this, R.layout.grren, songs);

                Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[1]);
            } else if (parent.getItemAtPosition(position).equals(colorsname[2])) {
                textColor.setTextColor(Color.parseColor(colorsColor[2]));
                stringArrayAdapter = new ArrayAdapter<>(this, R.layout.text, songs);
                Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[2]);
            } else if (parent.getItemAtPosition(position).equals(colorsname[4])) {
                textColor.setTextColor(Color.parseColor(colorsColor[4]));
                Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[3]);
            } else if (parent.getItemAtPosition(position).equals(colorsname[3])) {
                textColor.setTextColor(Color.parseColor(colorsColor[3]));
                stringArrayAdapter = new ArrayAdapter<>(this, R.layout.blau, songs);
                Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[3]);
            }


        } else {
            Log.i("spinn", "onItemSelected: " + parent.getItemAtPosition(position) + "2");
        }
        listView.setAdapter(stringArrayAdapter);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}