package com.example.music.HauptMain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.music.DatenBank.ConvertData;
import com.example.music.DatenBank.LocalDatenBank.DataBase;
import com.example.music.DatenBank.LocalDatenBank.SaveThings;
import com.example.music.DatenBank.SaveInfoUserselect;
import com.example.music.Firbase.Fierbase;
import com.example.music.Firbase.WorkwithFirbase;
import com.example.music.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

public class selectView extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ListView listView;
    RadioButton radio1, radio2;
    TextView textColor, buttonCollor;
    Spinner selecttext, selectbutton;
    ArrayAdapter<CharSequence> adapter;
    ArrayList<String> songs = new ArrayList<>();
    ArrayAdapter<String> stringArrayAdapter;
    ImageView button1, button2, button3, imageView, im;
    LinearLayout linearLayout;
    DataBase dataBase;
    String impath;


    GradientDrawable[] drawables;
    SaveInfoUserselect saveInfoUserselect;

    @SuppressLint({"ResourceAsColor", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       getSupportActionBar().setTitle("Main");
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.selectview);
        imageView = findViewById(R.id.imageView);
        selecttext = findViewById(R.id.spinner);
        selectbutton = findViewById(R.id.spinner2);
        listView = findViewById(R.id.userlist);
        linearLayout = findViewById(R.id.background);
        radio1 = findViewById(R.id.radioButton);
        radio2 = findViewById(R.id.radioButton2);
        spinners(selecttext);
        spinners(selectbutton);
        textColor = findViewById(R.id.textcolor);
        selectbutton.setOnItemSelectedListener(this);
        selecttext.setOnItemSelectedListener(this);
        button1 = findViewById(R.id.stop);
        button2 = findViewById(R.id.pause);
        button3 = findViewById(R.id.start);
        songs.add("song1");
        songs.add("song2");
        songs.add("song3");
        drawables = new GradientDrawable[3];
        drawables[0] = (GradientDrawable) button1.getBackground().mutate();
        drawables[1] = (GradientDrawable) button2.getBackground().mutate();
        drawables[2] = (GradientDrawable) button3.getBackground().mutate();
        saveInfoUserselect = SaveInfoUserselect.getContext(this);
        String i = saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY);
        Uri uri = Uri.parse(i);
        // imageView.setImageURI(uri);
        dataBase = DataBase.getInstance(this);
        im = findViewById(R.id.imageView2);
        //dataBase.daoData().deltetable();
        selecttext.setSelection(saveInfoUserselect.loadspinnerSelection(SaveInfoUserselect.USER_Int_Spinnerindex));
        selectbutton.setSelection(saveInfoUserselect.loadspinnerSelection(SaveInfoUserselect.USER_Intbutton_Spinnerindex));
        //image();


    }

    private void spinners(Spinner spinner) {
        if (spinner.getId() == R.id.spinner) {
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.colorsarra, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            selecttext.setAdapter(adapter);
        } else if (spinner.getId() == R.id.spinner2) {
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
                saveInfoUserselect.saveColorText(SaveInfoUserselect.USER_ColorT_KEY, colorsitems[0]);
            } else if (parent.getItemAtPosition(position).equals(colorsname[1])) {
                textColor.setTextColor(Color.parseColor(colorsitems[1]));
                saveInfoUserselect.savespinnerSelection(SaveInfoUserselect.USER_Int_Spinnerindex,1);
                stringArrayAdapter = new ArrayAdapter<>(this, R.layout.grren, songs);
                saveInfoUserselect.saveColorText(SaveInfoUserselect.USER_ColorT_KEY, colorsitems[1]);

                Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[1]);
            } else if (parent.getItemAtPosition(position).equals(colorsname[2])) {
                textColor.setTextColor(Color.parseColor(colorsitems[2]));
                saveInfoUserselect.savespinnerSelection(SaveInfoUserselect.USER_Int_Spinnerindex,2);
                stringArrayAdapter = new ArrayAdapter<>(this, R.layout.text, songs);
                Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[2]);
                saveInfoUserselect.saveColorText(SaveInfoUserselect.USER_ColorT_KEY, colorsitems[2]);
            } else if (parent.getItemAtPosition(position).equals(colorsname[4])) {
                textColor.setTextColor(Color.parseColor(colorsitems[4]));
                saveInfoUserselect.savespinnerSelection(SaveInfoUserselect.USER_Int_Spinnerindex,4);
                Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[3]);
                saveInfoUserselect.saveColorText(SaveInfoUserselect.USER_ColorT_KEY, colorsitems[4]);
            } else if (parent.getItemAtPosition(position).equals(colorsname[3])) {
                textColor.setTextColor(Color.parseColor(colorsitems[3]));
                saveInfoUserselect.savespinnerSelection(SaveInfoUserselect.USER_Int_Spinnerindex,4);
                stringArrayAdapter = new ArrayAdapter<>(this, R.layout.blau, songs);
                Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[3]);
                saveInfoUserselect.saveColorText(SaveInfoUserselect.USER_ColorT_KEY, colorsitems[3]);
            }


        } else {
            if (parent.getItemAtPosition(position).equals(colorsnameofButton[0])) {
                drawables[0].setColor(Color.parseColor(colorsButton[0]));
                drawables[1].setColor(Color.parseColor(colorsButton[0]));
                drawables[2].setColor(Color.parseColor(colorsButton[0]));
                saveInfoUserselect.saveColorButton(SaveInfoUserselect.USER_ColorB_KEY, colorsButton[0]);
                /*
                button1.setBackgroundColor(Color.parseColor(colorsButton[0]));
                button2.setBackgroundColor(Color.parseColor(colorsButton[0]));
                button3.setBackgroundColor(Color.parseColor(colorsButton[0]));

                 */
            } else if (parent.getItemAtPosition(position).equals(colorsnameofButton[1])) {
                saveInfoUserselect.savesbuttonSelection(SaveInfoUserselect.USER_Intbutton_Spinnerindex,1);
                drawables[0].setColor(Color.parseColor(colorsButton[1]));
                drawables[1].setColor(Color.parseColor(colorsButton[1]));
                drawables[2].setColor(Color.parseColor(colorsButton[1]));
                saveInfoUserselect.saveColorButton(SaveInfoUserselect.USER_ColorB_KEY, colorsButton[1]);
                /*
                button3.setBackgroundColor(Color.parseColor(colorsButton[1]));
                button2.setBackgroundColor(Color.parseColor(colorsButton[1]));
                button1.setBackgroundColor(Color.parseColor(colorsButton[1]));

                 */

                Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[1]);
            } else if (parent.getItemAtPosition(position).equals(colorsnameofButton[2])) {
                saveInfoUserselect.savesbuttonSelection(SaveInfoUserselect.USER_Intbutton_Spinnerindex,2);
                drawables[0].setColor(Color.parseColor(colorsButton[2]));
                drawables[1].setColor(Color.parseColor(colorsButton[2]));
                drawables[2].setColor(Color.parseColor(colorsButton[2]));
                saveInfoUserselect.saveColorButton(SaveInfoUserselect.USER_ColorB_KEY, colorsButton[2]);
                /*
                button3.setBackgroundColor(Color.parseColor(colorsButton[2]));
                button2.setBackgroundColor(Color.parseColor(colorsButton[2]));
                button1.setBackgroundColor(Color.parseColor(colorsButton[2]));

                 */
            } else if (parent.getItemAtPosition(position).equals(colorsnameofButton[4])) {
                saveInfoUserselect.savesbuttonSelection(SaveInfoUserselect.USER_Intbutton_Spinnerindex,4);
                drawables[0].setColor(Color.parseColor(colorsButton[4]));
                drawables[1].setColor(Color.parseColor(colorsButton[4]));
                drawables[2].setColor(Color.parseColor(colorsButton[4]));
                saveInfoUserselect.saveColorButton(SaveInfoUserselect.USER_ColorB_KEY, colorsButton[4]);
                /*
                button3.setBackgroundColor(Color.parseColor(colorsButton[4]));
                button2.setBackgroundColor(Color.parseColor(colorsButton[4]));
                button1.setBackgroundColor(Color.parseColor(colorsButton[4]));

                 */
            } else if (parent.getItemAtPosition(position).equals(colorsnameofButton[3])) {
                saveInfoUserselect.savesbuttonSelection(SaveInfoUserselect.USER_Intbutton_Spinnerindex,3);
                drawables[0].setColor(Color.parseColor(colorsButton[3]));
                drawables[2].setColor(Color.parseColor(colorsButton[3]));
                drawables[1].setColor(Color.parseColor(colorsButton[3]));
                saveInfoUserselect.saveColorButton(SaveInfoUserselect.USER_ColorB_KEY, colorsButton[3]);
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

        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        //  intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        // intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            //   imageView.setImageURI(uri);
            //  SaveThings saveThings=new SaveThings();
            // saveThings.setImage(ConvertData.imageViewToBtamp(imageView));
            // dataBase.daoData().image(saveThings);
            // saveInfoUserselect.saveURIImage(SaveInfoUserselect.USER_Image_KEY, Uri.decode(uri.toString()));
            Log.i("Path1", "onActivityResult: " + uri);
            String[] file = {MediaStore.MediaColumns.DATA};

            Cursor cursor = managedQuery(uri, file, null, null, null);
            Log.i("Path1", "onActivityResult: " + cursor);
            cursor.moveToFirst();

            int index = cursor.getColumnIndex(file[0]);
            Log.i("Path2", "onActivityResult: " + index);
            impath = cursor.getString(index);
            Log.i("Path3", "onActivityResult: " + impath);
            saveInfoUserselect.saveURIImage(SaveInfoUserselect.USER_Image_KEY, Uri.decode(impath));
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






/*
    private void image(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
//premistion not granted
                String[]premtion={Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(premtion,1001);
            }else {
//premistion not granted
                pickimage();
            }
        }else {

        }
    }

 */

    private void pickimage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imageView.setImageURI(Uri.parse(saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY)));

    }
    //handle result of runtime permission

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1001: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickimage();
                } else {

                }
            }
        }
    }


    public void im(View view) {
        imageView.setImageBitmap(BitmapFactory.decodeFile(saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY)));
        Log.i("imagefile", "im: " + saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY));

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void Radioclick(View view) {
        String uri;
        boolean check = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButton:

                uri = "android.resource://" + getPackageName() + "/" + R.drawable.n;
                saveInfoUserselect.saveURIImage(SaveInfoUserselect.USER_Image_KEY, "R.drawable.n");
                linearLayout.setBackgroundResource(R.drawable.n);
                break;
            case R.id.radioButton2:
                uri = "android.resource://" + getOpPackageName() + "/" + R.drawable.app;
                saveInfoUserselect.saveURIImage(SaveInfoUserselect.USER_Image_KEY,"R.drawable.app");
                linearLayout.setBackgroundResource(R.drawable.app);

                break;

        }
    }
}