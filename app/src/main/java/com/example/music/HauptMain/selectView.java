package com.example.music.HauptMain;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.music.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

public class selectView extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ListView listView;
    RadioButton iamg1, imag2;
    TextView textColor, buttonCollor;
    Spinner selecttext, selectbutton;
    ArrayAdapter<CharSequence> adapter;
    ArrayList<String> songs = new ArrayList<>();
    ArrayAdapter<String> stringArrayAdapter;
    ImageView button1,button2,button3;
    LinearLayout linearLayout;

    GradientDrawable []drawables;

    @SuppressLint({"ResourceAsColor", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectview);
        selecttext = findViewById(R.id.spinner);
        selectbutton = findViewById(R.id.spinner2);
        listView = findViewById(R.id.userlist);
        linearLayout=findViewById(R.id.background);
        spinners(selecttext);
        spinners(selectbutton);
        textColor = findViewById(R.id.textcolor);
        selectbutton.setOnItemSelectedListener(this);
        selecttext.setOnItemSelectedListener(this);
        button1=findViewById(R.id.stop);
        button2=findViewById(R.id.pause);
        button3=findViewById(R.id.start);
        songs.add("song1");
        songs.add("song2");
        songs.add("song3");
        drawables=new GradientDrawable[3];
        drawables[0]=(GradientDrawable) button1.getBackground().mutate();
        drawables[1]=(GradientDrawable) button2.getBackground().mutate();
        drawables[2]=(GradientDrawable) button3.getBackground().mutate();




    }

    private void spinners(Spinner spinner) {
        if (spinner.getId() == R.id.spinner) {
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.colorsarra, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            selecttext.setAdapter(adapter);
        } else if(spinner.getId() == R.id.spinner2){
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.colorsarrabutton, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            selectbutton.setAdapter(adapter);
        }


    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] colorsname = getResources().getStringArray(R.array.colorsarra);
        String[] colorsnameofButton = getResources().getStringArray(R.array.colorsarrabutton);

        String[] colorsButton = {"#2FD29F", "#2F519F", "#BE519F", "#E4D29F", "#5C8AFF"};
        String[] colorsitems = {"#FFFFFFFF", "#00FF00", "#FF0000", "#0000FF", "#000000"};

        String colorname = "";
        if (parent.getId() == R.id.spinner) {
            Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[position] + position);

            if (parent.getItemAtPosition(position).equals(colorsname[0])) {
                textColor.setTextColor(Color.parseColor(colorsitems[0]));
                Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[0]);
                stringArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, songs);

            } else if (parent.getItemAtPosition(position).equals(colorsname[1])) {
                textColor.setTextColor(Color.parseColor(colorsitems[1]));
                stringArrayAdapter = new ArrayAdapter<>(this, R.layout.grren, songs);

                Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[1]);
            } else if (parent.getItemAtPosition(position).equals(colorsname[2])) {
                textColor.setTextColor(Color.parseColor(colorsitems[2]));
                stringArrayAdapter = new ArrayAdapter<>(this, R.layout.text, songs);
                Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[2]);
            } else if (parent.getItemAtPosition(position).equals(colorsname[4])) {
                textColor.setTextColor(Color.parseColor(colorsitems[4]));
                Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[3]);
            } else if (parent.getItemAtPosition(position).equals(colorsname[3])) {
                textColor.setTextColor(Color.parseColor(colorsitems[3]));
                stringArrayAdapter = new ArrayAdapter<>(this, R.layout.blau, songs);
                Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[3]);
            }


        } else {
            if (parent.getItemAtPosition(position).equals(colorsnameofButton[0])) {
                drawables[0].setColor(Color.parseColor(colorsButton[0]));
                drawables[1].setColor(Color.parseColor(colorsButton[0]));
                drawables[2].setColor(Color.parseColor(colorsButton[0]));
                /*
                button1.setBackgroundColor(Color.parseColor(colorsButton[0]));
                button2.setBackgroundColor(Color.parseColor(colorsButton[0]));
                button3.setBackgroundColor(Color.parseColor(colorsButton[0]));

                 */
            } else if (parent.getItemAtPosition(position).equals(colorsnameofButton[1])) {
                drawables[0].setColor(Color.parseColor(colorsButton[1]));
                drawables[1].setColor(Color.parseColor(colorsButton[1]));
                drawables[2].setColor(Color.parseColor(colorsButton[1]));
                /*
                button3.setBackgroundColor(Color.parseColor(colorsButton[1]));
                button2.setBackgroundColor(Color.parseColor(colorsButton[1]));
                button1.setBackgroundColor(Color.parseColor(colorsButton[1]));

                 */

                Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[1]);
            } else if (parent.getItemAtPosition(position).equals(colorsnameofButton[2])) {
               drawables[0].setColor(Color.parseColor(colorsButton[2]));
                drawables[1].setColor(Color.parseColor(colorsButton[2]));
                drawables[2].setColor(Color.parseColor(colorsButton[2]));
                /*
                button3.setBackgroundColor(Color.parseColor(colorsButton[2]));
                button2.setBackgroundColor(Color.parseColor(colorsButton[2]));
                button1.setBackgroundColor(Color.parseColor(colorsButton[2]));

                 */
            } else if (parent.getItemAtPosition(position).equals(colorsnameofButton[4])) {
                drawables[0].setColor(Color.parseColor(colorsButton[4]));
                drawables[1].setColor(Color.parseColor(colorsButton[4]));
                drawables[2].setColor(Color.parseColor(colorsButton[4]));
                /*
                button3.setBackgroundColor(Color.parseColor(colorsButton[4]));
                button2.setBackgroundColor(Color.parseColor(colorsButton[4]));
                button1.setBackgroundColor(Color.parseColor(colorsButton[4]));

                 */
            } else if (parent.getItemAtPosition(position).equals(colorsnameofButton[3])) {
                drawables[0].setColor(Color.parseColor(colorsButton[3]));
                drawables[2].setColor(Color.parseColor(colorsButton[3]));
                drawables[1].setColor(Color.parseColor(colorsButton[3]));
                /*
                button3.setBackgroundColor(Color.parseColor(colorsButton[3]));
                button2.setBackgroundColor(Color.parseColor(colorsButton[3]));
                button1.setBackgroundColor(Color.parseColor(colorsButton[3]));

                 */

            }
        }
        listView.setAdapter(stringArrayAdapter);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void imageselect(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            Uri uri = data.getData();
            Log.i("Path", "onActivityResult: " + uri);
            Drawable bg = null;
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(uri);
                Log.i("inputS", "onActivityResult: " + inputStream);
                bg = Drawable.createFromStream(inputStream, uri.toString());

            } catch (FileNotFoundException e) {
                linearLayout.setBackgroundResource(R.drawable.n);
            }
            linearLayout.setBackground(bg);
        }
       

    }
}