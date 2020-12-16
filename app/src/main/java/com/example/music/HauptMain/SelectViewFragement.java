package com.example.music.HauptMain;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.music.DatenBank.SaveInfoUserselect;
import com.example.music.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class SelectViewFragement extends Fragment implements View.OnClickListener  {
    View veiw;
    RadioButton radioButton1, radioButton2;
    Spinner spinnerT, spinnerB;
    ListView listView;
    ImageView imageView,button2;
    LinearLayout linearLayout;
    ArrayAdapter adapter;
    ArrayAdapter<String> stringArrayAdapter;
    ArrayList<String> songs = new ArrayList<>();
    SaveInfoUserselect saveInfoUserselect;
    TextView textColor;
    GradientDrawable drawables[];
    ImageButton button1,button3;
    Button selctimage;
    String impath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        veiw = inflater.inflate(R.layout.selectviewuser, container, false);
        imageView = veiw.findViewById(R.id.imageView);
        spinnerT = veiw.findViewById(R.id.spinnerT);
        spinnerB = veiw.findViewById(R.id.spinnerB);
        listView = veiw.findViewById(R.id.userlist);
        linearLayout = veiw.findViewById(R.id.backgrounduser);
        radioButton1 = veiw.findViewById(R.id.radioButton1);
        radioButton2 = veiw.findViewById(R.id.radioButton2);
        selctimage=veiw.findViewById(R.id.selectimageUser);
        button1 = veiw.findViewById(R.id.stop);
        button2 = veiw.findViewById(R.id.pause);
        button3 = veiw.findViewById(R.id.start);
        textColor = veiw.findViewById(R.id.textcolor);
        songs.add("song1");
        songs.add("song2");
        songs.add("song3");
        drawables = new GradientDrawable[3];
        drawables[0] = (GradientDrawable) button1.getBackground().mutate();
        drawables[1] = (GradientDrawable) button2.getBackground().mutate();
        drawables[2] = (GradientDrawable) button3.getBackground().mutate();
        spinners(spinnerT);
        spinners(spinnerB);
        saveInfoUserselect=SaveInfoUserselect.getContext(veiw.getContext());
        spinnerT.setSelection(saveInfoUserselect.loadspinnerSelection(SaveInfoUserselect.USER_Int_Spinnerindex));
        spinnerB.setSelection(saveInfoUserselect.loadspinnerSelection(SaveInfoUserselect.USER_Intbutton_Spinnerindex));
        userImage(selctimage);
        SpinnerT();
        SpinnerB();


        return veiw;
    }
    private void spinners(Spinner spinner) {
        if (spinner.getId() == R.id.spinnerT) {
            adapter = ArrayAdapter.createFromResource(veiw.getContext(),
                    R.array.colorsarra, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerT.setAdapter(adapter);
        } else if (spinner.getId() == R.id.spinnerB) {
            adapter = ArrayAdapter.createFromResource(veiw.getContext(),
                    R.array.colorsarrabutton, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerB.setAdapter(adapter);
        }


    }

    public void SpinnerB() {

       spinnerB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String[] colorsname = getResources().getStringArray(R.array.colorsarra);
               String[] colorsnameofButton = getResources().getStringArray(R.array.colorsarrabutton);
               String[] colorsButton = {"#2FD29F", "#2F519F", "#BE519F", "#E4D29F", "#5C8AFF"};
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

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });



    }
    private void SpinnerT(){
        spinnerT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] colorsname = getResources().getStringArray(R.array.colorsarra);
                Log.i("select", "onItemSelected: "+"select");
                String[] colorsButton = {"#2FD29F", "#2F519F", "#BE519F", "#E4D29F", "#5C8AFF"};
                String[] colorsitems = {"#FFFFFFFF", "#00FF00", "#FF0000", "#0000FF", "#000000"};
                if (parent.getItemAtPosition(position).equals(colorsname[0])) {
                    Log.i("select", "onItemSelected: "+"select");
                    textColor.setTextColor(Color.parseColor(colorsitems[0]));
                    Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[0]);
                    stringArrayAdapter = new ArrayAdapter<>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, songs);
                    saveInfoUserselect.saveColorText(SaveInfoUserselect.USER_ColorT_KEY, colorsitems[0]);
                } else if (parent.getItemAtPosition(position).equals(colorsname[1])) {
                    textColor.setTextColor(Color.parseColor(colorsitems[1]));
                    saveInfoUserselect.savespinnerSelection(SaveInfoUserselect.USER_Int_Spinnerindex,1);
                    stringArrayAdapter = new ArrayAdapter<>(view.getContext(), R.layout.grren, songs);
                    saveInfoUserselect.saveColorText(SaveInfoUserselect.USER_ColorT_KEY, colorsitems[1]);

                    Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[1]);
                } else if (parent.getItemAtPosition(position).equals(colorsname[2])) {
                    textColor.setTextColor(Color.parseColor(colorsitems[2]));
                    saveInfoUserselect.savespinnerSelection(SaveInfoUserselect.USER_Int_Spinnerindex,2);
                    stringArrayAdapter = new ArrayAdapter<>(view.getContext(), R.layout.text, songs);
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
                    stringArrayAdapter = new ArrayAdapter<>(view.getContext()
                            , R.layout.blau, songs);
                    Log.i("spinn1", "onItemSelected: " + parent.getItemAtPosition(position) + colorsname[3]);
                    saveInfoUserselect.saveColorText(SaveInfoUserselect.USER_ColorT_KEY, colorsitems[3]);
                }
                listView.setAdapter(stringArrayAdapter);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void userImage(View view) {
           Button button=(Button)view;
           button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(Intent.ACTION_PICK,
                           MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                   intent.setType("image/*");
                   //  intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                   // intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                   //intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                   startActivityForResult(intent, 1);
               }
           });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            Uri uri = data.getData();
            //   imageView.setImageURI(uri);
            //  SaveThings saveThings=new SaveThings();
            // saveThings.setImage(ConvertData.imageViewToBtamp(imageView));
            // dataBase.daoData().image(saveThings);
            // saveInfoUserselect.saveURIImage(SaveInfoUserselect.USER_Image_KEY, Uri.decode(uri.toString()));
            Log.i("Path1", "onActivityResult: " + uri);
            String[] file = {MediaStore.MediaColumns.DATA};

            Cursor cursor = getActivity().managedQuery(uri, file, null, null, null);
            Log.i("Path1", "onActivityResult: " + cursor);
            cursor.moveToFirst();

            int index = cursor.getColumnIndex(file[0]);
            Log.i("Path2", "onActivityResult: " + index);
            impath = cursor.getString(index);

            saveInfoUserselect.saveURIImage(SaveInfoUserselect.USER_Image_KEY, Uri.decode(impath));
            Drawable bg = null;
            InputStream inputStream = null;
            try {
                inputStream =getActivity().getContentResolver().openInputStream(uri);
                Log.i("inputS", "onActivityResult: " + inputStream);
                bg = Drawable.createFromStream(inputStream, uri.toString());


            } catch (FileNotFoundException e) {
                linearLayout.setBackgroundResource(R.drawable.n);
            }
            linearLayout.setBackground(bg);

        }


    }
    private void pickimage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imageView.setImageURI(Uri.parse(saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY)));

    }
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


    @Override
    public void onClick(View v) {
        boolean check = ((RadioButton) v).isChecked();
        Log.i("radio", "UserRad: "+"radio");
        switch (v.getId()) {
            case R.id.radioButton1:
                saveInfoUserselect.saveURIImage(SaveInfoUserselect.USER_Image_KEY, "R.drawable.n");
                linearLayout.setBackgroundResource(R.drawable.n);
                break;
            case R.id.radioButton2:
                Log.i("radio", "UserRad: "+"radio");
                saveInfoUserselect.saveURIImage(SaveInfoUserselect.USER_Image_KEY,"R.drawable.app");
                linearLayout.setBackgroundResource(R.drawable.app);

                break;

        }

    }

}
